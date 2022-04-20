package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.TestException
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetUsersApiResponse
import com.immutable.sdk.api.model.RegisterUserResponse
import com.immutable.sdk.testFuture
import com.immutable.sdk.utils.Constants
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

class LoginWorkflowTest {
    @MockK
    private lateinit var api: UsersApi

    @MockK
    private lateinit var signer: Signer

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var starkSignatureFuture: CompletableFuture<String>
    private lateinit var registerStarkSignatureFuture: CompletableFuture<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture

        starkSignatureFuture = CompletableFuture<String>()
        every { signer.signMessage(Constants.STARK_MESSAGE) } returns starkSignatureFuture

        registerStarkSignatureFuture = CompletableFuture<String>()
        every {
            signer.signMessage(Constants.REGISTER_SIGN_MESSAGE)
        } returns registerStarkSignatureFuture
    }

    @Test
    fun testPreRegisteredUserLoginSuccess() {
        every { api.getUsers(ADDRESS) } returns GetUsersApiResponse(listOf("account"))
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)

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
        every { api.registerUser(any()) } returns RegisterUserResponse("txHash")

        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        registerStarkSignatureFuture.complete(SIGNATURE)

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
    fun testLoginFailedOnSignature() {
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testLoginFailedOnStarkSignature() {
        // Invalid argument
        val address = "5"
        val signature = "5"
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)

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
        starkSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testLoginFailedOnGetRegisterMessage() {
        every { api.getUsers(ADDRESS) } throws ClientException(statusCode = 404)
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        registerStarkSignatureFuture.completeExceptionally(TestException())

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testLoginFailedOnRegister() {
        every { api.getUsers(ADDRESS) } throws ClientException(statusCode = 404)
        every { api.registerUser(any()) } throws ClientException()
        addressFuture.complete(ADDRESS)
        starkSignatureFuture.complete(SIGNATURE)
        registerStarkSignatureFuture.complete(SIGNATURE)

        testFuture(
            future = login(signer, api),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )

        verify { api.registerUser(any()) }
    }
}
