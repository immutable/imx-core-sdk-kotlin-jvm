package com.immutable.sdk

import com.google.common.annotations.VisibleForTesting
import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.extensions.getStarkPublicKey
import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture

/**
 * A simple implementation of [StarkSigner] that holds the [ECKeyPair] in memory.
 */
class StandardStarkSigner(@VisibleForTesting internal val keyPair: ECKeyPair) : StarkSigner {
    override fun getAddress(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync { keyPair.getStarkPublicKey() }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun signMessage(message: String): CompletableFuture<String> {
        val future = CompletableFuture<String>()
        try {
            future.complete(StarkKey.sign(keyPair, message))
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
        return future
    }
}
