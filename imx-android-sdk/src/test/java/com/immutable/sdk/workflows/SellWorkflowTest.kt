package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.model.CreateOrderResponse
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.GetSignableOrderResponse
import com.immutable.sdk.model.SellToken
import com.immutable.sdk.testFuture
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture

        starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture

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

    private fun createSellFuture() = sell(
        asset = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
        sellAmount = SELL_AMOUNT,
        sellToken = SellToken.ETH,
        signer = signer,
        starkSigner = starkSigner,
        ordersApi = ordersApi
    )

    @Test
    fun testSellInEthSuccess() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        every { ordersApi.createOrder(any()) } returns CreateOrderResponse(orderId = ORDER_ID)

        testFuture(
            future = createSellFuture(),
            expectedResult = ORDER_ID,
            expectedError = null
        )
    }

    @Test
    fun testSellInErc20Success() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        every { ordersApi.createOrder(any()) } returns CreateOrderResponse(orderId = ORDER_ID)

        testFuture(
            future = sell(
                asset = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
                sellAmount = SELL_AMOUNT,
                sellToken = SellToken.ERC20(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS),
                signer = signer,
                starkSigner = starkSigner,
                ordersApi = ordersApi
            ),
            expectedResult = ORDER_ID,
            expectedError = null
        )
    }

    @Test
    fun testSellFailedOnAddress() {
        addressFuture.completeExceptionally(ImmutableException())

        val starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException()
        )
    }

    @Test
    fun testSellFailedOnGetSignableOrder() {
        addressFuture.complete(ADDRESS)
        every { ordersApi.getSignableOrder(any()) } throws ClientException()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException()
        )
    }

    @Test
    fun testSellFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(ImmutableException())

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException()
        )
    }

    @Test
    fun testSellFailedOnStarkSignatureInvalidSignableResponse() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(ImmutableException())
        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException()
        )
    }

    @Test
    fun testSellFailedOnCreateOrder() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        every { ordersApi.createOrder(any()) } throws ClientException()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException()
        )
    }

    @Test
    fun testConvertAmount() {
        assertEquals(convertAmount("0.081", SellToken.ETH), "81000000000000000")
        assertEquals(
            convertAmount(
                "55",
                SellToken.ERC20(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS)
            ),
            "55000000"
        )
    }
}
