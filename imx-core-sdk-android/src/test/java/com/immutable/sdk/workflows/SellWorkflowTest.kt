package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.AssetsApi
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
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ORDER_ID = 8426
private const val PAYLOAD_HASH = "payloadHash"
private const val SIGNABLE_MESSAGE = "messageForL1"
private const val TIME = 9

class SellWorkflowTest {
    @MockK
    private lateinit var ordersApi: OrdersApi

    @MockK
    private lateinit var assetsApi: AssetsApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    @MockK
    private lateinit var asset: Asset

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var signatureFuture: CompletableFuture<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture

        signatureFuture = CompletableFuture<String>()
        every { starkSigner.signMessage(any()) } returns signatureFuture

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
        } returns CreateOrderResponse(
            orderId = ORDER_ID,
            status = OrderStatus.Active.value,
            time = TIME
        )

        every { assetsApi.getAsset(any(), any(), any()) } returns asset
        every { asset.fees } returns arrayListOf()

        mockkObject(StarkKey)
        every { StarkKey.sign(any(), any()) } returns SIGNATURE
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createSellFuture() = sell(
        asset = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
        sellToken = EthAsset(SELL_AMOUNT),
        fees = emptyList(),
        signer = signer,
        starkSigner = starkSigner,
        ordersApi = ordersApi,
        assetsApi = assetsApi
    )

    @Test
    fun testSellInEthSuccess() {
        addressFuture.complete(ADDRESS)
        signatureFuture.complete(SIGNATURE)

        testFuture(
            future = createSellFuture(),
            expectedResult = ORDER_ID,
            expectedError = null
        )
    }

    @Test
    fun testSellInEthSuccess_withRoyaltyFee() {
        addressFuture.complete(ADDRESS)
        signatureFuture.complete(SIGNATURE)

        every { asset.fees } returns arrayListOf(
            Fee(address = ADDRESS, percentage = 2.0, type = FEE_TYPE_ROYALTY)
        )

        testFuture(
            future = createSellFuture(),
            expectedResult = ORDER_ID,
            expectedError = null
        )

        verify {
            ordersApi.getSignableOrder(
                GetSignableOrderRequest(
                    amountBuy = "10100000000000000",
                    amountSell = "1",
                    tokenBuy = SignableToken(
                        type = TokenType.ETH.name,
                        data = SignableTokenData(
                            decimals = Constants.ETH_DECIMALS
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
    fun testSellInErc20Success() {
        addressFuture.complete(ADDRESS)
        signatureFuture.complete(SIGNATURE)

        testFuture(
            future = sell(
                asset = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
                sellToken = Erc20Asset(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS, SELL_AMOUNT),
                fees = emptyList(),
                signer = signer,
                starkSigner = starkSigner,
                ordersApi = ordersApi,
                assetsApi = assetsApi
            ),
            expectedResult = ORDER_ID,
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
    fun testSellInErc20Success_withRoyaltyAndMakerFee() {
        addressFuture.complete(ADDRESS)
        signatureFuture.complete(SIGNATURE)

        every { asset.fees } returns arrayListOf(
            Fee(address = ADDRESS, percentage = 3.5, type = FEE_TYPE_ROYALTY)
        )

        testFuture(
            future = sell(
                asset = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
                sellToken = Erc20Asset(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS, SELL_AMOUNT),
                fees = arrayListOf(FeeEntry(address = ADDRESS, feePercentage = 1.0)),
                signer = signer,
                starkSigner = starkSigner,
                ordersApi = ordersApi,
                assetsApi = assetsApi
            ),
            expectedResult = ORDER_ID,
            expectedError = null
        )

        verify {
            ordersApi.getSignableOrder(
                GetSignableOrderRequest(
                    amountBuy = "10100",
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
                    fees = arrayListOf(FeeEntry(address = ADDRESS, feePercentage = 1.0))
                )
            )
        }
    }

    @Test
    fun testSellFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())
        signatureFuture.complete(SIGNATURE)

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testSellFailedOnGetAssets() {
        addressFuture.complete(ADDRESS)
        every { assetsApi.getAsset(any(), any(), any()) } throws RuntimeException()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
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
        signatureFuture.completeExceptionally(TestException())

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testSellFailedOnCreateOrder() {
        addressFuture.complete(ADDRESS)
        signatureFuture.complete(SIGNATURE)
        every { ordersApi.createOrder(any(), any(), any()) } throws ClientException()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
