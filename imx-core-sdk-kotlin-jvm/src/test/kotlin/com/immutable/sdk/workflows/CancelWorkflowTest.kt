package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.CancelOrderResponse
import com.immutable.sdk.api.model.GetSignableCancelOrderResponse
import com.immutable.sdk.crypto.StarkKey
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val STARK_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ETH_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1a"
private const val ORDER_ID = 4511
private const val PAYLOAD_HASH = "cancelPayloadHash"
private const val SIGNABLE_MESSAGE = "signableMessage"

class CancelWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    @MockK
    private lateinit var signableCancelOrderResponse: GetSignableCancelOrderResponse

    @MockK
    private lateinit var cancelOrderResponse: CancelOrderResponse

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkSignatureFuture: CompletableFuture<String>
    private lateinit var ethSignatureFuture: CompletableFuture<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.signMessage(any()) } returns starkSignatureFuture
        ethSignatureFuture = CompletableFuture<String>()
        every { signer.signMessage(any()) } returns ethSignatureFuture

        mockkObject(StarkKey)
        every { StarkKey.sign(any(), any()) } returns STARK_SIGNATURE

        every { ordersApi.getSignableCancelOrder(any()) } returns signableCancelOrderResponse
        every { signableCancelOrderResponse.payloadHash } returns PAYLOAD_HASH
        every { signableCancelOrderResponse.signableMessage } returns SIGNABLE_MESSAGE
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createCancelFuture() = cancel(
        orderId = ORDER_ID.toString(),
        signer = signer,
        starkSigner = starkSigner,
        ordersApi = ordersApi
    )

    @Test
    fun testCancelSuccess() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)
        every { ordersApi.cancelOrder(any(), any(), ADDRESS, ETH_SIGNATURE) } returns cancelOrderResponse

        testFuture(
            future = createCancelFuture(),
            expectedResult = cancelOrderResponse,
            expectedError = null
        )
    }

    @Test
    fun testCancelFailedOnGetAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = createCancelFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testCancelFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = createCancelFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testCancelFailedOnEthSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = createCancelFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testCancelFailedOnGetSignableCancelOrder() {
        addressFuture.complete(ADDRESS)
        every { ordersApi.getSignableCancelOrder(any()) } throws ClientException()

        testFuture(
            future = createCancelFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testCancelFailedOnCancelOrder() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)
        every { ordersApi.cancelOrder(any(), any(), ADDRESS, ETH_SIGNATURE) } throws ClientException()

        testFuture(
            future = createCancelFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
