package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.Constants.HEX_RADIX
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationRequest
import com.immutable.sdk.api.model.GetSignableRegistrationResponse
import com.immutable.sdk.api.model.RegisterUserRequest
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.extensions.hexToByteArray
import org.openapitools.client.infrastructure.ClientException
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

private const val GET_USER = "Get user"
private const val REGISTER_USER = "Register user"
private const val SIGNABLE_REGISTRATION = "Signable registration"
private const val SIGNABLE_REGISTRATION_ON_CHAIN = "Get signable registration on-chain"
private const val GET_STARK_KEY = "Get stark key"
internal const val USER_UNREGISTERED = "USER_UNREGISTERED"

private data class RegisterData(
    val address: String = "",
    val starkAddress: String = "",
    val isRegistered: Boolean = false,
    val ethSignature: String = "",
    val starkSignature: String = ""
)

/**
 * This is a utility function that will register a user to Immutable X if they aren't already
 */
internal fun registerOffChain(
    signer: Signer,
    starkSigner: StarkSigner,
    api: UsersApi = UsersApi()
): CompletableFuture<Unit> {
    val future = CompletableFuture<Unit>()

    signer.getAddress().thenApply { RegisterData(address = it) }
        .thenCompose { data -> starkSigner.getAddress().thenApply { data.copy(starkAddress = it) } }
        .thenCompose { data -> isUserRegistered(data, api) }
        .thenCompose { data -> getSignatures(signer, starkSigner, api, data) }
        .thenCompose { data -> registerUser(data, api) }
        .whenComplete { _, throwable ->
            // Forward any exceptions from the compose chain
            if (throwable != null)
                future.completeExceptionally(throwable)
            else
                future.complete(Unit)
        }

    return future
}

@Suppress("MagicNumber")
private fun isUserRegistered(
    data: RegisterData,
    api: UsersApi
): CompletableFuture<RegisterData> {
    val isRegisteredFuture = CompletableFuture<RegisterData>()
    CompletableFuture.runAsync {
        try {
            val isRegistered =
                api.getUsers(data.address).accounts.isNotEmpty()
            isRegisteredFuture.complete(data.copy(isRegistered = isRegistered))
        } catch (e: ClientException) {
            // Endpoint returns 404 when the user isn't registered
            if (e.statusCode == HttpURLConnection.HTTP_NOT_FOUND)
                isRegisteredFuture.complete(data.copy(isRegistered = false))
            else
                isRegisteredFuture.completeExceptionally(ImmutableException.apiError(GET_USER, e))
        }
    }
    return isRegisteredFuture
}

private fun getSignatures(
    signer: Signer,
    starkSigner: StarkSigner,
    api: UsersApi,
    data: RegisterData
) =
    if (data.isRegistered)
        CompletableFuture.completedFuture(data)
    else {
        call(SIGNABLE_REGISTRATION) {
            val result = api.getSignableRegistrationOffchain(
                GetSignableRegistrationRequest(
                    etherKey = data.address,
                    starkKey = data.starkAddress
                )
            )
            result.payloadHash to result.signableMessage
        }
            .thenCompose { (payloadHash, signableMessage) ->
                starkSigner.signMessage(payloadHash).thenApply { it to signableMessage }
            }
            .thenCompose { (starkSignature, signableMessage) ->
                signer.signMessage(signableMessage).thenApply { ethSignature ->
                    data.copy(
                        ethSignature = Crypto.serialiseEthSignature(ethSignature),
                        starkSignature = starkSignature
                    )
                }
            }
    }

@Suppress("TooGenericExceptionCaught")
private fun registerUser(
    data: RegisterData,
    api: UsersApi
): CompletableFuture<Unit> = if (data.isRegistered)
    CompletableFuture.completedFuture(Unit)
else {
    call(REGISTER_USER) {
        val starkPublicKey = data.starkAddress
        val body = RegisterUserRequest(
            ethSignature = data.ethSignature,
            etherKey = data.address,
            starkKey = starkPublicKey,
            starkSignature = data.starkSignature
        )
        api.registerUser(body)
        Unit
    }
}

@Suppress("TooGenericExceptionCaught")
internal fun isRegisteredOnChain(
    base: ImmutableXBase,
    nodeUrl: String,
    signer: Signer,
    api: UsersApi,
    gasProvider: StaticGasProvider
): CompletableFuture<Boolean> {
    val future = CompletableFuture<Boolean>()

    signer.getAddress()
        .thenCompose { address ->
            // Get user's stark key from user's L1 address
            call(GET_STARK_KEY) { api.getUsers(address).accounts.first() }
                .thenApply { starkPublicKey -> address to starkPublicKey }
        }
        .thenCompose { (address, starkPublicKey) ->
            val web3j = Web3j.build(HttpService(nodeUrl))
            val contract = Registration_sol_Registration.load(
                ImmutableConfig.getRegistrationContractAddress(base),
                web3j,
                ClientTransactionManager(web3j, address),
                gasProvider
            )
            contract.isRegistered(starkPublicKey.hexRemovePrefix().toBigInteger(HEX_RADIX))
                .sendAsync()
        }
        .whenComplete { response, error ->
            if (error?.message?.contains(USER_UNREGISTERED) == true)
                future.complete(false)
            else if (error != null) // Forward exceptions
                future.completeExceptionally(error)
            else future.complete(response)
        }

    return future
}

internal fun getSignableRegistrationOnChain(
    address: String,
    starkPublicKey: String,
    api: UsersApi
): CompletableFuture<GetSignableRegistrationResponse> = call(SIGNABLE_REGISTRATION_ON_CHAIN) {
    api.getSignableRegistration(
        GetSignableRegistrationRequest(
            etherKey = address,
            starkKey = starkPublicKey
        )
    )
}

@Suppress("LongParameterList")
internal fun registerOnChain(
    base: ImmutableXBase,
    nodeUrl: String,
    signer: Signer,
    starkPublicKey: String,
    api: UsersApi,
    gasProvider: StaticGasProvider
): CompletableFuture<EthSendTransaction> = signer.getAddress()
    .thenCompose { address ->
        getSignableRegistrationOnChain(address, starkPublicKey, api)
            .thenApply { response -> address to response }
    }
    .thenCompose { (address, response) ->
        val web3j = Web3j.build(HttpService(nodeUrl))
        val contract = Core_sol_Core.load(
            ImmutableConfig.getCoreContractAddress(base),
            web3j,
            ClientTransactionManager(web3j, address),
            gasProvider
        )
        sendTransaction(
            contract = contract,
            contractFunction = Core_sol_Core.FUNC_REGISTERUSER,
            data = contract.registerUser(
                address,
                starkPublicKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                response.operatorSignature.hexToByteArray()
            ).encodeFunctionCall(),
            signer = signer,
            web3j = web3j,
            gasProvider = gasProvider
        )
    }
