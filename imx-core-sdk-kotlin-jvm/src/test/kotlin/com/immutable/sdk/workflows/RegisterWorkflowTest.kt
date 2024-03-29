package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationOffchainResponse
import com.immutable.sdk.api.model.GetUsersApiResponse
import com.immutable.sdk.api.model.RegisterUserResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val STARK_ADDRESS = "0xb76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val STARK_PAYLOAD = "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466b"
private const val ETH_SIGNATURE = "Only sign this key linking request from Immutable X"

class RegisterWorkflowTest {
    @MockK
    private lateinit var api: UsersApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var starkSigner: StarkSigner

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkAddressFuture: CompletableFuture<String>
    private lateinit var starkSignatureFuture: CompletableFuture<String>
    private lateinit var ethSignatureFuture: CompletableFuture<String>

    private val signableResponse = GetSignableRegistrationOffchainResponse(
        STARK_PAYLOAD,
        ETH_SIGNATURE
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture

        starkAddressFuture = CompletableFuture<String>()
        every { starkSigner.getAddress() } returns starkAddressFuture

        starkSignatureFuture = CompletableFuture<String>()
        every { starkSigner.signMessage(STARK_PAYLOAD) } returns starkSignatureFuture

        ethSignatureFuture = CompletableFuture<String>()
        every { signer.signMessage(ETH_SIGNATURE) } returns ethSignatureFuture
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testPreRegisteredUserRegisterOffChainSuccess() {
        every { api.getUsers(ADDRESS) } returns GetUsersApiResponse(listOf("account"))
        addressFuture.complete(ADDRESS)
        starkAddressFuture.complete(STARK_ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = registerOffChain(signer, starkSigner, api)
        ) { ecKeyPair, throwable ->
            assert(throwable == null)
            assert(ecKeyPair != null)
        }

        verifyOrder {
            signer.getAddress()
            starkSigner.getAddress()
            api.getUsers(ADDRESS)
        }
        verify(exactly = 0) { api.getSignableRegistration(any()) }
        verify(exactly = 0) { api.registerUser(any()) }
    }

    @Test
    fun testUnregisteredUserRegisterOffChainSuccess() {
        every { api.getUsers(ADDRESS) } throws ClientException(statusCode = 404)
        every { api.getSignableRegistrationOffchain(any()) } returns signableResponse
        every { api.registerUser(any()) } returns RegisterUserResponse("txHash")

        addressFuture.complete(ADDRESS)
        starkAddressFuture.complete(STARK_ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        ethSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = registerOffChain(signer, starkSigner, api)
        ) { ecKeyPair, throwable ->
            assert(throwable == null)
            assert(ecKeyPair != null)
        }

        verify { api.registerUser(any()) }
    }

    @Test
    fun testRegisterOffChainFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = registerOffChain(signer, starkSigner, api),
            expectedResult = null,
            expectedError = TestException()
        )

        verify(exactly = 0) { api.registerUser(any()) }
    }

    @Test
    fun testRegisterOffChainFailedOnStarkAddress() {
        addressFuture.complete(STARK_ADDRESS)
        starkAddressFuture.completeExceptionally(TestException())

        testFuture(
            future = registerOffChain(signer, starkSigner, api),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testRegisterOffChainFailedOnStarkSignature() {
        every { api.getUsers(ADDRESS) } throws ClientException(statusCode = 404)
        every { api.getSignableRegistrationOffchain(any()) } returns signableResponse

        addressFuture.complete(ADDRESS)
        starkAddressFuture.complete(STARK_ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = registerOffChain(signer, starkSigner, api),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testRegisterOffChainFailedOnIsRegistered() {
        every { api.getUsers(any()) } throws ClientException()
        addressFuture.complete(ADDRESS)
        starkAddressFuture.complete(STARK_ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = registerOffChain(signer, starkSigner, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testRegisterOffChainFailedOnEthSignature() {
        every { api.getUsers(ADDRESS) } throws
            ClientException(statusCode = HttpURLConnection.HTTP_NOT_FOUND)
        every { api.getSignableRegistrationOffchain(any()) } returns signableResponse
        addressFuture.complete(ADDRESS)
        starkAddressFuture.complete(STARK_ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        ethSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = registerOffChain(signer, starkSigner, api),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testRegisterOffChainFailedOnGetSignableResponse() {
        every { api.getUsers(ADDRESS) } throws
            ClientException(statusCode = HttpURLConnection.HTTP_NOT_FOUND)
        every { api.getSignableRegistrationOffchain(any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        starkAddressFuture.complete(STARK_ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        ethSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = registerOffChain(signer, starkSigner, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testRegisterOffChainFailedOnRegister() {
        every { api.getUsers(ADDRESS) } throws
            ClientException(statusCode = HttpURLConnection.HTTP_NOT_FOUND)
        every { api.getSignableRegistrationOffchain(any()) } returns signableResponse
        every { api.registerUser(any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        starkAddressFuture.complete(STARK_ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        ethSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = registerOffChain(signer, starkSigner, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )

        verify { api.registerUser(any()) }
    }
}
