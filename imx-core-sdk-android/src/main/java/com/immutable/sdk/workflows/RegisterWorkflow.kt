package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationRequest
import com.immutable.sdk.api.model.RegisterUserRequest
import com.immutable.sdk.crypto.Crypto
import org.openapitools.client.infrastructure.ClientException
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

private const val GET_USER = "Get user"
private const val REGISTER_USER = "Register user"
private const val SIGNABLE_REGISTRATION = "Signable registration"

private data class RegisterData(
    val address: String = "",
    val starkAddress: String = "",
    val isRegistered: Boolean = false,
    val ethSignature: String = "",
    val starkSignature: String = ""
)

/**
 * This is a utility function that will register a user to Immutable X if they aren't already
 * and then return their Stark key pair.
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
