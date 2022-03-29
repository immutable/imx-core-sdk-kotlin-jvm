package com.immutable.sdk.utils

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.model.GetUsersApiResponse
import com.immutable.sdk.model.RegisterUserResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class LoginWorkflowTest {
    @MockK
    private lateinit var api: UsersApi

    @MockK
    private lateinit var signer: Signer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testPreRegisteredUserLoginSuccess() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(address, Constants.STARK_MESSAGE) } returns starkSignatureFuture
        every { api.getUser(address) } returns GetUsersApiResponse(listOf("account"))

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable == null)
        assert(ecKeyPair != null)
    }

    @Test
    fun testUnregisteredUserLoginSuccess() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()
        val registerStarkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(address, Constants.STARK_MESSAGE) } returns starkSignatureFuture
        every {
            signer.signMessage(
                address,
                Constants.REGISTER_SIGN_MESSAGE
            )
        } returns registerStarkSignatureFuture
        every { api.getUser(address) } throws ClientException(statusCode = 404)
        every { api.registerUser(any()) } returns RegisterUserResponse("txHash")

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        registerStarkSignatureFuture.complete(signature)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable == null)
        assert(ecKeyPair != null)

        verify { api.registerUser(any()) }
    }

    @Test
    fun testLoginFailedOnAddress() {
        val addressFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.completeExceptionally(ImmutableException("No address"))
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable is CompletionException)
        assert(ecKeyPair == null)
    }

    @Test
    fun testLoginFailedOnSignature() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(address, Constants.STARK_MESSAGE) } returns starkSignatureFuture

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.completeExceptionally(ImmutableException("Seed exception"))
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable is CompletionException)
        assert(ecKeyPair == null)
    }

    @Test
    fun testLoginFailedOnStarkSignature() {
        // Invalid argument
        val address = "5"
        val signature = "5"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(address, Constants.STARK_MESSAGE) } returns starkSignatureFuture

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable is CompletionException)
        assert(ecKeyPair == null)
    }

    @Test
    fun testLoginFailedOnIsRegistered() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(address, Constants.STARK_MESSAGE) } returns starkSignatureFuture
        every { api.getUser(any()) } throws ClientException()

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable is CompletionException)
        assert(ecKeyPair == null)
    }

    @Test
    fun testLoginFailedOnGetRegisterMessage() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()
        val registerStarkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(address, Constants.STARK_MESSAGE) } returns starkSignatureFuture
        every {
            signer.signMessage(
                address,
                Constants.REGISTER_SIGN_MESSAGE
            )
        } returns registerStarkSignatureFuture
        every { api.getUser(address) } throws ClientException(statusCode = 404)

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        registerStarkSignatureFuture.completeExceptionally(
            ImmutableException("No register message")
        )
        latch.await(3000, TimeUnit.MILLISECONDS)

        assert(completed)
        assert(throwable is CompletionException)
        assert(ecKeyPair == null)
    }

    @Test
    fun testLoginFailedOnRegister() {
        val address = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"

        val addressFuture = CompletableFuture<String>()
        val starkSignatureFuture = CompletableFuture<String>()
        val registerStarkSignatureFuture = CompletableFuture<String>()

        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(address, Constants.STARK_MESSAGE) } returns starkSignatureFuture
        every {
            signer.signMessage(
                address,
                Constants.REGISTER_SIGN_MESSAGE
            )
        } returns registerStarkSignatureFuture
        every { api.getUser(address) } throws ClientException(statusCode = 404)
        every { api.registerUser(any()) } throws ClientException()

        val latch = CountDownLatch(1)
        val future = login(signer, api)
        var throwable: Throwable? = null
        var ecKeyPair: ECKeyPair? = null
        var completed = false

        future.whenComplete { keyPair, error ->
            completed = true
            ecKeyPair = keyPair
            throwable = error
            latch.countDown()
        }
        addressFuture.complete(address)
        starkSignatureFuture.complete(signature)
        registerStarkSignatureFuture.complete(signature)
        latch.await()

        verify { api.registerUser(any()) }

        assert(completed)
        assert(throwable is CompletionException)
        assert(ecKeyPair == null)
    }
}
