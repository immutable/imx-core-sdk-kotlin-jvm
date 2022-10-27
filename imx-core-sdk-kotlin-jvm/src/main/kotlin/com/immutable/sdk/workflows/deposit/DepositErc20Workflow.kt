package com.immutable.sdk.workflows.deposit

import com.immutable.sdk.Constants.HEX_RADIX
import com.immutable.sdk.ImmutableConfig
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationResponse
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.IERC20_sol_IERC20
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.formatQuantity
import com.immutable.sdk.workflows.executeDeposit
import com.immutable.sdk.workflows.prepareDeposit
import com.immutable.sdk.workflows.sendTransaction
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList", "LongMethod")
internal fun depositErc20(
    base: ImmutableXBase,
    nodeUrl: String,
    token: Erc20Asset,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    gasProvider: StaticGasProvider,
): CompletableFuture<String> {
    val future = CompletableFuture<String>()

    val web3j = Web3j.build(HttpService(nodeUrl))

    approveErc20Token(base, token, web3j, signer, gasProvider)
        .thenCompose {
            prepareDeposit(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi, gasProvider)
        }
        .thenCompose { params ->
            executeDeposit(
                base,
                nodeUrl,
                signer,
                params,
                registerAndDepositFunction = Core_sol_Core.FUNC_REGISTERANDDEPOSITERC20,
                registerAndDepositData = { contract: Core_sol_Core, response: GetSignableRegistrationResponse ->
                    contract.registerAndDepositERC20(
                        params.address,
                        params.starkKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                        response.operatorSignature.hexToByteArray(),
                        params.assetType,
                        params.vaultId,
                        params.amount
                    ).encodeFunctionCall()
                },
                depositFunction = Core_sol_Core.FUNC_DEPOSITERC20,
                depositData = { contract: Core_sol_Core ->
                    contract.depositERC20(
                        params.starkKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                        params.assetType,
                        params.vaultId,
                        params.amount
                    ).encodeFunctionCall()
                },
                usersApi,
                gasProvider
            )
        }
        .whenComplete { response, throwable ->
            if (throwable != null) future.completeExceptionally(throwable)
            else future.complete(response.transactionHash)
        }

    return future
}

/**
 * Approve whether an amount of token from an account can be spent by a third-party account
 */
private fun approveErc20Token(
    base: ImmutableXBase,
    token: Erc20Asset,
    web3j: Web3j,
    signer: Signer,
    gasProvider: StaticGasProvider
): CompletableFuture<EthSendTransaction> = signer.getAddress()
    .thenCompose { address ->
        val contract = IERC20_sol_IERC20.load(
            token.tokenAddress,
            web3j,
            ClientTransactionManager(web3j, address),
            gasProvider
        )

        sendTransaction(
            contract = contract,
            contractFunction = IERC20_sol_IERC20.FUNC_APPROVE,
            data = contract.approve(
                ImmutableConfig.getCoreContractAddress(base),
                token.formatQuantity().toBigInteger()
            ).encodeFunctionCall(),
            signer = signer,
            web3j = web3j,
            gasProvider = gasProvider
        )
    }
