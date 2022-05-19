package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationRequest
import com.immutable.sdk.api.model.RegisterUserRequest
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.extensions.sanitizeBytes
import com.immutable.sdk.extensions.toHexString
import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.Constants
import org.openapitools.client.infrastructure.ClientException
import org.web3j.crypto.ECKeyPair
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

private const val GET_USER = "Get user"
private const val REGISTER_USER = "Register user"
private const val SIGNABLE_REGISTRATION = "Signable registration"

private data class LoginData(
    val address: String = "",
    val seed: String = "",
    val isRegistered: Boolean = false,
    val ethSignature: String = "",
    val starkSignature: String = ""
)

/**
 * This is a utility function that will register a user to Immutable X if they aren't already
 * and then return their Stark key pair.
 */
internal fun login(signer: Signer, api: UsersApi = UsersApi()): CompletableFuture<ECKeyPair> {
    val future = CompletableFuture<ECKeyPair>()

    signer.getAddress().thenApply { LoginData(address = it) }
        .thenCompose { data ->
            signer.signMessage(Constants.STARK_MESSAGE).thenApply { data.copy(seed = it) }
        }
        .thenCompose { data -> generateStarkKeyPair(data) }
        .thenCompose { (keyPair, data) -> isUserRegistered(keyPair, data, api) }
        .thenCompose { (keyPair, data) -> getSignatures(signer, api, keyPair, data) }
        .thenCompose { (keyPair, data) -> registerUser(keyPair, data, api) }
        .whenComplete { ecKeyPair, throwable ->
            // Forward any exceptions from the compose chain
            if (throwable != null)
                future.completeExceptionally(throwable)
            else
                future.complete(ecKeyPair)
        }

    return future
}

@Suppress("TooGenericExceptionCaught", "SwallowedException")
private fun generateStarkKeyPair(data: LoginData): CompletableFuture<Pair<ECKeyPair, LoginData>> {
    val keyPairFuture = CompletableFuture<ECKeyPair>()
    CompletableFuture.runAsync {
        try {
            val keyPair = StarkKey.getKeyFromRawSignature(data.seed, data.address)
            if (keyPair == null)
                keyPairFuture.completeExceptionally(ImmutableException.clientError("Failed to generate Stark key pair"))
            else
                keyPairFuture.complete(keyPair)
        } catch (e: Exception) {
            keyPairFuture.completeExceptionally(ImmutableException.clientError("Failed to generate Stark key pair"))
        }
    }
    return keyPairFuture.thenApply { it to data }
}

@Suppress("MagicNumber")
private fun isUserRegistered(
    keyPair: ECKeyPair,
    data: LoginData,
    api: UsersApi
): CompletableFuture<Pair<ECKeyPair, LoginData>> {
    val isRegisteredFuture = CompletableFuture<Pair<ECKeyPair, LoginData>>()
    CompletableFuture.runAsync {
        try {
            val isRegistered =
                api.getUsers(data.address).accounts?.isNotEmpty() == true
            isRegisteredFuture.complete(
                keyPair to data.copy(
                    isRegistered = isRegistered
                )
            )
        } catch (e: ClientException) {
            // Endpoint returns 404 when the user isn't registered
            if (e.statusCode == HttpURLConnection.HTTP_NOT_FOUND)
                isRegisteredFuture.complete(
                    keyPair to data.copy(
                        isRegistered = false
                    )
                )
            else
                isRegisteredFuture.completeExceptionally(ImmutableException.apiError(GET_USER, e))
        }
    }
    return isRegisteredFuture
}

private fun getSignatures(
    signer: Signer,
    api: UsersApi,
    keyPair: ECKeyPair,
    data: LoginData
) =
    if (data.isRegistered)
        CompletableFuture.completedFuture(keyPair to data)
    else {
        call(SIGNABLE_REGISTRATION) {
            val result = api.getSignableRegistrationOffchain(
                GetSignableRegistrationRequest(
                    etherKey = data.address,
                    starkKey = keyPair.getStarkPublicKey()
                )
            )
            // Force unwrap here so that the NPE gets handled by `call`
            result.payloadHash!! to result.signableMessage!!
        }.thenCompose { (payloadHash, signableMessage) ->
            signer.signMessage(signableMessage).thenApply { ethSignature ->
                keyPair to data.copy(
                    ethSignature = Crypto.serialiseEthSignature(ethSignature),
                    starkSignature = StarkKey.sign(keyPair, payloadHash)
                )
            }
        }
    }

@Suppress("TooGenericExceptionCaught")
private fun registerUser(
    keyPair: ECKeyPair,
    data: LoginData,
    api: UsersApi
): CompletableFuture<ECKeyPair> = if (data.isRegistered)
    CompletableFuture.completedFuture(keyPair)
else {
    call(REGISTER_USER) {
        val starkPublicKey = keyPair.getStarkPublicKey()
        val body = RegisterUserRequest(
            ethSignature = data.ethSignature,
            etherKey = data.address,
            starkKey = starkPublicKey,
            starkSignature = data.starkSignature
        )
        api.registerUser(body)
        keyPair
    }
}

internal fun ECKeyPair.getStarkPublicKey() =
    publicKey.toString(Constants.HEX_RADIX)
        .sanitizeBytes()
        .hexToByteArray()
        .toHexString(byteLength = Constants.STARK_KEY_PUBLIC_BYTE_LENGTH)
