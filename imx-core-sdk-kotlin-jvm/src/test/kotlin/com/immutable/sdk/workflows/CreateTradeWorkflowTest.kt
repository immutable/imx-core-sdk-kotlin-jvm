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
private const val RECIPIENT_ADDRESS1 = "0xa76e3eeb2f7143165618ab8feaabcd395b6f1234"
private const val RECIPIENT_ADDRESS2 = "0xa76e3eeb2f7143165618ab8feaabcd395b6fabcd"
private const val FEE_PERCENTAGE1 = 5.0
private const val FEE_PERCENTAGE2 = 2.5
private const val STARK_KEY = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
private const val ASSET_ID_SELL = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02"
private const val ASSET_ID_BUY = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb01"
private const val VAULT_ID_SELL = 1_502_450_104
private const val VAULT_ID_BUY = 1_502_450_105
private const val AMOUNT_SELL = "10100000000000000"
private const val AMOUNT_BUY = "1"
private const val NONCE = 639_749_977
private const val EXPIRATION_TIMESTAMP = 1_325_765

class CreateTradeWorkflowTest {
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

    @MockK
    private lateinit var feeInfo: FeeInfo

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
            assetIdSell = ASSET_ID_SELL,
            assetIdBuy = ASSET_ID_BUY,
            vaultIdSell = VAULT_ID_SELL,
            vaultIdBuy = VAULT_ID_BUY,
            amountSell = AMOUNT_SELL,
            amountBuy = AMOUNT_BUY,
            nonce = NONCE,
            expirationTimestamp = EXPIRATION_TIMESTAMP,
            starkKey = STARK_KEY,
            payloadHash = PAYLOAD_HASH,
            signableMessage = SIGNABLE_MESSAGE,
            feeInfo = feeInfo
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
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = response,
            expectedError = null
        )

        assert(slot.captured.starkSignature == STARK_SIGNATURE)
    }

    @Test
    fun testBuyFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testBuyFailedOnGetOrder() {
        every { ordersApi.getOrder(any(), any(), any(), any()) } throws ClientException()
        addressFuture.complete(ADDRESS)

        testFuture(
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testBuyFailedOnGetSignableTrade() {
        every { tradesApi.getSignableTrade(any()) } throws ClientException()
        addressFuture.complete(ADDRESS)

        testFuture(
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testBuyFailedOnGetSignableTrade_purchaseOwnOrder() {
        addressFuture.complete(ASSET_OWNER)

        testFuture(
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.invalidRequest("")
        )
    }

    @Test
    fun testBuyFailedOnGetSignableTrade_notActive() {
        every { order.status } returns OrderStatus.Filled.value
        addressFuture.complete(ADDRESS)

        testFuture(
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.invalidRequest("")
        )
    }

    @Test
    fun testBuyFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
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
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
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
            future = createTrade(ORDER_ID, emptyList(), signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testBuyWithFeesSuccess() {
        every { ordersApi.getOrder(any(), any(), any(), any()) } returns order

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

        val fees = arrayListOf(
            FeeEntry(RECIPIENT_ADDRESS1, FEE_PERCENTAGE1),
            FeeEntry(RECIPIENT_ADDRESS2, FEE_PERCENTAGE2)
        )
        testFuture(
            future = createTrade(ORDER_ID, fees, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = response,
            expectedError = null
        )

        assert(slot.captured.starkSignature == STARK_SIGNATURE)
        verifyOrder {
            signer.getAddress()
            ordersApi.getOrder(
                ORDER_ID,
                true,
                "$FEE_PERCENTAGE1$COMMA$FEE_PERCENTAGE2",
                "$RECIPIENT_ADDRESS1$COMMA$RECIPIENT_ADDRESS2"
            )
            tradesApi.getSignableTrade(GetSignableTradeRequest(ORDER_ID.toInt(), ADDRESS, fees = fees))
            starkSigner.signMessage(PAYLOAD_HASH)
            signer.signMessage(SIGNABLE_MESSAGE)
            tradesApi.createTrade(
                CreateTradeRequestV1(
                    amountBuy = AMOUNT_BUY,
                    amountSell = AMOUNT_SELL,
                    assetIdBuy = ASSET_ID_BUY,
                    assetIdSell = ASSET_ID_SELL,
                    expirationTimestamp = EXPIRATION_TIMESTAMP,
                    nonce = NONCE,
                    orderId = ORDER_ID.toInt(),
                    starkKey = STARK_KEY,
                    starkSignature = STARK_SIGNATURE,
                    vaultIdBuy = VAULT_ID_BUY,
                    vaultIdSell = VAULT_ID_SELL,
                    feeInfo = feeInfo,
                    fees = fees,
                    includeFees = true
                ),
                xImxEthAddress = ADDRESS,
                xImxEthSignature = ETH_SIGNATURE
            )
        }
    }
}
