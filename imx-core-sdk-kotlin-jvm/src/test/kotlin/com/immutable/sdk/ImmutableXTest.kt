package com.immutable.sdk

import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.WithdrawalsApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.createTrade
import com.immutable.sdk.workflows.buyCrypto
import com.immutable.sdk.workflows.cancelOrder
import com.immutable.sdk.workflows.registerOffChain
import com.immutable.sdk.workflows.createOrder
import com.immutable.sdk.workflows.transfer
import com.immutable.sdk.workflows.deposit
import com.immutable.sdk.workflows.completeWithdrawal
import com.immutable.sdk.workflows.isRegisteredOnChain
import com.immutable.sdk.workflows.withdrawal.prepareWithdrawal
import com.immutable.sdk.workflows.TransferData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import org.web3j.tx.gas.DefaultGasProvider
import java.util.*
import java.util.concurrent.CompletableFuture

private const val API_URL = "url"
private const val NODE_URL = "alchemy"
private const val TRANSACTION_HASH = "0xtransactionHash"
private const val ADDRESS = "0x4Ce91391E915aa274bBBeEC39639C5b6238D8268"
private const val TOKEN_ADDRESS = "0xaee5c0826ba5523c9f0eee40da69befa12b3d97f"
private const val TOKEN_ID = "541"
private const val AMOUNT = "0.02"
private const val STARK_ADDRESS = "0xabcd3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val ID = "690"

class ImmutableXTest {

    @MockK
    private lateinit var properties: Properties

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    @MockK
    private lateinit var gasProvider: DefaultGasProvider

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

