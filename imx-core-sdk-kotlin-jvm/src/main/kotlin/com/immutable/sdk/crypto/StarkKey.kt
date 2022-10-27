package com.immutable.sdk.crypto

import com.google.common.annotations.VisibleForTesting
import com.immutable.sdk.Constants
import com.immutable.sdk.extensions.*
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.crypto.signers.HMacDSAKCalculator
import org.bouncycastle.util.BigIntegers
import org.web3j.crypto.ECDSASignature
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger
import java.security.MessageDigest

private val ORDER = BigInteger(
    "0800000000000010ffffffffffffffffb781126dcae7b2321e66a241adc64d2f",
    Constants.HEX_RADIX,
)
private val SECP_ORDER = BigInteger(
    "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",
    Constants.HEX_RADIX,
)
private const val SHA_256 = "SHA-256"

/**
 * An object to generate a Stark key pair from a given Ethereum signer
 */
object StarkKey {
    /**
     * Hashes the given [input] using SHA-256
     *
     * @param input the value to hash
     * @return hex string representation of the hash
     */
    internal fun hashSha256(input: ByteArray) = MessageDigest.getInstance(SHA_256).run {
        update(input)
        digest().toHexString("")
    }

    /**
     * Combines the [key] and [index] and SHA-256 hash them
     */
    private fun hashKeyWithIndex(key: String, index: Int): BigInteger {
        val hexString = key.hexRemovePrefix() +
            Integer.toHexString(index).sanitizeBytes(byteSize = 2)

        val hash = hashSha256(hexString.hexToByteArray())
        return BigInteger(hash, Constants.HEX_RADIX)
    }

    private fun grindKey(privateKey: String): String {
        var i = 0
        var key = hashKeyWithIndex(privateKey, i)

        while (key >= SECP_ORDER.subtract(SECP_ORDER.mod(ORDER))) {
            key = hashKeyWithIndex(key.toString(Constants.HEX_RADIX), i)
            i++
        }
        return key.mod(ORDER).toString(Constants.HEX_RADIX)
    }

    /**
     * Signs the given [msg] with the given [keyPair]
     *
     * @param msg must be in hex format and 64 characters or less in length (including 0x prefix)
     *
     * @return Stark signature
     */
    @Suppress("MagicNumber")
    internal fun sign(keyPair: ECKeyPair, msg: String): String {
        val fixMessage = fixMessage(msg)
        val signer = ECDSASigner(HMacDSAKCalculator(SHA256Digest()))

        val privateKey = StarkCurve.createPrivateKeyParams(keyPair.privateKey)
        signer.init(true, privateKey)
        val msgArray = BigIntegers.asUnsignedByteArray(BigInteger(fixMessage, Constants.HEX_RADIX))
        val components = signer.generateSignature(msgArray)

        val sig = ECDSASignature(components[0], components[1]).toCanonicalised()
        // Serialise signature
        return (
            sig.r.toByteArray().toNoPrefixHexString().padStart(64, Constants.CHAR_ZERO) +
                sig.s.toByteArray().toNoPrefixHexString().padStart(64, Constants.CHAR_ZERO)
            ).addHexPrefix()
    }

    /**
     * Generates a new Stark key pair
     */
    fun generateStarkPrivateKey(): String {
        val privateKey = StarkCurve.generatePrivateKey()
        return StarkCurve.getKeyPair(grindKey(privateKey.toHexString())).privateKey.toByteArray().toNoPrefixHexString()
    }

    /**
     * Removes the hex prefix and appends the [msg] with a zero if it's required
     */
    @VisibleForTesting
    @Suppress("MagicNumber")
    internal fun fixMessage(msg: String): String {
        val message = msg.hexRemovePrefix()
        return if (message.length <= 62) {
            message
        } else {
            assert(message.length == 63)
            "${message}0"
        }
    }
}
