package com.immutable.sdk.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class ByteExtensionsTest {
    @Test
    fun testToHex() {
        assertEquals("00", 0.toByte().toHex())
        assertEquals("32", 50.toByte().toHex())
        assertEquals("ff", 255.toByte().toHex())
        assertEquals("2c", 300.toByte().toHex())
    }

    @Test
    fun testCollectionByteToHex() {
        val actual = arrayListOf(
            75.toByte(),
            255.toByte(),
            400.toByte(),
            8.toByte()
        ).toHexString()
        val expected = "0x4bff9008"
        assertEquals(expected, actual)
    }
}
