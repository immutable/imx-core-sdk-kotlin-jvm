package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.crypto.Crypto
import java.util.concurrent.CompletableFuture
import kotlin.math.floor

internal data class ImxTimestamp(val timestamp: String, val signature: String)

/**
 * Generic helper for API's that require a signed timestamp as a parameter.
 *
 * https://docs.x.immutable.com/docs/generate-imx-signature/
 */

internal fun <T> imxTimestampRequest(
    signer: Signer,
    performRequest: (ImxTimestamp) -> CompletableFuture<T>
): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    val seconds = System.currentTimeMillis() / 1000L
    signer.signMessage(floor(seconds.toDouble()).toString())
        .thenCompose { signature ->
            performRequest(ImxTimestamp(seconds.toString(), Crypto.serialiseEthSignature(signature)))
        }
        .whenComplete { collection, throwable ->
            if (throwable != null)
                future.completeExceptionally(throwable)
            else
                future.complete(collection)
        }
    return future
}
