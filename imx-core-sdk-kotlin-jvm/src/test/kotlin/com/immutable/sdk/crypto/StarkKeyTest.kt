package com.immutable.sdk.crypto

import com.immutable.sdk.Constants
import com.immutable.sdk.Signer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.generators.ECKeyPairGenerator
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger

class StarkKeyTest {
    @MockK
    lateinit var signer: Signer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testGetAccountPath() {
        val path = StarkKey.getAccountPath("0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f")
        assertEquals("m/2645'/579218131'/211006541'/1534045311'/1431804530'/1", path)
    }

    @Test
    fun testGetIntFromBits() {
        // binary representation: 00010010001101001010011001001001101111111101100001001111111101000101001000100010
        val hex = "1234a649bfd84ff45222"
        assertEquals(34, StarkKey.getIntFromBits(hex, 9)) // 000100010
        assertEquals(1_024_657, StarkKey.getIntFromBits(hex, 25, 5)) // 11111010001010010001
    }

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

    @Test
    fun testGenerateStarkPrivateKey() {
        mockkObject(StarkKey)
        mockkObject(StarkCurve)
        mockkConstructor(ECKeyPairGenerator::class)
        every { anyConstructed<ECKeyPairGenerator>().init(any()) } returns Unit
        val kp = mockk<AsymmetricCipherKeyPair>()
        every { anyConstructed<ECKeyPairGenerator>().generateKeyPair() } returns kp
        val privateKey = mockk<ECPrivateKeyParameters>()
        every { kp.private } returns privateKey
        every { privateKey.d } returns
            BigInteger("1836448370050608829118549998082879500112197653260742969768389852330536019586")

        assertEquals(
            "03e049957cf93fdf11937d404828378f97a1d6d0e5882c328460b7d413d65329",
            StarkKey.generateStarkPrivateKey()
        )
        verifyOrder {
            StarkCurve.generatePrivateKey()
            StarkCurve.getKeyPair(any())
        }
    }

    @Test
    fun testGenerateLegacyKeyPair() {
        val pk = StarkKey.generateLegacyStarkPrivateKey(
            "0xe834136cc3206a8f80acd81922d80b377ca769dc973d83ee2bd8bed4b7cdc3565f2a3aded8de5c93f85" +
                "327d5c1fb535959bdc3068318875b95788b074f3ab2931c",
            "0x2cD7944D8398017d0D142Ea2Ec483bc230f01A84"
        )
        assertEquals("0512653c071aa6fb61615354cb850c1d6c122635c08329ce3b5c1f23ec844d19", pk)
    }
}
