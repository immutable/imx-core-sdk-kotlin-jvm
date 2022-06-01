package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationOffchainResponse
import com.immutable.sdk.api.model.GetUsersApiResponse
import com.immutable.sdk.api.model.RegisterUserResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
private const val STARK_PAYLOAD = "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466b"
private const val ETH_SIGNATURE = "Only sign this key linking request from Immutable X"

class LoginWorkflowTest {
    @MockK
    private lateinit var api: UsersApi

    @MockK
    private lateinit var signer: Signer

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkSeedFuture: CompletableFuture<String>
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

        starkSeedFuture = CompletableFuture<String>()
        every { signer.signMessage(Constants.STARK_MESSAGE) } returns starkSeedFuture

        ethSignatureFuture = CompletableFuture<String>()
        every { signer.signMessage(ETH_SIGNATURE) } returns ethSignatureFuture
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testPreRegisteredUserLoginSuccess() {
        every { api.getUsers(ADDRESS) } returns GetUsersApiResponse(listOf("account"))
        addressFuture.complete(ADDRESS)
        starkSeedFuture.complete(SIGNATURE)

        testFuture(
            future = login(signer, api)
        ) { ecKeyPair, throwable ->
            assert(throwable == null)
            assert(ecKeyPair != null)
        }
    }

    @Test
    fun testUnregisteredUserLoginSuccess() {
        every { api.getUsers(ADDRESS) } throws ClientException(statusCode = 404)
        every { api.getSignableRegistrationOffchain(any()) } returns signableResponse
        every { api.registerUser(any()) } returns RegisterUserResponse("txHash")

        addressFuture.complete(ADDRESS)
        starkSeedFuture.complete(SIGNATURE)
        ethSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = login(signer, api)
        ) { ecKeyPair, throwable ->
            assert(throwable == null)
            assert(ecKeyPair != null)
        }

        verify { api.registerUser(any()) }
    }

    @Test
    fun testLoginFailedOnAddress() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = TestException()
        )

        verify(exactly = 0) { api.registerUser(any()) }
    }

    @Test
    fun testLoginFailedOnStarkSignature() {
        addressFuture.complete(ADDRESS)
        starkSeedFuture.completeExceptionally(TestException())

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testLoginFailedOnStarkKeyGenerationException() {
        // Invalid argument
        val address = "5"
        val signature = "5"
        addressFuture.complete(address)
        starkSeedFuture.complete(signature)

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = ImmutableException.clientError("")
        )
    }

    @Test
    fun testLoginFailedOnIsRegistered() {
        every { api.getUsers(any()) } throws ClientException()
        addressFuture.complete(ADDRESS)
        starkSeedFuture.complete(SIGNATURE)

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testLoginFailedOnEthSignature() {
        every { api.getUsers(ADDRESS) } throws
            ClientException(statusCode = HttpURLConnection.HTTP_NOT_FOUND)
        every { api.getSignableRegistrationOffchain(any()) } returns signableResponse
        addressFuture.complete(ADDRESS)
        starkSeedFuture.complete(SIGNATURE)
        ethSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testLoginFailedOnGetSignableResponse() {
        every { api.getUsers(ADDRESS) } throws
            ClientException(statusCode = HttpURLConnection.HTTP_NOT_FOUND)
        every { api.getSignableRegistrationOffchain(any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        starkSeedFuture.complete(SIGNATURE)
        ethSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testLoginFailedOnRegister() {
        every { api.getUsers(ADDRESS) } throws
            ClientException(statusCode = HttpURLConnection.HTTP_NOT_FOUND)
        every { api.getSignableRegistrationOffchain(any()) } returns signableResponse
        every { api.registerUser(any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        starkSeedFuture.complete(SIGNATURE)
        ethSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )

        verify { api.registerUser(any()) }
    }
}
