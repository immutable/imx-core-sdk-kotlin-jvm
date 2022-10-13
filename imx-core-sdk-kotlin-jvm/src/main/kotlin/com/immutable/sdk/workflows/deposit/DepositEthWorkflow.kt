package com.immutable.sdk.workflows.deposit

import com.immutable.sdk.Constants
import com.immutable.sdk.Constants.HEX_PREFIX
import com.immutable.sdk.Constants.HEX_RADIX
import com.immutable.sdk.ImmutableConfig
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.EncodeAssetRequest
import com.immutable.sdk.api.model.EncodeAssetRequestToken
import com.immutable.sdk.api.model.GetSignableDepositRequest
import com.immutable.sdk.api.model.GetSignableDepositResponse
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.extensions.getNonce
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.extensions.signTransaction
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
import java.math.BigDecimal
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_DEPOSIT = "Get signable deposit"
private const val ENCODE_ASSET = "Encode asset"

private const val ASSET_TYPE = "asset"

@Suppress("UnusedPrivateMember", "LongParameterList", "LongMethod")
internal fun depositEth(
    base: ImmutableXBase,
    nodeUrl: String,
    token: EthAsset,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
): CompletableFuture<Unit> {
    val future = CompletableFuture<Unit>()

    val amount =
        BigDecimal(token.quantity).times(BigDecimal.TEN.pow(Constants.ETH_DECIMALS)).toBigInteger()

    val address = signer.getAddress().getNow("")

    val web3j =
        Web3j.build(HttpService(nodeUrl))
    val clientTransactionManager = ClientTransactionManager(web3j, address)
    val gasProvider = DefaultGasProvider()
    val coreContract = Core_sol_Core.load(
        ImmutableConfig.getCoreContractAddress(base),
        web3j,
        clientTransactionManager,
        gasProvider
    )
    val registrationContract = Registration_sol_Registration.load(
        ImmutableConfig.getRegistrationContractAddress(base),
        web3j,
        clientTransactionManager,
        gasProvider
    )

    getSignableDeposit(address, token, amount, depositsApi)
        .thenCompose { signableDepositResponse ->
            encodeAsset(token, encodingApi)
                .thenApply { response -> signableDepositResponse to response }
        }
        .thenCompose { (signableDepositResponse, encodeAssetResponse) ->
            isRegisteredOnChain(signableDepositResponse.starkKey, registrationContract)
                .thenApply { isRegistered ->
                    Triple(
                        isRegistered,
                        signableDepositResponse,
                        encodeAssetResponse
                    )
                }
        }.thenCompose { (isRegistered, signableDepositResponse, encodeAssetResponse) ->
            val assetType = encodeAssetResponse.assetType
            val starkPublicKey = signableDepositResponse.starkKey
            val vaultId = signableDepositResponse.vaultId
            if (!isRegistered) {
                registerAndDepositEth(
                    web3j,
                    amount,
                    assetType,
                    vaultId,
                    signer,
                    starkPublicKey,
                    gasProvider,
                    coreContract,
                    usersApi
                )
            } else {
                CompletableFuture.completedFuture(EthSendTransaction())
            }
        }
        .whenComplete { response, throwable ->
            if (throwable != null) {
                future.completeExceptionally(throwable)
            } else {
                future.complete(Unit)
            }
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

private fun encodeAsset(token: EthAsset, api: EncodingApi) = call(ENCODE_ASSET) {
    val request =
        EncodeAssetRequest(token = EncodeAssetRequestToken(type = EncodeAssetRequestToken.Type.eTH))
    api.encodeAsset(ASSET_TYPE, request)
}

@Suppress("LongParameterList")
private fun registerAndDepositEth(
    web3j: Web3j,
    amount: BigInteger,
    assetType: String,
    vaultId: Int,
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
                BigInteger(starkPublicKey.removePrefix(HEX_PREFIX), HEX_RADIX),
                response.operatorSignature.hexToByteArray(),
                assetType.toBigInteger(),
                vaultId.toBigInteger()
            ).encodeFunctionCall()

            val rawTransaction = RawTransaction.createTransaction(
                web3j.getNonce(address),
                gasProvider.gasPrice,
                gasProvider.getGasLimit(Core_sol_Core.FUNC_REGISTERANDDEPOSITETH),
                contract.contractAddress,
                amount,
                data
            )

            val signedTransaction = signer.signTransaction(rawTransaction)

            val result = web3j.ethSendRawTransaction(signedTransaction).sendAsync().get()

            CompletableFuture.completedFuture(result)
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
