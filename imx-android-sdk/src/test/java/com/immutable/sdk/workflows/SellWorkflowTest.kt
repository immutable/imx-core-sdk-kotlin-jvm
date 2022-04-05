package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.model.CreateOrderResponse
import com.immutable.sdk.model.GetSignableOrderResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private const val LATCH_TIME_OUT_MS = 3000L
private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val TOKEN_ADDRESS = "0x6ee5c0826ba5523c9f0eee40da69befa30b3d97f"
private const val TOKEN_ID = "9"
private const val SELL_AMOUNT = "0.0101"
private const val SELL_TOKEN_ADDRESS = "0x07865c6e87b9f70255377e024ace6630c1eaa37f"
private const val SELL_TOKEN_DECIMALS = 6
private const val STARK_KEY = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ORDER_ID = 8426

class SellWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkSignatureFuture: CompletableFuture<String>
    private var throwable: Throwable? = null
    private var orderId: Int? = null
    private var completed = false

    private lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture

        starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture

        latch = CountDownLatch(1)

        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse(
            assetIdSell = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02",
            assetIdBuy = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb01",
            vaultIdSell = 1_502_450_104,
            vaultIdBuy = 1_502_450_105,
            amountSell = "1",
            amountBuy = "10100000000000000",
            nonce = 596_252_354,
            expirationTimestamp = 1_325_907,
            starkKey = STARK_KEY
        )
    }

    private fun sell() {
        val future = sell(
            tokenAddress = TOKEN_ADDRESS,
            tokenId = TOKEN_ID,
            sellTokenAmount = SELL_AMOUNT,
            sellTokenAddress = null,
            sellTokenDecimals = null,
            signer = signer,
            starkSigner = starkSigner,
            ordersApi = ordersApi
        )
        future.whenComplete { id, error ->
            completed = true
            orderId = id
            throwable = error
            latch.countDown()
        }
        latch.await(LATCH_TIME_OUT_MS, TimeUnit.MILLISECONDS)
    }

    @Test
    fun testSellInEthSuccess() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        every { ordersApi.createOrder(any()) } returns CreateOrderResponse(orderId = ORDER_ID)

        sell()

        assert(completed)
        assert(throwable == null)
        assert(orderId == ORDER_ID)
    }

    @Test
    fun testSellInErc20Success() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        every { ordersApi.createOrder(any()) } returns CreateOrderResponse(orderId = ORDER_ID)

        val future = sell(
            TOKEN_ADDRESS,
            TOKEN_ID,
            SELL_AMOUNT,
            SELL_TOKEN_ADDRESS,
            SELL_TOKEN_DECIMALS,
            signer = signer,
            starkSigner = starkSigner,
            ordersApi = ordersApi
        )
        future.whenComplete { id, error ->
            completed = true
            orderId = id
            throwable = error
            latch.countDown()
        }
        latch.await(LATCH_TIME_OUT_MS, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable == null)
        assert(orderId == ORDER_ID)
    }

    @Test
    fun testSellFailedOnAddress() {
        addressFuture.completeExceptionally(ImmutableException(""))

        val starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture
        starkSignatureFuture.complete(SIGNATURE)

        sell()

        assert(completed)
        assert(throwable != null)
        assert(orderId == null)
    }

    @Test
    fun testSellFailedOnGetSignableOrder() {
        addressFuture.complete(ADDRESS)
        every { ordersApi.getSignableOrder(any()) } throws ClientException()

        sell()

        assert(completed)
        assert(throwable != null)
        assert(orderId == null)
    }

    @Test
    fun testSellFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(ImmutableException())

        sell()

        assert(completed)
        assert(throwable != null)
        assert(orderId == null)
    }

    @Test
    fun testSellFailedOnStarkSignatureInvalidSignableResponse() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(ImmutableException())
        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse()

        sell()

        assert(completed)
        assert(throwable != null)
        assert(orderId == null)
    }

    @Test
    fun testSellFailedOnCreateOrder() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        every { ordersApi.createOrder(any()) } throws ClientException()

        sell()

        assert(completed)
        assert(throwable != null)
        assert(orderId == null)
    }

    @Test
    fun testConvertAmount() {
        assertEquals(convertAmount("0.081", null), "81000000000000000")
        assertEquals(convertAmount("55", 6), "55000000")
    }
}
