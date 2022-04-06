package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.TestException
import com.immutable.sdk.api.TransfersApi
import com.immutable.sdk.model.*
import com.immutable.sdk.testFuture
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7g"
private const val RECIPIENT_ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

class TransferWorkflowTest {
    @MockK
    private lateinit var api: TransfersApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkSignatureFuture: CompletableFuture<String>

    private val transferResponse = CreateTransferResponse(
        sentSignature = "sentSignature",
        status = "complete",
        time = 1_325_907,
        transferId = 5
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture

        starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.starkSign("hash") } returns starkSignatureFuture

        every { api.getSignableTransfer(any()) } returns GetSignableTransferResponse(
            amount = "1",
            assetId = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02",
            expirationTimestamp = 1_325_907,
            nonce = 596_252_354,
            payloadHash = "hash",
            receiverStarkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485",
            receiverVaultId = 1_502_450_104,
            senderStarkKey = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23486",
            senderVaultId = 1_502_450_105
        )
        every {
            api.createTransfer(
                CreateTransferRequest(
                    amount = "1",
                    assetId = "0x0400018c7bd712ffd55027823f43277c11070bbaae94c8817552471a7abfcb02",
                    expirationTimestamp = 1_325_907,
                    nonce = 596_252_354,
                    starkSignature = SIGNATURE,
                    receiverStarkKey =
                    "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485",
                    receiverVaultId = 1_502_450_104,
                    senderStarkKey =
                    "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23486",
                    senderVaultId = 1_502_450_105
                )
            )
        } returns transferResponse
    }

    @Test
    fun testTransferErc20Success() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            transfer(
                token = Erc20Asset("address", 18, "10"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = 5,
            expectedError = null
        )
    }

    @Test
    fun testTransferEthSuccess() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            transfer(
                token = EthAsset("10"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = 5,
            expectedError = null
        )
    }

    @Test
    fun testTransferErc721Success() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            transfer(
                token = Erc721Asset("address", "id"),
                recipientAddress = RECIPIENT_ADDRESS,
                signer = signer,
                starkSigner = starkSigner,
                api = api
            ),
            expectedResult = 5,
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
            expectedError = ClientException()
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
        every { api.getSignableTransfer(any()) } returns GetSignableTransferResponse()
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
            expectedError = NullPointerException()
        )
    }

    @Test
    fun testTransferFailOnCreateTransfer() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        every { api.createTransfer(any()) } throws ClientException()

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
