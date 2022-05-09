package com.immutable.sdk.crypto

import org.junit.Assert
import org.junit.Test

class CryptoTest {
    @Test
    fun testGetIntFromBits() {
        // binary representation: 00010010001101001010011001001001101111111101100001001111111101000101001000100010
        val hex = "1234a649bfd84ff45222"
        Assert.assertEquals(34, Crypto.getIntFromBits(hex, 9)) // 000100010
        Assert.assertEquals(1_024_657, Crypto.getIntFromBits(hex, 25, 5)) // 11111010001010010001
    }

    @Test
    fun testHashSha256Update() {
        Assert.assertEquals(
            "c0e45835461c373342946f01e6173b4ed07bf296d04a57e87f85e29a0fa45ae8",
            Crypto.hashSha256Update("ImmutableX".encodeToByteArray())
        )
    }
}
