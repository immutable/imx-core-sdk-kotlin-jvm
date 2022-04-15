package com.immutable.sdk.extensions

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

/**
 * Returns a new CompletionStage that, when [this][CompletableFuture] and the [other] given stage
 * both complete normally, is executed with the two results as arguments to the supplied function.
 *
 * If an [ExecutionException] is thrown, this function also unpacks and return the cause instead if
 * it exists.
 */
internal fun <T, U, V> CompletableFuture<T>.combine(
    other: CompletableFuture<U>,
    combined: (T, U) -> CompletableFuture<V>
): CompletableFuture<V> {
    return thenCombine(other) { first, second ->
        try {
            combined(first, second).get()
        } catch (ex: ExecutionException) {
            throw ex.cause ?: ex
        }
    }
}
