package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.util.concurrent.CompletableFuture

private const val SIGNATURE =
    "0xc5b53280e17b53d130eed7f00fc4270c29910fc30445af60dbb6abd82dc98f5923fb2fa2" +
        "a8940c1d6d871c984f19954d25d913857e798d0c8f3fe98b57e7bcb61c"
private const val SERIALIZED_SIGNATURE =
    "0xc5b53280e17b53d130eed7f00fc4270c29910fc30445af60dbb6abd82dc98f5923fb2fa2" +
        "a8940c1d6d871c984f19954d25d913857e798d0c8f3fe98b57e7bcb601"

class ImxTimestampTest {
    @MockK
    private lateinit var signer: Signer

    private val clock = Clock.fixed(Instant.parse("2016-01-23T12:34:56Z"), ZoneOffset.UTC)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testImxTimestampRequest() {
        every { signer.signMessage("1453552496") } returns CompletableFuture.completedFuture(SIGNATURE)

        val future = imxTimestampRequest(signer, clock) { timestamp ->
            CompletableFuture.completedFuture(timestamp)
        }.get()

        assertEquals("1453552496", future.timestamp)
        assertEquals(SERIALIZED_SIGNATURE, future.signature)
    }

    @Test
    fun testImxTimestampRequest2() {
        mockkStatic(Clock::class)
        every { Clock.systemUTC() } returns clock
        every { signer.signMessage("1453552496") } returns CompletableFuture.completedFuture(SIGNATURE)

        val future = imxTimestampRequest(signer) { timestamp ->
            CompletableFuture.completedFuture(timestamp)
        }.get()

        assertEquals("1453552496", future.timestamp)
        assertEquals(SERIALIZED_SIGNATURE, future.signature)
    }
}
