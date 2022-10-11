package com.immutable.sdk

import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.buy
import com.immutable.sdk.workflows.buyCrypto
import com.immutable.sdk.workflows.cancelOrder
import com.immutable.sdk.workflows.registerOffChain
import com.immutable.sdk.workflows.sell
import com.immutable.sdk.workflows.transfer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.CompletableFuture

private const val API_URL = "url"

class ImmutableXTest {

    @MockK
    private lateinit var properties: Properties

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    private lateinit var sdk: ImmutableX

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockkObject(ImmutableConfig)
        every { ImmutableConfig.getPublicApiUrl(any()) } returns API_URL

        mockkStatic(System::class)
        every { System.getProperties() } returns properties
        every { properties.setProperty(any(), any()) } returns mockk()
        every { properties.getProperty(any(), any()) } returns ""

        sdk = spyk(ImmutableX(ImmutableXBase.Ropsten))
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testInit() {
        verify { ImmutableConfig.getPublicApiUrl(ImmutableXBase.Ropsten) }
        verify { properties.setProperty(KEY_BASE_URL, API_URL) }
    }

    @Test
    fun testSetHttpLoggingLevel() {
        assertEquals(ImmutableXHttpLoggingLevel.None, ImmutableX.httpLoggingLevel)
        sdk.setHttpLoggingLevel(ImmutableXHttpLoggingLevel.Body)
        assertEquals(ImmutableXHttpLoggingLevel.Body, ImmutableX.httpLoggingLevel)
    }

    @Test
    fun testRegisterOffChain() {
        val future = CompletableFuture<Unit>()
        mockkStatic(::registerOffChain)
        every { registerOffChain(signer, starkSigner, any()) } returns future
        assertEquals(future, sdk.registerOffChain(signer, starkSigner))
    }

    @Test
    fun testBuy() {
        val future = CompletableFuture<CreateTradeResponse>()
        mockkStatic(::buy)
        every { buy("orderId", listOf(FeeEntry("address", 5.0)), signer, starkSigner, any(), any()) } returns future
        assertEquals(future, sdk.buy("orderId", listOf(FeeEntry("address", 5.0)), signer, starkSigner))
    }

    @Test
    fun testSell() {
        val future = CompletableFuture<CreateOrderResponse>()
        val asset = Erc721Asset("address", "id")
        val sellToken = EthAsset("1")
        mockkStatic(::sell)
        every {
            sell(
                asset,
                sellToken,
                listOf(FeeEntry("address", 5.0)),
                signer,
                starkSigner,
                any()
            )
        } returns future
        assertEquals(
            future,
            sdk.sell(
                asset,
                sellToken,
                listOf(FeeEntry("address", 5.0)),
                signer,
                starkSigner
            )
        )
    }

    @Test
    fun testCancelOrder() {
        val future = CompletableFuture<CancelOrderResponse>()
        mockkStatic(::cancelOrder)
        every { cancelOrder("orderId", signer, starkSigner, any()) } returns future
        assertEquals(future, sdk.cancelOrder("orderId", signer, starkSigner))
    }

    @Test
    fun testTransfer() {
        val future = CompletableFuture<CreateTransferResponse>()
        val asset = Erc721Asset("address", "id")
        mockkStatic(::transfer)
        every { transfer(asset, "recipientAddress", signer, starkSigner, any()) } returns future
        assertEquals(
            future,
            sdk.transfer(asset, "recipientAddress", signer, starkSigner)
        )
    }

    @Test
    fun testBuyCrypto() {
        val future = CompletableFuture<String>()
        mockkStatic(::buyCrypto)
        every { buyCrypto(any(), signer, any(), "colorCode", any()) } returns future
        assertEquals(future, sdk.buyCrypto(signer, "colorCode"))
    }
}
