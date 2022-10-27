package com.immutable.sdk

import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.extensions.getStarkPublicKey
import com.immutable.sdk.extensions.toNoPrefixHexString
import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

private const val STARK_PRIVATE_KEY = "0520a33a1c3bbdbf4c180fe810269eb4b0169b816c8fdde688d415c53ccc0d0c"
private const val STARK_PUBLIC_ADDRESS = "0x0752ff93fa18f10f3a564e5ba3a2ba7078a59500817a0e5aedbfe1f35bd888e5"
private const val MESSAGE = "Sign this"
private const val SIGNED_MESSAGE = "result"

class StandardStarkSignerTest {
    private lateinit var starkSigner: StandardStarkSigner

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        starkSigner = StandardStarkSigner(STARK_PRIVATE_KEY)
    }

    @Test
    fun testKeyPairInitialisation() {
        assertEquals(STARK_PRIVATE_KEY, starkSigner.keyPair.privateKey.toByteArray().toNoPrefixHexString())
        assertEquals(STARK_PUBLIC_ADDRESS, starkSigner.keyPair.getStarkPublicKey())
    }

    @Test
    fun testStarkSignSuccess() {
        mockkObject(StarkKey)
        every { StarkKey.sign(starkSigner.keyPair, MESSAGE) } returns SIGNED_MESSAGE
        assertEquals(SIGNED_MESSAGE, starkSigner.signMessage(MESSAGE).get())
    }

    @Test(expected = Exception::class)
    fun testStarkSignError() {
        mockkObject(StarkKey)
        every { StarkKey.sign(any(), MESSAGE) } throws Exception()
        starkSigner.signMessage(MESSAGE).get()
    }

    @Test
    fun testGetAddress() {
        assertEquals(STARK_PUBLIC_ADDRESS, starkSigner.getAddress().get())
    }
}
