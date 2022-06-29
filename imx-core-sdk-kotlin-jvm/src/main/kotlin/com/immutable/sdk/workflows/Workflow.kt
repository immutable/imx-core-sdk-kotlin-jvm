package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import java.util.concurrent.CompletableFuture

/**
 * Within the provided call block force unwrap any response values that are required and if they are
 * missing it will automatically be handled and return an invalidResponse exception.
 */
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

/**
 * Data model for workflows to hold the ethAddress and signatures for transactions
 */
internal class WorkflowSignatures(val ethAddress: String) {
    lateinit var ethSignature: String
    lateinit var starkSignature: String
}
