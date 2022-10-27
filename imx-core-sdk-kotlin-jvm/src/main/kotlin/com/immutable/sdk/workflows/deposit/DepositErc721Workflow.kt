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
import com.immutable.sdk.contracts.IERC721_sol_IERC721
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.workflows.executeDepositToken
import com.immutable.sdk.workflows.executeRegisterAndDepositToken
import com.immutable.sdk.workflows.prepareDeposit
import com.immutable.sdk.workflows.sendTransaction
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList", "LongMethod")
internal fun depositErc721(
    base: ImmutableXBase,
    nodeUrl: String,
    token: Erc721Asset,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    gasProvider: StaticGasProvider,
): CompletableFuture<String> {
    val future = CompletableFuture<String>()

    val web3j = Web3j.build(HttpService(nodeUrl))

    prepareDeposit(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi, gasProvider)
        .thenCompose { params ->
            approveErc721Token(
                token,
                if (params.isRegistered) ImmutableConfig.getCoreContractAddress(base)
                else ImmutableConfig.getRegistrationContractAddress(base),
                web3j,
                signer,
                gasProvider
            ).thenApply { params }
        }
        .thenCompose { params ->
            // Using individual execute functions instead of `executeDeposit` since registration contract
            // is required (instead of core contract) to register and deposit ERC 721 tokens
            if (!params.isRegistered) executeRegisterAndDepositToken(
                web3j = web3j,
                signer = signer,
                params = params,
                contract = Registration_sol_Registration.load(
                    ImmutableConfig.getRegistrationContractAddress(base),
                    web3j,
                    ClientTransactionManager(web3j, params.address),
                    gasProvider
                ),
                contractFunction = Registration_sol_Registration.FUNC_REGISTERANDDEPOSITNFT,
                data = { contract: Registration_sol_Registration, response: GetSignableRegistrationResponse ->
                    contract.registerAndDepositNft(
                        params.address,
                        params.starkKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                        response.operatorSignature.hexToByteArray(),
                        params.assetType,
                        params.vaultId,
                        token.tokenId.toBigInteger()
                    ).encodeFunctionCall()
                },
                usersApi = usersApi,
                gasProvider = gasProvider
            )
            else executeDepositToken(
                web3j = web3j,
                signer = signer,
                params = params,
                contract = Core_sol_Core.load(
                    ImmutableConfig.getCoreContractAddress(base),
                    web3j,
                    ClientTransactionManager(web3j, params.address),
                    gasProvider
                ),
                contractFunction = Core_sol_Core.FUNC_DEPOSITNFT,
                data = { contract: Core_sol_Core ->
                    contract.depositNft(
                        params.starkKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                        params.assetType,
                        params.vaultId,
                        token.tokenId.toBigInteger()
                    ).encodeFunctionCall()
                },
                gasProvider = gasProvider
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
@Suppress("UnusedPrivateMember")
private fun approveErc721Token(
    token: Erc721Asset,
    approveForContractAddress: String,
    web3j: Web3j,
    signer: Signer,
    gasProvider: StaticGasProvider
): CompletableFuture<EthSendTransaction> = signer.getAddress()
    .thenCompose { address ->
        val contract = IERC721_sol_IERC721.load(
            token.tokenAddress,
            web3j,
            ClientTransactionManager(web3j, address),
            gasProvider
        )

        sendTransaction(
            contract = contract,
            contractFunction = IERC721_sol_IERC721.FUNC_APPROVE,
            data = contract.approve(
                approveForContractAddress,
                token.tokenId.toBigInteger()
            ).encodeFunctionCall(),
            signer = signer,
            web3j = web3j,
            gasProvider = gasProvider
        )
    }
