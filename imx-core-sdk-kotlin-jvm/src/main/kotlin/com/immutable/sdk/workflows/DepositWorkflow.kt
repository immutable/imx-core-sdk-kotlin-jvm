package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableConfig.getCoreContractAddress
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableDepositRequest
import com.immutable.sdk.api.model.GetSignableDepositResponse
import com.immutable.sdk.api.model.GetSignableRegistrationResponse
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.model.*
import com.immutable.sdk.workflows.deposit.depositErc20
import com.immutable.sdk.workflows.deposit.depositErc721
import com.immutable.sdk.workflows.deposit.depositEth
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.Contract
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_DEPOSIT = "Get signable deposit"

@Suppress("LongParameterList")
internal fun deposit(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    gasProvider: StaticGasProvider
): CompletableFuture<String> {
    return when (token) {
        is EthAsset ->
            depositEth(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi, gasProvider)
        is Erc20Asset ->
            depositErc20(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi, gasProvider)
        is Erc721Asset ->
            depositErc721(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi, gasProvider)
    }
}

/**
 * 1. Gets signable deposit
 * 2. Encodes the [token]
 * 3. Checks if user is registered on chain
 *
 * @return [DepositWorkflowParams]
 */
@Suppress("LongParameterList")
internal fun prepareDeposit(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    gasProvider: StaticGasProvider
): CompletableFuture<DepositWorkflowParams> {
    val amount = when (token) {
        is EthAsset -> Convert.toWei(token.quantity, Convert.Unit.ETHER).toBigInteger()
        is Erc20Asset -> token.formatQuantity().toBigInteger()
        is Erc721Asset -> BigInteger.ONE
    }

    return signer.getAddress()
        .thenCompose { address ->
            getSignableDeposit(address, token, amount, depositsApi)
                .thenApply { response -> address to response }
        }
        .thenCompose { (address, signableDepositResponse) ->
            encodeAsset(token, encodingApi).thenApply { encodeAssetResponse ->
                DepositWorkflowParams(
                    tokenType = token.toSignableToken().type,
                    address = address,
                    starkKey = signableDepositResponse.starkKey,
                    assetType = encodeAssetResponse.assetType.toBigInteger(),
                    vaultId = signableDepositResponse.vaultId.toBigInteger(),
                    amount = amount
                )
            }
        }
        .thenCompose { params ->
            isRegisteredOnChain(base, nodeUrl, signer, usersApi, gasProvider)
                .thenApply { isRegistered -> params.copy(isRegistered = isRegistered) }
        }
}

private fun getSignableDeposit(
    address: String,
    token: AssetModel,
    amount: BigInteger,
    api: DepositsApi
): CompletableFuture<GetSignableDepositResponse> = call(GET_SIGNABLE_DEPOSIT) {
    api.getSignableDeposit(
        GetSignableDepositRequest(
            amount = amount.toString(),
            token = token.toSignableToken(),
            user = address
        )
    )
}

@Suppress("LongParameterList")
internal fun executeDeposit(
    base: ImmutableXBase,
    nodeUrl: String,
    signer: Signer,
    params: DepositWorkflowParams,
    registerAndDepositFunction: String,
    registerAndDepositData: (Core_sol_Core, GetSignableRegistrationResponse) -> String,
    depositFunction: String,
    depositData: (Core_sol_Core) -> String,
    usersApi: UsersApi,
    gasProvider: StaticGasProvider
): CompletableFuture<EthSendTransaction> {
    val web3j = Web3j.build(HttpService(nodeUrl))
    val contract = Core_sol_Core.load(
        getCoreContractAddress(base),
        web3j,
        ClientTransactionManager(web3j, params.address),
        gasProvider
    )
    return if (!params.isRegistered) // User is not registered, do both registration and deposit
        executeRegisterAndDepositToken(
            web3j,
            signer,
            params,
            contract,
            registerAndDepositFunction,
            registerAndDepositData,
            usersApi,
            gasProvider
        )
    else // User is already registered, continue to deposit
        executeDepositToken(web3j, signer, params, contract, depositFunction, depositData, gasProvider)
}

@Suppress("LongParameterList")
private fun <C : Contract> executeRegisterAndDepositToken(
    web3j: Web3j,
    signer: Signer,
    params: DepositWorkflowParams,
    contract: C,
    contractFunction: String,
    data: (C, GetSignableRegistrationResponse) -> String,
    usersApi: UsersApi,
    gasProvider: StaticGasProvider
): CompletableFuture<EthSendTransaction> {
    // This amount is only used for Eth asset
    val transactionAmount =
        if (params.tokenType == TokenType.ETH.name) params.amount else BigInteger.ZERO

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
                amount = transactionAmount,
                data = data(contract, response),
                signer = signer,
                web3j = web3j,
                gasProvider = gasProvider
            )
        }
}

@Suppress("LongParameterList")
internal fun <C : Contract> executeDepositToken(
    web3j: Web3j,
    signer: Signer,
    params: DepositWorkflowParams,
    contract: C,
    contractFunction: String,
    data: (C) -> String,
    gasProvider: StaticGasProvider
): CompletableFuture<EthSendTransaction> {
    // This amount is only used for Eth asset
    val transactionAmount =
        if (params.tokenType == TokenType.ETH.name) params.amount else BigInteger.ZERO

    return sendTransaction(
        contract = contract,
        contractFunction = contractFunction,
        amount = transactionAmount,
        data = data(contract),
        signer = signer,
        web3j = web3j,
        gasProvider = gasProvider
    )
}

internal data class DepositWorkflowParams(
    val tokenType: String?,
    val address: String,
    val starkKey: String,
    val assetType: BigInteger,
    val vaultId: BigInteger,
    val amount: BigInteger,
    val isRegistered: Boolean = false,
)
