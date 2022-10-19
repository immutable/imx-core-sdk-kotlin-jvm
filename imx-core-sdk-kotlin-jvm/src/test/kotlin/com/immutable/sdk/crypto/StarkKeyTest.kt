package com.immutable.sdk.crypto

import com.immutable.sdk.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger

class StarkKeyTest {
    @Test
    fun testHashSha256Update() {
        assertEquals(
            "c0e45835461c373342946f01e6173b4ed07bf296d04a57e87f85e29a0fa45ae8",
            StarkKey.hashSha256("ImmutableX".encodeToByteArray())
        )
    }

    @Test
    fun testSign() {
        val encodedMessage = "e2919c6f19f93d3b9e40c1eef10660bd12240a1520793a28ef21a7457037dd"
        val keyPair = ECKeyPair(
            BigInteger(
                "7CEFD165C3A374AC3E05170D699BF6549E371522883B447B284A3C16FC04CCC",
                Constants.HEX_RADIX
            ),
            BigInteger(
                "302ef6456cb87e2c77dd6b43074c20848069a306aeff7ba36f654543bbe80f659",
                Constants.HEX_RADIX
            )
        )
        assertEquals(
            "0x0752063caed87ef11d6e91c4a226ebfe98f190d248b857d882ae331771e6e462" +
                "0364a2c46e2190bbb243309a40da051b88f0657ea9d1c2ca11510fe18a8a22ae",
            StarkKey.sign(keyPair, encodedMessage)
        )
    }

    @Test
    fun testFixMessage() {
        assertEquals("123456789abcdef", StarkKey.fixMessage("0x123456789abcdef"))

        assertEquals(
            "074180eaec7e68712b5a0fbf5d63a70c33940c9b02e60565e36f84d705b669e0",
            StarkKey.fixMessage(
                "0x074180eaec7e68712b5a0fbf5d63a70c33940c9b02e60565e36f84d705b669e"
            )
        )
        assertEquals(
            "074180eaec7e68712b5a0fbf5d63a70c33940c9b02e60565e36f84d705b669e0",
            StarkKey.fixMessage(
                "074180eaec7e68712b5a0fbf5d63a70c33940c9b02e60565e36f84d705b669e"
            )
        )
    }
}
