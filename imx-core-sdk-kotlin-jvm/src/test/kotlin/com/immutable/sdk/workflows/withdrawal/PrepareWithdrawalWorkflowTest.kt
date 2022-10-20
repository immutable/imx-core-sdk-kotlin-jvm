package com.immutable.sdk.workflows.withdrawal

import com.immutable.sdk.*
import com.immutable.sdk.Constants.ERC721_AMOUNT
import com.immutable.sdk.api.WithdrawalsApi
import com.immutable.sdk.api.model.CreateWithdrawalRequest
import com.immutable.sdk.api.model.CreateWithdrawalResponse
import com.immutable.sdk.api.model.GetSignableWithdrawalRequest
import com.immutable.sdk.api.model.GetSignableWithdrawalResponse
import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val TOKEN_ADDRESS = "0x6ff5c0826ba5523c9f0eee40da69befa30b3d97f"
private const val TOKEN_ID = "8"
private const val TOKEN_DECIMALS = 6
private const val AMOUNT = "1"
private const val ASSET_ID = "0x008a91cd9a0dc267c1ae9e92497eb92068e3101d8cf7a93007362f944679b991"
private const val VAULT_ID = 195_710
private const val STARK_KEY = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
private const val STARK_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ETH_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1a"
private const val PAYLOAD_HASH = "payloadHash"
private const val SIGNABLE_MESSAGE = "messageForL1"
private const val NONCE = 596_252_123

class PrepareWithdrawalWorkflowTest {
    @MockK
    private lateinit var withdrawalsApi: WithdrawalsApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    @MockK
    private lateinit var response: CreateWithdrawalResponse

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

        every { withdrawalsApi.getSignableWithdrawal(any()) } returns GetSignableWithdrawalResponse(
            amount = AMOUNT,
            assetId = ASSET_ID,
            nonce = NONCE,
            payloadHash = PAYLOAD_HASH,
            signableMessage = SIGNABLE_MESSAGE,
            starkKey = STARK_KEY,
            vaultId = VAULT_ID
        )
        every {
            withdrawalsApi.createWithdrawal(any(), any(), any())
        } returns response

        mockkObject(StarkKey)
        every { StarkKey.sign(any(), any()) } returns STARK_SIGNATURE
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun prepareWithdrawalFuture(token: AssetModel) = prepareWithdrawal(
        token = token,
        signer = signer,
        starkSigner = starkSigner,
        withdrawalsApi = withdrawalsApi
    )

    @Test
    fun testPrepareWithdrawalSuccess_erc721() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        val token = Erc721Asset(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID)
        testFuture(
            future = prepareWithdrawalFuture(token),
            expectedResult = response,
            expectedError = null
        )

        verify {
            withdrawalsApi.getSignableWithdrawal(
                GetSignableWithdrawalRequest(AMOUNT, token.toSignableToken(), ADDRESS)
            )
        }
        verify { starkSigner.signMessage(PAYLOAD_HASH) }
        verify { signer.signMessage(SIGNABLE_MESSAGE) }
        verify {
            withdrawalsApi.createWithdrawal(
                CreateWithdrawalRequest(
                    amount = ERC721_AMOUNT,
                    assetId = ASSET_ID,
                    nonce = NONCE,
                    starkKey = STARK_KEY,
                    starkSignature = STARK_SIGNATURE,
                    vaultId = VAULT_ID
                ),
                xImxEthAddress = ADDRESS,
                xImxEthSignature = ETH_SIGNATURE
            )
        }
    }

    @Test
    fun testPrepareWithdrawalSuccess_erc20() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        val token =
            Erc20Asset(tokenAddress = TOKEN_ADDRESS, decimals = TOKEN_DECIMALS, quantity = AMOUNT)
        testFuture(
            future = prepareWithdrawalFuture(token),
            expectedResult = response,
            expectedError = null
        )

        val amount = "1000000"
        verify {
            withdrawalsApi.getSignableWithdrawal(
                GetSignableWithdrawalRequest(amount, token.toSignableToken(), ADDRESS)
            )
        }
        verify { starkSigner.signMessage(PAYLOAD_HASH) }
        verify { signer.signMessage(SIGNABLE_MESSAGE) }
        verify {
            withdrawalsApi.createWithdrawal(
                CreateWithdrawalRequest(
                    amount = "1000000",
                    assetId = ASSET_ID,
                    nonce = NONCE,
                    starkKey = STARK_KEY,
                    starkSignature = STARK_SIGNATURE,
                    vaultId = VAULT_ID
                ),
                xImxEthAddress = ADDRESS,
                xImxEthSignature = ETH_SIGNATURE
            )
        }
    }

    @Test
    fun testPrepareWithdrawalSuccess_eth() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        val token = EthAsset(quantity = AMOUNT)
        testFuture(
            future = prepareWithdrawalFuture(token),
            expectedResult = response,
            expectedError = null
        )

        val amount = "1000000000000000000"
        verify {
            withdrawalsApi.getSignableWithdrawal(
                GetSignableWithdrawalRequest(amount, token.toSignableToken(), ADDRESS)
            )
        }
        verify { starkSigner.signMessage(PAYLOAD_HASH) }
        verify { signer.signMessage(SIGNABLE_MESSAGE) }
        verify {
            withdrawalsApi.createWithdrawal(
                CreateWithdrawalRequest(
                    amount = amount,
                    assetId = ASSET_ID,
                    nonce = NONCE,
                    starkKey = STARK_KEY,
                    starkSignature = STARK_SIGNATURE,
                    vaultId = VAULT_ID
                ),
                xImxEthAddress = ADDRESS,
                xImxEthSignature = ETH_SIGNATURE
            )
        }
    }

    @Test
    fun testPrepareWithdrawalFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = prepareWithdrawalFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testPrepareWithdrawalFailedOnGetSignableWithdrawal() {
        addressFuture.complete(ADDRESS)
        every { withdrawalsApi.getSignableWithdrawal(any()) } throws ClientException()

        testFuture(
            future = prepareWithdrawalFuture(Erc20Asset(TOKEN_ADDRESS, TOKEN_DECIMALS, AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testPrepareWithdrawalFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(RuntimeException())

        testFuture(
            future = prepareWithdrawalFuture(Erc721Asset(TOKEN_ADDRESS, TOKEN_ID)),
            expectedResult = null,
            expectedError = RuntimeException()
        )
    }

    @Test
    fun testPrepareWithdrawalFailedOnEthSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = prepareWithdrawalFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testPrepareWithdrawalFailedOnCreateOrder() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)
        every { withdrawalsApi.createWithdrawal(any(), any(), any()) } throws ClientException()

        testFuture(
            future = prepareWithdrawalFuture(Erc20Asset(TOKEN_ADDRESS, TOKEN_DECIMALS, AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
