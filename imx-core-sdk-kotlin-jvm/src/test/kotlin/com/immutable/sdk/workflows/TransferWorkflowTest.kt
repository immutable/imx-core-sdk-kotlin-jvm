package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.TransfersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7g"
private const val RECIPIENT_ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val STARK_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val ETH_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1a"
private const val SIGNABLE_MESSAGE = "messageForL1"

class TransferWorkflowTest {
    @MockK
    private lateinit var api: TransfersApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkSignatureFuture: CompletableFuture<String>
    private lateinit var ethSignatureFuture: CompletableFuture<String>

    private val transferResponse = CreateTransferResponse(
        transferIds = arrayListOf(5)
    )

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

        every { api.getSignableTransfer(any()) } returns GetSignableTransferResponse(
            senderStarkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23486",
            signableMessage = SIGNABLE_MESSAGE,
            signableResponses = arrayListOf(
                SignableTransferResponseDetails(
                    amount = "1",
                    assetId = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02",
                    expirationTimestamp = 1_325_907,
                    nonce = 596_252_354,
                    payloadHash = "hash",
                    receiverStarkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91" +
                        "e971a23485",
                    receiverVaultId = 1_502_450_104,
                    senderVaultId = 1_502_450_105,
                    token = Erc721Asset("tokenAddress", "tokenId").toSignableToken()
                )
            )
        )
        every {
            api.createTransfer(
                CreateTransferRequest(
                    senderStarkKey =
                    "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23486",
                    requests = arrayListOf(
                        TransferRequest(
                            amount = "1",
                            assetId = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552" +
                                "471a7abfcb02",
                            expirationTimestamp = 1_325_907,
                            nonce = 596_252_354,
                            starkSignature = STARK_SIGNATURE,
                            receiverStarkKey =
                            "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485",
                            receiverVaultId = 1_502_450_104,
                            senderVaultId = 1_502_450_105
                        )
                    )
                ),
                xImxEthAddress = ADDRESS,
                xImxEthSignature = ETH_SIGNATURE
            )
        } returns transferResponse
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testTransferErc20Success() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        testFuture(
            transfer(
                token = Erc20Asset("address", 18, "10"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = transferResponse,
            expectedError = null
        )
    }

    @Test
    fun testTransferEthSuccess() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        testFuture(
            transfer(
                token = EthAsset("10"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = transferResponse,
            expectedError = null
        )
    }

    @Test
    fun testTransferErc721Success() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        testFuture(
            transfer(
                token = Erc721Asset("address", "id"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = transferResponse,
            expectedError = null
        )
    }

    @Test
    fun testTransferFailOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            transfer(
                token = Erc721Asset("address", "id"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testTransferFailOnGetSignable() {
        every { api.getSignableTransfer(any()) } throws ClientException()
        addressFuture.complete(ADDRESS)

        testFuture(
            transfer(
                token = Erc721Asset("address", "id"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testTransferFailOnGetSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            transfer(
                token = Erc721Asset("address", "id"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testTransferFailOnInvalidSignableResponse() {
        every { api.getSignableTransfer(any()) } returns GetSignableTransferResponse(
            senderStarkKey = ADDRESS,
            signableMessage = SIGNABLE_MESSAGE,
            signableResponses = emptyList()
        )
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)

        testFuture(
            transfer(
                token = Erc721Asset("address", "id"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = null,
            expectedError = ImmutableException.invalidResponse("")
        )
    }

    @Test
    fun testTransferFailOnCreateTransfer() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(STARK_SIGNATURE)
        ethSignatureFuture.complete(ETH_SIGNATURE)
        every { api.createTransfer(any(), any(), any()) } throws ClientException()

        testFuture(
            transfer(
                token = Erc721Asset("address", "id"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = null,
            expectedError = ClientException()
        )
    }
}
