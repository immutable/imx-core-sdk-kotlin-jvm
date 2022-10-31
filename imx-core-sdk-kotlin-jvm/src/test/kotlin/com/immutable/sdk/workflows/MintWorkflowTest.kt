package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.TestException
import com.immutable.sdk.api.MintsApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.testFuture
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val ADDRESS2 = "0x4d12885544eB026AA73310871c3DDdfb7599BFf4"
private const val ADDRESS3 = "0xb398edd1970EAAb380b46C7f191853d6816d82eB"
private const val TOKEN_ID1 = "111"
private const val TOKEN_ID2 = "222"
private const val BLUEPRINT1 = "{blue:print}"
private const val BLUEPRINT2 = "{blueprint: data}"
private const val ETH_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1a"
private const val CONTRACT_ADDRESS = "0x0281d479BADBfDfb354e099a4Eb7df9911eE98b1"
private const val AUTH_SIGNATURE = "0x2b533bbd78acee85c4d5fba4ff205f59ac93579a19aa4b8bf099a75502e967b" +
    "Q85e76178c29cb1d8966d0c7728fe12f9a189542efb62793b2c572c58dde2aeebc00"

class MintWorkflowTest {
    @MockK
    private lateinit var mintsApi: MintsApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var mintTokensResponse: MintTokensResponse

    @MockK
    private lateinit var mintResultDetails: MintResultDetails

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var ethSignatureFuture: CompletableFuture<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        ethSignatureFuture = CompletableFuture<String>()
        every { signer.signMessage(any()) } returns ethSignatureFuture

        every { mintsApi.mintTokens(any()) } returns mintTokensResponse
        every { mintTokensResponse.results } returns arrayListOf(mintResultDetails)

        mockkObject(Crypto)
        every { Crypto.serialiseEthSignature(any()) } returns AUTH_SIGNATURE
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createMintFuture(users: ArrayList<MintUser>, royalties: ArrayList<MintFee>? = null) =
        mint(
            UnsignedMintRequest(CONTRACT_ADDRESS, users, royalties),
            signer,
            mintsApi
        )

    @Test
    fun testMintOneSuccess() {
        addressFuture.complete(ADDRESS)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        val users = arrayListOf(
            MintUser(arrayListOf(MintTokenDataV2(id = TOKEN_ID1, blueprint = BLUEPRINT1)), ADDRESS)
        )
        testFuture(
            future = createMintFuture(users),
            expectedResult = arrayListOf(mintResultDetails),
            expectedError = null
        )

        verifyOrder {
            signer.signMessage("0xf94ee0f8a5560b562af7ef2c7bf0c0a06ea738408a9dc650f99943cdaa81b092")
            mintsApi.mintTokens(
                arrayListOf(
                    MintRequest(AUTH_SIGNATURE, CONTRACT_ADDRESS, users, null)
                )
            )
        }
    }

    @Test
    fun testMintOneWithRoyaltiesSuccess() {
        addressFuture.complete(ADDRESS)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        val users = arrayListOf(
            MintUser(
                arrayListOf(
                    MintTokenDataV2(
                        id = TOKEN_ID1,
                        blueprint = BLUEPRINT1,
                        royalties = arrayListOf(
                            MintFee(0.02, ADDRESS2),
                            MintFee(0.01, ADDRESS3)
                        )
                    )
                ),
                ADDRESS,
            ),
        )
        val royalties = arrayListOf(MintFee(0.15, ADDRESS2))
        testFuture(
            future = createMintFuture(users, royalties),
            expectedResult = arrayListOf(mintResultDetails),
            expectedError = null
        )

        verifyOrder {
            signer.signMessage("0x85e77826969e7e72503103df181b513452dc4bdcc3b6e02fea569b07110a099b")
            mintsApi.mintTokens(
                arrayListOf(MintRequest(AUTH_SIGNATURE, CONTRACT_ADDRESS, users, royalties))
            )
        }
    }

    @Test
    fun testMintBatchSuccess() {
        addressFuture.complete(ADDRESS)
        ethSignatureFuture.complete(ETH_SIGNATURE)

        val users = arrayListOf(
            MintUser(
                arrayListOf(
                    MintTokenDataV2(
                        id = TOKEN_ID1,
                        blueprint = BLUEPRINT1,
                        royalties = arrayListOf(
                            MintFee(0.02, ADDRESS2),
                            MintFee(0.01, ADDRESS3)
                        )
                    )
                ),
                ADDRESS,
            ),
            MintUser(
                arrayListOf(
                    MintTokenDataV2(
                        id = TOKEN_ID2,
                        blueprint = BLUEPRINT2
                    )
                ),
                ADDRESS2,
            ),
        )
        val royalties = arrayListOf(MintFee(0.15, ADDRESS2))
        testFuture(
            future = createMintFuture(users, royalties),
            expectedResult = arrayListOf(mintResultDetails),
            expectedError = null
        )

        verifyOrder {
            signer.signMessage("0x3672d93d6ab85cfbc6cec47a0017560dd118d822f7bcfc38544387ad624f117a")
            mintsApi.mintTokens(
                arrayListOf(MintRequest(AUTH_SIGNATURE, CONTRACT_ADDRESS, users, royalties))
            )
        }
    }

    @Test
    fun testMintFailedOnEthSignature() {
        addressFuture.complete(ADDRESS)
        ethSignatureFuture.completeExceptionally(TestException())

        val users = arrayListOf(
            MintUser(arrayListOf(MintTokenDataV2(id = TOKEN_ID1, blueprint = BLUEPRINT1)), ADDRESS)
        )
        testFuture(
            future = createMintFuture(users),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testMintFailedOnMintTokens() {
        addressFuture.complete(ADDRESS)
        ethSignatureFuture.complete(ETH_SIGNATURE)
        every { mintsApi.mintTokens(any()) } throws ClientException()

        val users = arrayListOf(
            MintUser(arrayListOf(MintTokenDataV2(id = TOKEN_ID2, blueprint = BLUEPRINT2)), ADDRESS3)
        )
        testFuture(
            future = createMintFuture(users),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }
}
