package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
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
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class BuyWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var tradesApi: TradesApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Suppress("UnderscoresInNumericLiterals")
    @Test
    fun testBuySuccess() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture
        every {
            ordersApi.getOrder(
                "5",
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
            vaultIdSell = 1502450104,
            vaultIdBuy = 1502450105,
            amountSell = "10100000000000000",
            amountBuy = "1",
            nonce = 639749977,
            expirationTimestamp = 1325765,
            starkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
        )
        every { tradesApi.createTrade(any()) } returns CreateTradeResponse(tradeId = 6)

        val latch = CountDownLatch(1)
        val future = buy("5", signer, starkSigner, ordersApi, tradesApi)
        var throwable: Throwable? = null
        var tradeId: Int? = null
        var completed = false

        future.whenComplete { id, error ->
            completed = true
            tradeId = id
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable == null)
        assert(tradeId == 6)
    }

    @Test
    fun testBuyFailedOnAddress() {
        val addressFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture

        val latch = CountDownLatch(1)
        val future = buy("5", signer, starkSigner, ordersApi, tradesApi)
        var throwable: Throwable? = null
        var tradeId: Int? = null
        var completed = false

        future.whenComplete { id, error ->
            completed = true
            tradeId = id
            throwable = error
            latch.countDown()
        }
        addressFuture.completeExceptionally(ImmutableException(""))
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable != null)
        assert(tradeId == null)
    }

    @Suppress("UnderscoresInNumericLiterals")
    @Test
    fun testBuyFailedOnGetOrder() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"

        val addressFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every {
            ordersApi.getOrder(
                "5",
                true,
                null,
                null
            )
        } throws ClientException()

        val latch = CountDownLatch(1)
        val future = buy("5", signer, starkSigner, ordersApi, tradesApi)
        var throwable: Throwable? = null
        var tradeId: Int? = null
        var completed = false

        future.whenComplete { id, error ->
            completed = true
            tradeId = id
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable != null)
        assert(tradeId == null)
    }

    @Suppress("UnderscoresInNumericLiterals")
    @Test
    fun testBuyFailedOnGetSignableOrder() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"

        val addressFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every {
            ordersApi.getOrder(
                "5",
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
        every { ordersApi.getSignableOrder(any()) } throws ClientException()

        val latch = CountDownLatch(1)
        val future = buy("5", signer, starkSigner, ordersApi, tradesApi)
        var throwable: Throwable? = null
        var tradeId: Int? = null
        var completed = false

        future.whenComplete { id, error ->
            completed = true
            tradeId = id
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable != null)
        assert(tradeId == null)
    }

    @Suppress("UnderscoresInNumericLiterals")
    @Test
    fun testBuyFailedOnStarkSignature() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture
        every {
            ordersApi.getOrder(
                "5",
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
            vaultIdSell = 1502450104,
            vaultIdBuy = 1502450105,
            amountSell = "10100000000000000",
            amountBuy = "1",
            nonce = 639749977,
            expirationTimestamp = 1325765,
            starkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
        )

        val latch = CountDownLatch(1)
        val future = buy("5", signer, starkSigner, ordersApi, tradesApi)
        var throwable: Throwable? = null
        var tradeId: Int? = null
        var completed = false

        future.whenComplete { id, error ->
            completed = true
            tradeId = id
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.completeExceptionally(ImmutableException())
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable != null)
        assert(tradeId == null)
    }

    @Suppress("UnderscoresInNumericLiterals")
    @Test
    fun testBuyFailedOnStarkSignatureInvalidSignableResponse() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture
        every {
            ordersApi.getOrder(
                "5",
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
        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse()

        val latch = CountDownLatch(1)
        val future = buy("5", signer, starkSigner, ordersApi, tradesApi)
        var throwable: Throwable? = null
        var tradeId: Int? = null
        var completed = false

        future.whenComplete { id, error ->
            completed = true
            tradeId = id
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.completeExceptionally(ImmutableException())
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable != null)
        assert(tradeId == null)
    }

    @Suppress("UnderscoresInNumericLiterals")
    @Test
    fun testBuyFailedOnCreateTrade() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { starkSigner.starkSign(any()) } returns starkSignatureFuture
        every {
            ordersApi.getOrder(
                "5",
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
            vaultIdSell = 1502450104,
            vaultIdBuy = 1502450105,
            amountSell = "10100000000000000",
            amountBuy = "1",
            nonce = 639749977,
            expirationTimestamp = 1325765,
            starkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
        )
        every { tradesApi.createTrade(any()) } throws ClientException()

        val latch = CountDownLatch(1)
        val future = buy("5", signer, starkSigner, ordersApi, tradesApi)
        var throwable: Throwable? = null
        var tradeId: Int? = null
        var completed = false

        future.whenComplete { id, error ->
            completed = true
            tradeId = id
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable != null)
        assert(tradeId == null)
    }
}
