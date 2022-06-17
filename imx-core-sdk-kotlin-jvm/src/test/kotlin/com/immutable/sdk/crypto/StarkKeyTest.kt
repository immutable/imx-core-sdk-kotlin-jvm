package com.immutable.sdk.crypto

import com.immutable.sdk.*
import com.immutable.sdk.extensions.toHexString
import com.immutable.sdk.workflows.SIGNATURE
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

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
            "0x074180eaec7e68712b5a0fbf5d63a70c33940c9b02e60565e36f84d705b669e0",
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
    fun testGenerateSuccess() {
        val addressFuture = CompletableFuture<String>()
        val signatureFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(Constants.STARK_MESSAGE) } returns signatureFuture

        val signature =
            "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
                "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf1b"
        addressFuture.complete("0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f")
        signatureFuture.complete(signature)

        testFuture(
            future = StarkKey.generate(signer)
        ) { ecKeyPair, throwable ->
            assert(ecKeyPair != null)
            assert(throwable == null)
        }
    }

    @Test
    fun testGenerateFailedOnGetAddress() {
        val addressFuture = CompletableFuture<String>()
        val signatureFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(Constants.STARK_MESSAGE) } returns signatureFuture

        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = StarkKey.generate(signer),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testGenerateFailedOnSignMessage() {
        val addressFuture = CompletableFuture<String>()
        val signatureFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(Constants.STARK_MESSAGE) } returns signatureFuture

        addressFuture.complete("5")
        signatureFuture.completeExceptionally(TestException())

        testFuture(
            future = StarkKey.generate(signer),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testGenerateFailedInvalidAddress() {
        val addressFuture = CompletableFuture<String>()
        val signatureFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(Constants.STARK_MESSAGE) } returns signatureFuture

        // invalid argument
        addressFuture.complete("5")
        signatureFuture.complete(SIGNATURE)

        testFuture(
            future = StarkKey.generate(signer),
            expectedResult = null,
            expectedError = ImmutableException.clientError("")
        )
    }

    @Test
    fun testGenerateFailedInvalidSignature() {
        val signer = mockk<Signer>()
        val addressFuture = CompletableFuture<String>()
        val signatureFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        every { signer.signMessage(Constants.STARK_MESSAGE) } returns signatureFuture

        addressFuture.complete("0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f")
        // invalid argument
        signatureFuture.complete("5")

        testFuture(
            future = StarkKey.generate(signer),
            expectedResult = null,
            expectedError = ImmutableException.clientError("")
        )
    }
}
