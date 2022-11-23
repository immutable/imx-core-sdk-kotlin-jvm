package com.immutable.sdk.workflows.deposit

import com.immutable.sdk.Constants.HEX_RADIX
import com.immutable.sdk.ImmutableConfig
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.IERC721_sol_IERC721
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.workflows.executeDepositToken
import com.immutable.sdk.workflows.prepareDeposit
import com.immutable.sdk.workflows.registerOnChain
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
    val web3j = Web3j.build(HttpService(nodeUrl))

    return prepareDeposit(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi, gasProvider)
        .thenCompose { params ->
            approveErc721Token(
                token,
                ImmutableConfig.getCoreContractAddress(base),
                web3j,
                signer,
                gasProvider
            ).thenApply { params }
        }
        .thenCompose { params ->
            // Proxy registration contract registerAndDepositNft method is not used as it currently fails
            // ERC721 transfer ownership check
            if (!params.isRegistered)
                registerOnChain(base, nodeUrl, signer, params.starkKey, usersApi, gasProvider)
                    .thenApply { params }
            else CompletableFuture.completedFuture(params)
        }
        .thenCompose { params ->
            executeDepositToken(
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
        .thenApply { it.transactionHash }
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
            contractFunction = IERC721_sol_IERC721.FUNC_ISAPPROVEDFORALL,
            data = contract.isApprovedForAll(address, approveForContractAddress)
                .encodeFunctionCall(),
            signer = signer,
            web3j = web3j,
            gasProvider = gasProvider
        ).thenApply { response ->
            val approved = contract.isApprovedForAll(address, approveForContractAddress)
                .decodeFunctionResponse(response.transactionHash)
                .first().value as? Boolean
            contract to (approved ?: false)
        }
    }
    .thenCompose { (contract, isApprovedForAll) ->
        if (!isApprovedForAll) sendTransaction(
            contract = contract,
            contractFunction = IERC721_sol_IERC721.FUNC_SETAPPROVALFORALL,
            data = contract.setApprovalForAll(approveForContractAddress, true)
                .encodeFunctionCall(),
            signer = signer,
            web3j = web3j,
            gasProvider = gasProvider
        ) else CompletableFuture.completedFuture(EthSendTransaction())
    }
