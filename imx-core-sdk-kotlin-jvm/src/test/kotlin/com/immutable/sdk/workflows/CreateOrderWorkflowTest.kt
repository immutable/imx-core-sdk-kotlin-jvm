package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.model.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
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
private const val STARK_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ETH_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1a"
private const val PAYLOAD_HASH = "payloadHash"
private const val SIGNABLE_MESSAGE = "messageForL1"

class CreateOrderWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    @MockK
    private lateinit var response: CreateOrderResponse

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

        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse(
            assetIdSell = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02",
            assetIdBuy = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb01",
            vaultIdSell = 1_502_450_104,
            vaultIdBuy = 1_502_450_105,
            amountSell = "1",
            amountBuy = "10100000000000000",
            nonce = 596_252_354,
            expirationTimestamp = 1_325_907,
            starkKey = STARK_KEY,
            payloadHash = PAYLOAD_HASH,
            signableMessage = SIGNABLE_MESSAGE
        )
        every {
            ordersApi.createOrder(any(), any(), any())
        } returns response

        mockkObject(StarkKey)
        every { StarkKey.sign(any(), any()) } returns STARK_SIGNATURE
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createSellFuture() = createOrder(
        asset = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
        sellToken = EthAsset(SELL_AMOUNT),
        fees = emptyList(),
        signer = signer,
        starkSigner = starkSigner,
        ordersApi = ordersApi
    )

    @Test
    fun testSellInEthSuccess() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        testFuture(
            future = createSellFuture(),
            expectedResult = response,
            expectedError = null
        )

        val slot = slot<CreateOrderRequest>()
        verify { ordersApi.createOrder(capture(slot), ADDRESS, ETH_SIGNATURE) }
        assert(slot.captured.starkSignature == STARK_SIGNATURE)
    }

    @Test
    fun testSellInErc20Success() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        testFuture(
            future = createOrder(
                asset = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
                sellToken = Erc20Asset(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS, SELL_AMOUNT),
                fees = emptyList(),
                signer = signer,
                starkSigner = starkSigner,
                ordersApi = ordersApi
            ),
            expectedResult = response,
            expectedError = null
        )

        val amountBuy = "10100"
        verify {
            ordersApi.getSignableOrder(
                GetSignableOrderRequest(
                    amountBuy = amountBuy,
                    amountSell = "1",
                    tokenBuy = SignableToken(
                        type = TokenType.ERC20.name,
                        data = SignableTokenData(
                            tokenAddress = SELL_TOKEN_ADDRESS,
                            decimals = SELL_TOKEN_DECIMALS
                        )
                    ),
                    tokenSell = SignableToken(
                        data = SignableTokenData(
                            tokenId = TOKEN_ID,
                            tokenAddress = TOKEN_ADDRESS
                        ),
                        type = TokenType.ERC721.name
                    ),
                    user = ADDRESS,
                    fees = emptyList()
                )
            )
        }
    }

    @Test
    fun testSellFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testSellFailedOnGetSignableOrder() {
        addressFuture.complete(ADDRESS)
        every { ordersApi.getSignableOrder(any()) } throws ClientException()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testSellFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testSellFailedOnEthSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testSellFailedOnCreateOrder() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)
        every { ordersApi.createOrder(any(), any(), any()) } throws ClientException()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
