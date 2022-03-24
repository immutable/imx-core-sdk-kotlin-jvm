package com.immutable.sdk.stark

import com.immutable.sdk.extensions.toHexString
import com.immutable.sdk.utils.Constants
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class StarkKeyTest {
    @Test
    fun testGetAccountPath() {
        val path = StarkKey.getAccountPath("0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f")
        assertEquals("m/2645'/579218131'/211006541'/1534045311'/1431804530'/1", path)
    }

    @Test
    fun testGetKeyFromRawSignature() {
        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                    "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
        val ethAddress = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
        val keypair = StarkKey.getKeyFromRawSignature(
            signature = signature,
            ethereumAddress = ethAddress
        )
        assertEquals(
            "0x02a4c7332c55d6c1c510d24272d1db82878f2302f05b53bcc38695ed5f78fffd",
            keypair?.publicKey?.toByteArray()?.toHexString(
                byteLength = Constants.STARK_KEY_PUBLIC_BYTE_LENGTH
            )
        )

        assertNull(StarkKey.getKeyFromRawSignature(null, null))
        assertNull(StarkKey.getKeyFromRawSignature(signature, null))
        assertNull(StarkKey.getKeyFromRawSignature(null, ethAddress))
    }
}
