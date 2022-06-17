package com.immutable.sdk.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

private const val VALUE = "ImmutableX"

class ByteArrayExtensionsTest {
    @Test
    fun testToHexString() {
        assertEquals("0x496d6d757461626c6558", VALUE.toByteArray().toHexString())
        assertEquals("##496d6d757461626c6558", VALUE.toByteArray().toHexString(prefix = "##"))
    }

    @Test
    fun testToNoPrefixHexString() {
        assertEquals("496d6d757461626c6558", VALUE.toByteArray().toNoPrefixHexString())
    }
}
