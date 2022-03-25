package com.immutable.sdk.crypto

import com.immutable.sdk.extensions.*
import com.immutable.sdk.stark.Points
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.Constants.HEX_RADIX
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger
import java.security.MessageDigest

private const val HARDENED_BIT = 0x80000000.toInt()
private val ORDER = BigInteger(
    "0800000000000010ffffffffffffffffb781126dcae7b2321e66a241adc64d2f",
    HEX_RADIX,
)
private val SECP_ORDER = BigInteger(
    "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",
    HEX_RADIX,
)
private const val SHA_256 = "SHA-256"

object Crypto {
    internal fun getIntFromBits(
        hex: String,
        startFromEnd: Int,
        endFromEnd: Int? = null,
    ): Int {
        val bin = hex.hexToBinary()
        val bits = if (endFromEnd == null) {
            bin.takeLast(startFromEnd)
        } else {
            val start = bin.length - startFromEnd
            val end = bin.length - endFromEnd
            bin.substring(start, end)
        }
        return Integer.parseInt(bits, 2)
    }

    internal fun getKeyPairFromPath(seed: String, path: String): ECKeyPair {
        val master = Bip32ECKeyPair.generateKeyPair(BigInteger(seed, HEX_RADIX).toByteArray())
        val pathArray =
            path.split("/") // Example: "m/2645'/579218131'/211006541'/9971226311'/947333111'/1"
        val p = pathArray
            .subList(1, pathArray.size) // Ignore the first one
            .map {
                val isHardened = it.endsWith("'")
                it
                    .replace("'", "") // Remove hardened notation
                    .toInt() // Convert string to int
                    .run {
                        if (isHardened) this or HARDENED_BIT else this
                    }
            }.toIntArray()
        val gk = grindKey(
            Bip32ECKeyPair.deriveKeyPair(master, p)
                .privateKeyBytes33.drop(1).toHexString()
        )
        return StarkCurve.getKeyPair(gk)
    }

    private fun grindKey(privateKey: String): String {
        var i = 0
        var key = hashKeyWithIndex(privateKey, i)

        while (key >= SECP_ORDER.subtract(SECP_ORDER.mod(ORDER))) {
            key = hashKeyWithIndex(key.toString(HEX_RADIX), i)
            i++
        }
        return key.mod(ORDER).toString(HEX_RADIX)
    }

    private fun hashKeyWithIndex(key: String, index: Int): BigInteger {
        val hexString = key.hexRemovePrefix() +
            Integer.toHexString(index).sanitizeBytes(byteSize = 2)

        val hash = hashSha256Update(hexString.hexToByteArray())
        return BigInteger(hash, HEX_RADIX)
    }

    internal fun hashSha256Update(input: ByteArray) = MessageDigest.getInstance(SHA_256).run {
        update(input)
        digest().toHexString("")
    }

    // Can be removed once API returns encoded and serialised message ready to be signed with the Stark keys
    @Suppress("MagicNumber")
    fun pedersenHash(input: Array<String>): String {
        val prime =
            BigInteger("800000000000011000000000000000000000000000000000000000000000001", HEX_RADIX)

        var point = Points.constantPoints[0]
        for (i in input.indices) {
            var x = BigInteger(input[i].hexRemovePrefix(), HEX_RADIX)
            assert(x >= BigInteger.ZERO && x < prime) { "Invalid input: ${input[i]}" }
            for (j in 0 until 252) {
                val pt = Points.constantPoints[2 + i * 252 + j]
                assert(!point.xCoord.equals(pt.xCoord))
                if (x.and(BigInteger.ONE) != BigInteger.ZERO) {
                    point = point.add(pt)
                }
                x = x.shr(1)
            }
        }
        return point.xCoord.toString()
    }

    internal fun serializeEthSignature(signature: String, size: Int = 64): String {
        val sig = signature.hexRemovePrefix()
        val v = BigInteger(sig.substring(size * 2, size * 2 + 2), HEX_RADIX)

        return (
                sig.substring(0, size).padStart(64, Constants.CHAR_ZERO) +
                        sig.substring(size, size * 2).padStart(64, Constants.CHAR_ZERO) +
                        importRecoveryParam(v).padStart(2, Constants.CHAR_ZERO)
                ).addHexPrefix()
    }

    private fun importRecoveryParam(v: BigInteger): String {
        val comp = BigInteger("27")
        return if (v.compareTo(comp) != -1)
            v.subtract(comp).toString(HEX_RADIX)
        else
            v.toString(HEX_RADIX)
    }
}
