package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.Constants.ERC721_AMOUNT
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.TradesApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.model.OrderStatus
import com.immutable.sdk.model.TokenType
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val ASSET_OWNER = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7d"
private const val STARK_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ETH_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1a"
private const val ORDER_ID = "5"
private const val TRADE_ID = 6
private const val PAYLOAD_HASH = "tradePayloadHash"
private const val SIGNABLE_MESSAGE = "messageForL1"

class BuyWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var tradesApi: TradesApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    @MockK
    private lateinit var order: Order

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

        every {
            ordersApi.getOrder(ORDER_ID, true, "", "")
        } returns order
        every { order.orderId } returns ORDER_ID.toInt()
        every { order.user } returns ASSET_OWNER
        every { order.status } returns OrderStatus.Active.value
        every { order.buy } returns Token(
            TokenData(
                quantity = "200000000000000",
                decimals = 18,
                quantityWithFees = "200000000000000"
            ),
            TokenType.ETH.name
        )
        every { order.sell } returns Token(
            TokenData(
                quantity = ERC721_AMOUNT,
                tokenId = "11",
                tokenAddress = "0x6ee5c0836ba5523c9f0eee40da69befa30b3d97e",
                quantityWithFees = ERC721_AMOUNT
            ),
            TokenType.ERC721.name
        )

        every { tradesApi.getSignableTrade(any()) } returns GetSignableTradeResponse(
            assetIdSell = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02",
            assetIdBuy = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb01",
            vaultIdSell = 1_502_450_104,
            vaultIdBuy = 1_502_450_105,
            amountSell = "10100000000000000",
            amountBuy = "1",
            nonce = 639_749_977,
            expirationTimestamp = 1_325_765,
            starkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485",
            payloadHash = PAYLOAD_HASH,
            signableMessage = SIGNABLE_MESSAGE
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testBuySuccess() {
        val response = CreateTradeResponse(tradeId = TRADE_ID, status = OrderStatus.Filled.value)
        val slot = slot<CreateTradeRequestV1>()
        every {
            tradesApi.createTrade(
                capture(slot),
                ADDRESS,
                ETH_SIGNATURE
            )
        } returns response
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = response,
            expectedError = null
        )

        assert(slot.captured.starkSignature == STARK_SIGNATURE)
    }

    @Test
    fun testBuyFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testBuyFailedOnGetOrder() {
        every { ordersApi.getOrder(any(), any(), any(), any()) } throws ClientException()
        addressFuture.complete(ADDRESS)

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testBuyFailedOnGetSignableTrade() {
        every { tradesApi.getSignableTrade(any()) } throws ClientException()
        addressFuture.complete(ADDRESS)

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testBuyFailedOnGetSignableTrade_purchaseOwnOrder() {
        addressFuture.complete(ASSET_OWNER)

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.invalidRequest("")
        )
    }

    @Test
    fun testBuyFailedOnGetSignableTrade_notActive() {
        every { order.status } returns OrderStatus.Filled.value
        addressFuture.complete(ADDRESS)

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.invalidRequest("")
        )
    }

    @Test
    fun testBuyFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testBuyFailedOnEthSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testBuyFailedOnCreateTrade() {
        every { tradesApi.createTrade(any(), any(), any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(STARK_SIGNATURE)

        testFuture(
            future = buy(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
