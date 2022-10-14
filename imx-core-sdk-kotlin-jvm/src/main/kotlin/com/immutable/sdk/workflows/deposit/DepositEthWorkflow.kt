package com.immutable.sdk.workflows.deposit

import com.immutable.sdk.Constants.HEX_RADIX
import com.immutable.sdk.ImmutableConfig
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.extensions.getNonce
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.call
import com.immutable.sdk.workflows.getSignableRegistrationOnChain
import com.immutable.sdk.workflows.isRegisteredOnChain
import org.web3j.crypto.RawTransaction
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_DEPOSIT = "Get signable deposit"
private const val ENCODE_ASSET = "Encode asset"
private const val ASSET_TYPE = "asset"
private const val IS_USER_REGISTERED_ON_CHAIN = "Is user registered on chain"

@Suppress("LongParameterList", "LongMethod")
internal fun depositEth(
    base: ImmutableXBase,
    nodeUrl: String,
    token: EthAsset,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
): CompletableFuture<String> {
    val future = CompletableFuture<String>()

    val web3j = Web3j.build(HttpService(nodeUrl))
    val amount = Convert.toWei(token.quantity, Convert.Unit.ETHER).toBigInteger()

    signer.getAddress()
        .thenCompose { address ->
            getSignableDeposit(address, token, amount, depositsApi)
                .thenApply { response -> address to response }
        }
        .thenCompose { (address, signableDepositResponse) ->
            encodeAsset(encodingApi)
                .thenApply { encodeAssetResponse ->
                    Params(address, signableDepositResponse, encodeAssetResponse)
                }
        }
        .thenCompose { params ->
            isUserRegisteredOnChain(base, web3j, params)
                .thenApply { isRegistered -> isRegistered to params }
        }.thenCompose { (isRegistered, params) ->
            executeDeposit(base, web3j, signer, amount, isRegistered, params, usersApi)
        }
        .whenComplete { response, throwable ->
            if (throwable != null) future.completeExceptionally(throwable)
            else future.complete(response.transactionHash)
        }

    return future
}

private fun getSignableDeposit(
    address: String,
    token: EthAsset,
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

private fun encodeAsset(api: EncodingApi) = call(ENCODE_ASSET) {
    val request =
        EncodeAssetRequest(token = EncodeAssetRequestToken(type = EncodeAssetRequestToken.Type.eTH))
    api.encodeAsset(ASSET_TYPE, request)
}

private fun isUserRegisteredOnChain(
    base: ImmutableXBase,
    web3j: Web3j,
    params: Params
): CompletableFuture<Boolean> = call(IS_USER_REGISTERED_ON_CHAIN) {
    val registrationContract = Registration_sol_Registration.load(
        ImmutableConfig.getRegistrationContractAddress(base),
        web3j,
        ClientTransactionManager(web3j, params.address),
        DefaultGasProvider()
    )

    // Checks whether the user is registered on chain
    isRegisteredOnChain(params.signableDepositResponse.starkKey, registrationContract).get()
}

@Suppress("LongParameterList")
private fun executeDeposit(
    base: ImmutableXBase,
    web3j: Web3j,
    signer: Signer,
    amount: BigInteger,
    isRegistered: Boolean,
    params: Params,
    usersApi: UsersApi
): CompletableFuture<EthSendTransaction> {
    val assetType = params.encodeAssetResponse.assetType.toBigInteger()
    val starkPublicKey = params.signableDepositResponse.starkKey
    val vaultId = params.signableDepositResponse.vaultId.toBigInteger()

    val gasProvider = DefaultGasProvider()
    val coreContract = Core_sol_Core.load(
        ImmutableConfig.getCoreContractAddress(base),
        web3j,
        ClientTransactionManager(web3j, params.address),
        gasProvider
    )

    return if (!isRegistered)
    // User is not registered, do both registration and deposit
        executeRegisterAndDepositEth(
            web3j = web3j,
            amount = amount,
            assetType = assetType,
            vaultId = vaultId,
            signer = signer,
            starkPublicKey = starkPublicKey,
            gasProvider = gasProvider,
            contract = coreContract,
            usersApi = usersApi
        )
    else
    // User is already registered, continue to deposit
        executeDepositEth(
            web3j = web3j,
            amount = amount,
            assetType = assetType,
            vaultId = vaultId,
            signer = signer,
            starkPublicKey = starkPublicKey,
            gasProvider = gasProvider,
            contract = coreContract
        )
}

@Suppress("LongParameterList")
private fun executeRegisterAndDepositEth(
    web3j: Web3j,
    amount: BigInteger,
    assetType: BigInteger,
    vaultId: BigInteger,
    signer: Signer,
    starkPublicKey: String,
    gasProvider: DefaultGasProvider,
    contract: Core_sol_Core,
    usersApi: UsersApi
): CompletableFuture<EthSendTransaction> {
    val future = CompletableFuture<EthSendTransaction>()
    signer.getAddress()
        .thenCompose { address ->
            getSignableRegistrationOnChain(
                address,
                starkPublicKey,
                usersApi
            ).thenApply { response -> address to response }
        }
        .thenCompose { (address, response) ->
            val data = contract.registerAndDepositEth(
                address,
                starkPublicKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                response.operatorSignature.hexToByteArray(),
                assetType,
                vaultId
            ).encodeFunctionCall()

            signer.signTransaction(
                RawTransaction.createTransaction(
                    web3j.getNonce(address),
                    gasProvider.gasPrice,
                    gasProvider.getGasLimit(Core_sol_Core.FUNC_REGISTERANDDEPOSITETH),
                    contract.contractAddress,
                    amount,
                    data
                )
            )
        }
        .thenCompose { signedTransaction ->
            web3j.ethSendRawTransaction(signedTransaction).sendAsync()
        }
        .whenComplete { response, throwable ->
            if (throwable != null) {
                future.completeExceptionally(throwable)
            } else {
                future.complete(response)
            }
        }
    return future
}

@Suppress("LongParameterList")
private fun executeDepositEth(
    web3j: Web3j,
    amount: BigInteger,
    assetType: BigInteger,
    vaultId: BigInteger,
    signer: Signer,
    starkPublicKey: String,
    gasProvider: DefaultGasProvider,
    contract: Core_sol_Core,
): CompletableFuture<EthSendTransaction> {
    val future = CompletableFuture<EthSendTransaction>()
    signer.getAddress()
        .thenCompose { address ->
            val data = contract.deposit(
                starkPublicKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                assetType,
                vaultId
            ).encodeFunctionCall()

            signer.signTransaction(
                RawTransaction.createTransaction(
                    web3j.getNonce(address),
                    gasProvider.gasPrice,
                    gasProvider.getGasLimit(Core_sol_Core.FUNC_DEPOSITETH),
                    contract.contractAddress,
                    amount,
                    data
                )
            )
        }
        .thenCompose { signedTransaction ->
            web3j.ethSendRawTransaction(signedTransaction).sendAsync()
        }
        .whenComplete { response, throwable ->
            if (throwable != null) {
                future.completeExceptionally(throwable)
            } else {
                future.complete(response)
            }
        }
    return future
}

private data class Params(
    val address: String,
    val signableDepositResponse: GetSignableDepositResponse,
    val encodeAssetResponse: EncodeAssetResponse
)
