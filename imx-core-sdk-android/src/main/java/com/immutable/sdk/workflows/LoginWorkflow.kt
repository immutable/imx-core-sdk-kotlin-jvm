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
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.stark.StarkKey
import com.immutable.sdk.utils.Constants
import org.openapitools.client.infrastructure.ClientException
import org.web3j.crypto.ECKeyPair
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
        .thenCompose { keyPairAndData -> isUserRegistered(keyPairAndData, api) }
        .thenCompose { keyPairAndData -> getSignatures(signer, api, keyPairAndData) }
        .thenCompose { keyPairAndData -> registerUser(keyPairAndData, api) }
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
    keyPairAndData: Pair<ECKeyPair, LoginData>,
    api: UsersApi
): CompletableFuture<Pair<ECKeyPair, LoginData>> {
    val isRegisteredFuture = CompletableFuture<Pair<ECKeyPair, LoginData>>()
    CompletableFuture.runAsync {
        try {
            val isRegistered =
                api.getUsers(keyPairAndData.second.address).accounts?.isNotEmpty() == true
            isRegisteredFuture.complete(
                keyPairAndData.first to keyPairAndData.second.copy(
                    isRegistered = isRegistered
                )
            )
        } catch (e: ClientException) {
            // Endpoint returns 404 when the user isn't registered
            if (e.statusCode == 404)
                isRegisteredFuture.complete(
                    keyPairAndData.first to keyPairAndData.second.copy(
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
    keyPairAndData: Pair<ECKeyPair, LoginData>
) =
    if (keyPairAndData.second.isRegistered)
        CompletableFuture.completedFuture(keyPairAndData)
    else {
        call(SIGNABLE_REGISTRATION) {
            val result = api.getSignableRegistrationOffchain(
                GetSignableRegistrationRequest(
                    etherKey = keyPairAndData.second.address,
                    starkKey = keyPairAndData.first.getStarkPublicKey()
                )
            )
            // Force unwrap here so that the NPE gets handled by `call`
            result.payloadHash!! to result.signableMessage!!
        }.thenCompose { (payloadHash, signableMessage) ->
            signer.signMessage(signableMessage).thenApply { ethSignature ->
                keyPairAndData.first to keyPairAndData.second.copy(
                    ethSignature = Crypto.serializeEthSignature(ethSignature),
                    starkSignature = StarkCurve.sign(keyPairAndData.first, payloadHash)
                )
            }
        }
    }

@Suppress("TooGenericExceptionCaught")
private fun registerUser(
    keyPairAndData: Pair<ECKeyPair, LoginData>,
    api: UsersApi
): CompletableFuture<ECKeyPair> = if (keyPairAndData.second.isRegistered)
    CompletableFuture.completedFuture(keyPairAndData.first)
else {
    call(REGISTER_USER) {
        val starkPublicKey = keyPairAndData.first.getStarkPublicKey()
        val body = RegisterUserRequest(
            ethSignature = keyPairAndData.second.ethSignature,
            etherKey = keyPairAndData.second.address,
            starkKey = starkPublicKey,
            starkSignature = keyPairAndData.second.starkSignature
        )
        api.registerUser(body)
        keyPairAndData.first
    }
}

internal fun ECKeyPair.getStarkPublicKey() =
    publicKey.toString(Constants.HEX_RADIX)
        .sanitizeBytes()
        .hexToByteArray()
        .toHexString(byteLength = Constants.STARK_KEY_PUBLIC_BYTE_LENGTH)
