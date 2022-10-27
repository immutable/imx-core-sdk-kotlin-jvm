package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableConfig
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.MintsApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationResponse
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.workflows.withdrawal.completeErc721Withdrawal
import com.immutable.sdk.workflows.withdrawal.completeFungibleTokenWithdrawal
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList")
internal fun completeWithdrawal(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    starkPublicKey: String,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    mintsApi: MintsApi
): CompletableFuture<String> = when (token) {
    is Erc721Asset -> completeErc721Withdrawal(
        base, nodeUrl, token, signer, starkPublicKey, usersApi, encodingApi, mintsApi
    )
    else -> completeFungibleTokenWithdrawal(
        base, nodeUrl, token, signer, starkPublicKey, usersApi, encodingApi
    )
}

/**
 * 1. Encodes the [token]
 * 2. Checks if user is registered on chain
 *
 * @return [DepositWorkflowParams]
 */
@Suppress("LongParameterList")
internal fun prepareCompleteWithdrawal(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    starkPublicKey: String,
    usersApi: UsersApi,
    encodingApi: EncodingApi
): CompletableFuture<CompleteWithdrawalWorkflowParams> {
    return signer.getAddress()
        .thenCompose { address ->
            encodeAsset(token, encodingApi).thenApply { encodeAssetResponse ->
                CompleteWithdrawalWorkflowParams(
                    token = token,
                    address = address,
                    starkKey = starkPublicKey,
                    assetType = encodeAssetResponse.assetType.toBigInteger(),
                )
            }
        }
        .thenCompose { params ->
            isRegisteredOnChain(base, nodeUrl, signer, usersApi)
                .thenApply { isRegistered -> params.copy(isRegistered = isRegistered) }
        }
}

@Suppress("LongParameterList")
internal fun executeCompleteWithdrawal(
    base: ImmutableXBase,
    nodeUrl: String,
    signer: Signer,
    params: CompleteWithdrawalWorkflowParams,
    registerAndWithdrawFunction: String,
    registerAndWithdrawData: (Registration_sol_Registration, GetSignableRegistrationResponse) -> String,
    withdrawFunction: String,
    withdrawData: (Core_sol_Core) -> String,
    usersApi: UsersApi
): CompletableFuture<EthSendTransaction> {
    val web3j = Web3j.build(HttpService(nodeUrl))

    return if (!params.isRegistered) // User is not registered, do both registration and complete withdrawal
        executeRegisterAndWithdrawToken(
            base,
            web3j,
            signer,
            params,
            registerAndWithdrawFunction,
            registerAndWithdrawData,
            usersApi
        )
    else // User is already registered, continue to complete withdrawal
        executeWithdrawToken(
            base,
            web3j,
            signer,
            params,
            withdrawFunction,
            withdrawData
        )
}

@Suppress("LongParameterList")
internal fun executeRegisterAndWithdrawToken(
    base: ImmutableXBase,
    web3j: Web3j,
    signer: Signer,
    params: CompleteWithdrawalWorkflowParams,
    contractFunction: String,
    data: (Registration_sol_Registration, GetSignableRegistrationResponse) -> String,
    usersApi: UsersApi
): CompletableFuture<EthSendTransaction> {
    val contract = Registration_sol_Registration.load(
        ImmutableConfig.getRegistrationContractAddress(base),
        web3j,
        ClientTransactionManager(web3j, params.address),
        DefaultGasProvider()
    )

    return signer.getAddress()
        .thenCompose { address ->
            getSignableRegistrationOnChain(
                address,
                params.starkKey,
                usersApi
            )
        }
        .thenCompose { response ->
            sendTransaction(
                contract = contract,
                contractFunction = contractFunction,
                data = data(contract, response),
                signer = signer,
                web3j = web3j
            )
        }
}

@Suppress("LongParameterList")
internal fun executeWithdrawToken(
    base: ImmutableXBase,
    web3j: Web3j,
    signer: Signer,
    params: CompleteWithdrawalWorkflowParams,
    contractFunction: String,
    data: (Core_sol_Core) -> String,
): CompletableFuture<EthSendTransaction> {
    val contract = Core_sol_Core.load(
        ImmutableConfig.getCoreContractAddress(base),
        web3j,
        ClientTransactionManager(web3j, params.address),
        DefaultGasProvider()
    )

    return sendTransaction(
        contract = contract,
        contractFunction = contractFunction,
        data = data(contract),
        signer = signer,
        web3j = web3j
    )
}

internal data class CompleteWithdrawalWorkflowParams(
    val token: AssetModel,
    val address: String,
    val starkKey: String,
    val assetType: BigInteger,
    val isRegistered: Boolean = false,
)
