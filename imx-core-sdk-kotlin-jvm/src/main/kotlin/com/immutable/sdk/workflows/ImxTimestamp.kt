package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.crypto.Crypto
import java.time.Clock
import java.time.Instant
import java.util.concurrent.CompletableFuture

internal data class ImxTimestamp(val timestamp: String, val signature: String)

/**
 * Generic helper for API's that require a signed timestamp as a parameter.
 *
 * https://docs.x.immutable.com/docs/generate-imx-signature/
 */

internal fun <T> imxTimestampRequest(
    signer: Signer,
    clock: Clock = Clock.systemUTC(),
    performRequest: (ImxTimestamp) -> CompletableFuture<T>
): CompletableFuture<T> {
    val seconds = Instant.now(clock).epochSecond
    return signer.signMessage(seconds.toString())
        .thenCompose { signature ->
            performRequest(ImxTimestamp(seconds.toString(), Crypto.serialiseEthSignature(signature)))
        }
}
