package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.AssetsApi
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.TokenType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import org.web3j.crypto.ECKeyPair
import java.math.BigDecimal
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
    private lateinit var ecKeyPair: ECKeyPair

    @MockK
    private lateinit var asset: Asset

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkKeysFuture: CompletableFuture<ECKeyPair>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture

        starkKeysFuture = CompletableFuture<ECKeyPair>()
        every { starkSigner.getStarkKeys() } returns starkKeysFuture

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
            payloadHash = PAYLOAD_HASH
        )

        every { assetsApi.getAsset(any(), any(), any()) } returns asset
        every { asset.fees } returns arrayListOf()

        mockkObject(StarkCurve)
        every { StarkCurve.sign(any(), any()) } returns SIGNATURE
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
        starkKeysFuture.complete(ecKeyPair)

        every {
            ordersApi.createOrder(any(), any(), any())
        } returns CreateOrderResponse(orderId = ORDER_ID)

        testFuture(
            future = createSellFuture(),
            expectedResult = ORDER_ID,
            expectedError = null
        )
    }

    @Test
    fun testSellInEthSuccess_withRoyaltyFee() {
        addressFuture.complete(ADDRESS)
        starkKeysFuture.complete(ecKeyPair)

        every {
            ordersApi.createOrder(any(), any(), any())
        } returns CreateOrderResponse(orderId = ORDER_ID)
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
                    amountBuy = "10302000000000000",
                    amountSell = "1",
                    tokenBuy = SignableToken(
                        type = TokenType.ETH.name,
                        data = TokenData(decimals = Constants.ETH_DECIMALS)
                    ),
                    tokenSell = SignableToken(
                        data = TokenData(tokenId = TOKEN_ID, tokenAddress = TOKEN_ADDRESS),
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
        starkKeysFuture.complete(ecKeyPair)

        every {
            ordersApi.createOrder(any(), any(), any())
        } returns CreateOrderResponse(orderId = ORDER_ID)

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

        verify {
            ordersApi.getSignableOrder(
                GetSignableOrderRequest(
                    amountBuy = "10100",
                    amountSell = "1",
                    tokenBuy = SignableToken(
                        type = TokenType.ERC20.name,
                        data = TokenData(
                            tokenAddress = SELL_TOKEN_ADDRESS,
                            decimals = SELL_TOKEN_DECIMALS
                        )
                    ),
                    tokenSell = SignableToken(
                        data = TokenData(tokenId = TOKEN_ID, tokenAddress = TOKEN_ADDRESS),
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
        starkKeysFuture.complete(ecKeyPair)

        every {
            ordersApi.createOrder(any(), any(), any())
        } returns CreateOrderResponse(orderId = ORDER_ID)
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
                    amountBuy = "10554",
                    amountSell = "1",
                    tokenBuy = SignableToken(
                        type = TokenType.ERC20.name,
                        data = TokenData(
                            tokenAddress = SELL_TOKEN_ADDRESS,
                            decimals = SELL_TOKEN_DECIMALS
                        )
                    ),
                    tokenSell = SignableToken(
                        data = TokenData(tokenId = TOKEN_ID, tokenAddress = TOKEN_ADDRESS),
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
        starkKeysFuture.complete(ecKeyPair)

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
        starkKeysFuture.completeExceptionally(TestException())

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testSellFailedOnStarkSignatureInvalidSignableResponse() {
        addressFuture.complete(ADDRESS)
        starkKeysFuture.completeExceptionally(TestException())
        every { ordersApi.getSignableOrder(any()) } returns GetSignableOrderResponse()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testSellFailedOnCreateOrder() {
        addressFuture.complete(ADDRESS)
        starkKeysFuture.complete(ecKeyPair)
        every { ordersApi.createOrder(any(), any(), any()) } throws ClientException()

        testFuture(
            future = createSellFuture(),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testFormatAmount() {
        assertEquals("81000000000000000", formatAmount(EthAsset("0.081"), BigDecimal.ZERO))
        assertEquals(
            "55000000",
            formatAmount(
                Erc20Asset(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS, "55"),
                BigDecimal.ZERO
            )
        )
        assertEquals(
            "2000000",
            formatAmount(
                Erc20Asset(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS, "1"),
                BigDecimal.ONE
            )
        )
    }
}