        sdk = spyk(ImmutableX(ImmutableXBase.Ropsten, NODE_URL))
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
        mockkStatic(::createTrade)
        every {
            createTrade(
                "orderId",
                listOf(FeeEntry("address", 5.0)),
                signer,
                starkSigner,
                any(),
                any()
            )
        } returns future
        assertEquals(
            future,
            sdk.createTrade("orderId", listOf(FeeEntry("address", 5.0)), signer, starkSigner)
        )
    }

    @Test
    fun testSell() {
        val future = CompletableFuture<CreateOrderResponse>()
        val asset = Erc721Asset("address", "id")
        val sellToken = EthAsset("1")
        mockkStatic(::createOrder)
        every {
            createOrder(
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
            sdk.createOrder(
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
        every {
            transfer(
                listOf(TransferData(asset, "recipientAddress")),
                signer,
                starkSigner,
                any()
            )
        } returns future
        assertEquals(
            future,
            sdk.transfer(TransferData(asset, "recipientAddress"), signer, starkSigner)
        )
    }

    @Test
    fun testBuyCrypto() {
        val future = CompletableFuture<String>()
        mockkStatic(::buyCrypto)
        every { buyCrypto(any(), signer, any(), "colorCode", any()) } returns future
        assertEquals(future, sdk.buyCrypto(signer, "colorCode"))
    }

    @Test
    fun testDeposit() {
        mockkStatic(::deposit)
        every {
            deposit(any(), any(), any(), any(), any(), any(), any(), any())
        } returns CompletableFuture.completedFuture(TRANSACTION_HASH)

        assertEquals(TRANSACTION_HASH, sdk.deposit(EthAsset(AMOUNT), signer, gasProvider).get())
    }

    @Test(expected = IllegalStateException::class)
    fun testDeposit_noNodeUrlSet() {
        sdk = spyk(ImmutableX(ImmutableXBase.Sandbox))

        sdk.deposit(EthAsset(AMOUNT), signer, gasProvider).get()
    }

    @Test
    fun testGetDeposit() {
        val deposit = mockk<Deposit>()
        mockkConstructor(DepositsApi::class)
        every { anyConstructed<DepositsApi>().getDeposit(any()) } returns deposit

        assertEquals(deposit, sdk.getDeposit(ID))
        verify { anyConstructed<DepositsApi>().getDeposit(ID) }
    }

    @Test(expected = ImmutableException::class)
    fun testApiCallError() {
        mockkConstructor(DepositsApi::class)
        every { anyConstructed<DepositsApi>().getDeposit(any()) } throws ClientException()

        sdk.getDeposit(ID)
    }

    @Test
    fun testListDeposits() {
        val response = mockk<ListDepositsResponse>()
        mockkConstructor(DepositsApi::class)
        every { anyConstructed<DepositsApi>().listDeposits(tokenAddress = TOKEN_ADDRESS) } returns response

        assertEquals(response, sdk.listDeposits(tokenAddress = TOKEN_ADDRESS))
        verify { anyConstructed<DepositsApi>().listDeposits(tokenAddress = TOKEN_ADDRESS) }
    }

    @Test
    fun testListWithdrawals() {
        val response = mockk<ListWithdrawalsResponse>()
        mockkConstructor(WithdrawalsApi::class)
        every { anyConstructed<WithdrawalsApi>().listWithdrawals(user = ADDRESS) } returns response

        assertEquals(response, sdk.listWithdrawals(user = ADDRESS))
        verify { anyConstructed<WithdrawalsApi>().listWithdrawals(user = ADDRESS) }
    }

    @Test
    fun testGetWithdrawal() {
        val response = mockk<Withdrawal>()
        mockkConstructor(WithdrawalsApi::class)
        every { anyConstructed<WithdrawalsApi>().getWithdrawal(any()) } returns response

        assertEquals(response, sdk.getWithdrawal(ID))
        verify { anyConstructed<WithdrawalsApi>().getWithdrawal(ID) }
    }

    @Test
    fun testPrepareWithdrawal() {
        val response = mockk<CreateWithdrawalResponse>()
        mockkStatic(::prepareWithdrawal)
        every {
            prepareWithdrawal(any(), any(), any(), any())
        } returns CompletableFuture.completedFuture(response)

        assertEquals(
            response,
            sdk.prepareWithdrawal(Erc721Asset(TOKEN_ADDRESS, TOKEN_ID), signer, starkSigner).get()
        )
    }

    @Test
    fun testWithdraw() {
        mockkStatic(::completeWithdrawal)
        every {
            completeWithdrawal(any(), any(), any(), any(), any(), any(), any(), any(), any())
        } returns CompletableFuture.completedFuture(TRANSACTION_HASH)

        assertEquals(
            TRANSACTION_HASH,
            sdk.completeWithdrawal(Erc721Asset(TOKEN_ADDRESS, TOKEN_ID), signer, STARK_ADDRESS, gasProvider).get()
        )
    }

    @Test(expected = IllegalStateException::class)
    fun testWithdraw_noNodeUrlSet() {
        sdk = spyk(ImmutableX(ImmutableXBase.Sandbox))

        sdk.completeWithdrawal(
            Erc721Asset(TOKEN_ADDRESS, TOKEN_ID), signer, STARK_ADDRESS, gasProvider
        ).get()
    }

    @Test
    fun testIsRegisteredOnChain() {
        mockkStatic(::isRegisteredOnChain)
        every {
            isRegisteredOnChain(any(), any(), any(), any(), any())
        } returns CompletableFuture.completedFuture(true)

        assertEquals(true, sdk.isRegisteredOnChain(signer, gasProvider).get())
    }

    @Test(expected = IllegalStateException::class)
    fun testIsRegisteredOnChain_noNodeUrlSet() {
        sdk = spyk(ImmutableX(ImmutableXBase.Sandbox))

        sdk.isRegisteredOnChain(signer, gasProvider).get()
    }

    @Test
    fun testGetUser() {
        val response = mockk<GetUsersApiResponse>()
        mockkConstructor(UsersApi::class)
        every { anyConstructed<UsersApi>().getUsers(ADDRESS) } returns response

        assertEquals(response, sdk.getUser(ADDRESS))
        verify { anyConstructed<UsersApi>().getUsers(ADDRESS) }
    }
}
