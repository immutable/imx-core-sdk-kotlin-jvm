package com.immutable.sdk

import android.os.NetworkOnMainThreadException
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.crypto.CryptoUtil
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.extensions.sanitizeBytes
import com.immutable.sdk.extensions.toHexString
import com.immutable.sdk.model.RegisterUserRequestVerifyEth
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.stark.StarkKey
import com.immutable.sdk.utils.Constants
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ServerException
import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

enum class ImmutableXBase(val url: String) {
    Production("https://api.x.immutable.com"),
    Ropsten("https://api.ropsten.x.immutable.com")
}

object ImmutableXSdk {
    /**
     * Sets the base property used by all Immutable X API classes.
     */
    fun setBase(base: ImmutableXBase) {
        System.getProperties().setProperty("org.openapitools.client.baseUrl", base.url)
    }

    private data class LoginData(
        val address: String = "",
        val seed: String = "",
        val isRegistered: Boolean = false,
        val ethSignature: String = ""
    )

    /**
     * This is a utility function that will register a user to Immutable X if they aren't already
     * and then return their Stark key pair. This must be called from a background thread.
     *
     * @throws [InterruptedException]
     * @throws [ExecutionException]
     * @throws [NetworkOnMainThreadException] if this method isn't run on a background thread
     * @throws [ClientException] if the api requests fail due to a client error
     * @throws [ServerException] if the api requests fail due to a server error
     * @throws [UnsupportedOperationException] if the api response is informational or redirect
     */
    fun login(signer: Signer, api: UsersApi = UsersApi()): CompletableFuture<ECKeyPair> {
        val future = CompletableFuture<ECKeyPair>()

        signer.getAddress().thenApply { LoginData(address = it) }
            .thenCompose { data ->
                signer.signMessage(data.address, Constants.STARK_MESSAGE)
                    .thenApply { data.copy(seed = it) }
            }
            .thenCompose { data -> generateStarkKeyPair(data) }
            .thenCompose { keyPairAndData -> isUserRegistered(keyPairAndData, api) }
            .thenCompose { keyPairAndData -> getRegisterMessage(signer, keyPairAndData) }
            .thenCompose { keyPairAndData -> registerUser(keyPairAndData, api) }
            .whenComplete { ecKeyPair, throwable ->
                // Forward any exceptions from the compose chain to the login future
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
                    keyPairFuture.completeExceptionally(ImmutableException("Failed to generate Stark key pair"))
                else
                    keyPairFuture.complete(keyPair)
            } catch (e: Exception) {
                keyPairFuture.completeExceptionally(ImmutableException("Failed to generate Stark key pair"))
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
                    api.getUser(keyPairAndData.second.address).accounts?.isNotEmpty() == true
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
                    isRegisteredFuture.completeExceptionally(e)
            }
        }
        return isRegisteredFuture
    }

    private fun getRegisterMessage(
        signer: Signer,
        keyPairAndData: Pair<ECKeyPair, LoginData>
    ): CompletableFuture<Pair<ECKeyPair, LoginData>> {
        return if (keyPairAndData.second.isRegistered)
            CompletableFuture.supplyAsync { keyPairAndData }
        else
            signer.signMessage(
                keyPairAndData.second.address,
                Constants.REGISTER_SIGN_MESSAGE
            ).thenApply {
                keyPairAndData.first to keyPairAndData.second.copy(
                    ethSignature = Crypto.serializeEthSignature(it)
                )
            }
    }

    @Suppress("TooGenericExceptionCaught")
    private fun registerUser(
        keyPairAndData: Pair<ECKeyPair, LoginData>,
        api: UsersApi
    ): CompletableFuture<ECKeyPair> {
        val registerFuture = CompletableFuture<ECKeyPair>()
        if (keyPairAndData.second.isRegistered)
            registerFuture.complete(keyPairAndData.first)
        else {
            CompletableFuture.runAsync {
                try {
                    val starkPublicKey = keyPairAndData.first.getStarkPublicKey()
                    val body = RegisterUserRequestVerifyEth(
                        ethSignature = keyPairAndData.second.ethSignature,
                        etherKey = keyPairAndData.second.address,
                        starkKey = starkPublicKey,
                        starkSignature = StarkCurve.sign(
                            keyPairAndData.first,
                            CryptoUtil.getRegisterUserMsgVerifyEth(
                                keyPairAndData.second.address,
                                starkPublicKey
                            )
                        )
                    )
                    api.registerUser(body)
                    registerFuture.complete(keyPairAndData.first)
                } catch (e: Exception) {
                    registerFuture.completeExceptionally(e)
                }
            }
        }
        return registerFuture
    }

    private fun ECKeyPair.getStarkPublicKey() =
        publicKey.toString(Constants.HEX_RADIX)
            .sanitizeBytes()
            .hexToByteArray()
            .toHexString(byteLength = Constants.STARK_KEY_PUBLIC_BYTE_LENGTH)
}
