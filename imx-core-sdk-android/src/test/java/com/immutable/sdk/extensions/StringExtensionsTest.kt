package com.immutable.sdk.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionsTest {
    @Test
    fun testHexToByteArray() {
        val expected = arrayListOf(18, 52, 86, 120, 144, 171, 205, 239)
        val actual = "1234567890AbCdEf".hexToByteArray().map { byte ->
            byte.toInt() and 0xFF
        }
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHexToByteArray_notHex() {
        "1".hexToByteArray()
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHexToByteArray_notHexChar() {
        "xx".hexToByteArray()
    }

    @Test
    fun testHexToBinary() {
        assertEquals("10011101011101101010100011101111", "9D76A8EF".hexToBinary())
        assertEquals("101010111100110111101111", "0xABCDEF".hexToBinary())
    }

    @Test
    fun testHexRemovePrefix() {
        assertEquals("123456789", "0x123456789".hexRemovePrefix())
        assertEquals("123456789", "123456789".hexRemovePrefix())
    }

    @Test
    fun testSanitizeBytes() {
        assertEquals("00ImmutableX-NFT", "ImmutableX-NFT".sanitizeBytes())
        assertEquals("XXXXXXImmutableX", "ImmutableX".sanitizeBytes(padding = 'X'))
        assertEquals("ImmutableX", "ImmutableX".sanitizeBytes(byteSize = 2, padding = 'X'))
        assertEquals("##ImmutableX", "ImmutableX".sanitizeBytes(byteSize = 4, padding = '#'))
    }

    @Test
    fun testCalcByteLength() {
        assertEquals(16, calcByteLength(14))
        assertEquals(24, calcByteLength(21))
        assertEquals(8, calcByteLength(8))
        assertEquals(16, calcByteLength(16))
    }
}
