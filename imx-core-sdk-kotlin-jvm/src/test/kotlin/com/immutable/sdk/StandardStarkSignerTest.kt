package com.immutable.sdk

import com.immutable.sdk.crypto.StarkKey
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger

class StandardStarkSignerTest {
    private lateinit var starkSigner: StandardStarkSigner
    private val keyPair = ECKeyPair(BigInteger.ONE, BigInteger.TEN)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        starkSigner = StandardStarkSigner(keyPair)
    }

    @Test
    fun testStarkSignSuccess() {
        mockkObject(StarkKey)
        every { StarkKey.sign(keyPair, "Sign this") } returns "result"
        assertEquals("result", starkSigner.signMessage("Sign this").get())
    }

    @Test(expected = Exception::class)
    fun testStarkSignError() {
        mockkObject(StarkKey)
        every { StarkKey.sign(keyPair, "Sign this") } throws Exception()
        starkSigner.signMessage("Sign this").get()
    }
}
