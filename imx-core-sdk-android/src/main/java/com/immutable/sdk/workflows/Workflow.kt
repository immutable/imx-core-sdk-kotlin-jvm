package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import java.util.concurrent.CompletableFuture

@Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")
internal fun <T> call(callName: String, call: () -> T): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    CompletableFuture.runAsync {
        try {
            future.complete(call())
        } catch (e: Exception) {
            if (e is NullPointerException)
                future.completeExceptionally(ImmutableException.invalidResponse(callName, e))
            else
                future.completeExceptionally(ImmutableException.apiError(callName, e))
        }
    }
    return future
}

internal fun <T> completeExceptionally(ex: Throwable): CompletableFuture<T> =
    CompletableFuture<T>().apply {
        completeExceptionally(ex)
    }
