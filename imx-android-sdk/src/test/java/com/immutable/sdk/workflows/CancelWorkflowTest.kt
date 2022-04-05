package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.model.CancelOrderResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private const val LATCH_TIME_OUT_MS = 3000L
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ORDER_ID = 4511
private const val STATUS = "success"

class CancelWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var starkSigner: StarkSigner

    private lateinit var starkSignatureFuture: CompletableFuture<String>
    private var throwable: Throwable? = null
    private var cancelledOrderId: Int? = null
    private var completed = false

    private lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture

        latch = CountDownLatch(1)
    }

    private fun cancel() {
        val future = cancel(
            orderId = ORDER_ID.toString(),
            starkSigner = starkSigner,
            ordersApi = ordersApi
        )
        future.whenComplete { id, error ->
            completed = true
            cancelledOrderId = id
            throwable = error
            latch.countDown()
        }
        latch.await(LATCH_TIME_OUT_MS, TimeUnit.MILLISECONDS)
    }

    @Test
    fun testCancelSuccess() {
        starkSignatureFuture.complete(SIGNATURE)

        every { ordersApi.cancelOrder(any(), any()) } returns CancelOrderResponse(ORDER_ID, STATUS)

        cancel()

        assert(completed)
        assert(throwable == null)
        assert(cancelledOrderId == ORDER_ID)
    }

    @Test
    fun testCancelFailedOnStarkSignature() {
        starkSignatureFuture.completeExceptionally(ImmutableException())

        cancel()

        assert(completed)
        assert(throwable != null)
        assert(cancelledOrderId == null)
    }

    @Test
    fun testCancelFailedOnCancelOrder() {
        starkSignatureFuture.complete(SIGNATURE)
        every { ordersApi.cancelOrder(any(), any()) } throws ClientException()

        cancel()

        assert(completed)
        assert(throwable != null)
        assert(cancelledOrderId == null)
    }
}
