package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.TestException
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.CancelOrderResponse
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.testFuture
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture

private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ORDER_ID = 4511

class CancelWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var starkSigner: StarkSigner

    @MockK
    private lateinit var cancelOrderResponse: CancelOrderResponse

    @MockK
    private lateinit var ecKeyPair: ECKeyPair

    private lateinit var starkKeysFuture: CompletableFuture<ECKeyPair>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        starkKeysFuture = CompletableFuture<ECKeyPair>()
        every { starkSigner.getStarkKeys() } returns starkKeysFuture

        mockkObject(StarkCurve)
        every { StarkCurve.sign(any(), any()) } returns SIGNATURE
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createCancelFuture() = cancel(
        orderId = ORDER_ID.toString(),
        starkSigner = starkSigner,
        ordersApi = ordersApi
    )

    @Test
    fun testCancelSuccess() {
        starkKeysFuture.complete(ecKeyPair)
        every { ordersApi.cancelOrder(any(), any(), any(), any()) } returns cancelOrderResponse
        every { cancelOrderResponse.orderId } returns ORDER_ID

        testFuture(
            future = createCancelFuture(),
            expectedResult = ORDER_ID,
            expectedError = null
        )
    }

    @Test
    fun testCancelFailedOnStarkSignature() {
        starkKeysFuture.completeExceptionally(TestException())

        testFuture(
            future = createCancelFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testCancelFailedOnCancelOrder() {
        starkKeysFuture.complete(ecKeyPair)
        every { ordersApi.cancelOrder(any(), any(), any(), any()) } throws ClientException()

        testFuture(
            future = createCancelFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
