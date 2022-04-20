package com.immutable.sdk

import org.junit.Assert.assertEquals
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestException : Exception()

/**
 * Helper for testing futures that will check the expected result/error after waiting for it to
 * complete.
 *
 * @param future that is to be tested
 * @param expectedResult from the future, null if errored
 * @param expectedError from the future, null if completed successfully
 * @param block to be executed that will trigger the completion and result of the future
 */
fun <T> testFuture(
    future: CompletableFuture<T>,
    expectedResult: T?,
    expectedError: Throwable?
) {
    val latch = CountDownLatch(1)
    var data: T? = null
    var error: Throwable? = null

    future.whenComplete { result, throwable ->
        data = result
        error = throwable
        throwable.printStackTrace()
        latch.countDown()
    }

    latch.await(3000, TimeUnit.MILLISECONDS)

    assertEquals(expectedResult, data)
    if (expectedError == null)
        assert(error == null)
    else {
        val rootCause = when (error) {
            is CompletionException -> error!!.cause
            else -> error
        }

        // If expected error is ImmutableException verify the type is correct otherwise just match
        // the exception class
        if (expectedError is ImmutableException)
            assert(expectedError.type == (rootCause as ImmutableException).type)
        else
            assert(rootCause?.javaClass?.isInstance(expectedError) == true)
    }
}

/**
 * Helper for testing futures that will return the result and error in the given [block] after waiting for it to
 * complete.
 *
 * @param future that is to be tested
 * @param block to be executed that will trigger the completion and result of the future
 */
fun <T> testFuture(
    future: CompletableFuture<T>,
    block: (T?, Throwable?) -> Unit
) {
    val latch = CountDownLatch(1)

    future.whenComplete { result, throwable ->
        block(result, throwable)
        latch.countDown()
    }

    latch.await(3000, TimeUnit.MILLISECONDS)
}
