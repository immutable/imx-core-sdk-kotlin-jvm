package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.TradesApi
import com.immutable.sdk.model.*
import com.immutable.sdk.utils.TokenType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ORDER_ID = "5"
private const val TRADE_ID = 6

class BuyWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var tradesApi: TradesApi

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

        every {
            ordersApi.getOrder(
                ORDER_ID,
                true,
                null,
                null
            )
        } returns Order(
            buy = Token(TokenData(quantity = "200000000000000", decimals = 18), TokenType.ETH.name),
            sell = Token(
                TokenData(
                    quantity = "1",
                    tokenId = "11",
                    tokenAddress = "0x6ee5c0836ba5523c9f0eee40da69befa30b3d97e"
                ),
                TokenType.ERC721.name
            ),
            status = "active",
            user = "0xb76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        )

        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse(
            assetIdSell = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02",
            assetIdBuy = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb01",
            vaultIdSell = 1_502_450_104,
            vaultIdBuy = 1_502_450_105,
            amountSell = "10100000000000000",
            amountBuy = "1",
            nonce = 639_749_977,
            expirationTimestamp = 1_325_765,
            starkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
        )
    }

    @Test
    fun testBuySuccess() {
        every { tradesApi.createTrade(any()) } returns CreateTradeResponse(tradeId = TRADE_ID)
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = buy(ORDER_ID, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = TRADE_ID,
            expectedError = null
        )
    }

    @Test
    fun testBuyFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = buy(ORDER_ID, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testBuyFailedOnGetOrder() {
        every {
            ordersApi.getOrder(
                ORDER_ID,
                true,
                null,
                null
            )
        } throws ClientException()
        addressFuture.complete(ADDRESS)

        testFuture(
            future = buy(ORDER_ID, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Suppress("UnderscoresInNumericLiterals")
    @Test
    fun testBuyFailedOnGetSignableOrder() {
        every { ordersApi.getSignableOrder(any()) } throws ClientException()
        addressFuture.complete(ADDRESS)

        testFuture(
            future = buy(ORDER_ID, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testBuyFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = buy(ORDER_ID, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testBuyFailedOnStarkSignatureInvalidSignableResponse() {
        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse()

        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(ImmutableException.invalidResponse(""))

        testFuture(
            future = buy(ORDER_ID, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.invalidResponse("")
        )
    }

    @Test
    fun testBuyFailedOnCreateTrade() {
        every { tradesApi.createTrade(any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = buy(ORDER_ID, signer, starkSigner, ordersApi, tradesApi),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
