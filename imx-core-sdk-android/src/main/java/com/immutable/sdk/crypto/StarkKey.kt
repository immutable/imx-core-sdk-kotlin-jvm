package com.immutable.sdk.crypto

import androidx.annotation.VisibleForTesting
import com.immutable.sdk.Constants
import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.extensions.*
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.crypto.signers.HMacDSAKCalculator
import org.bouncycastle.util.BigIntegers
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.ECDSASignature
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.CompletableFuture

private const val LAYER = "starkex"
private const val APPLICATION = "immutablex"
private const val ACCOUNT_PATH = "m/2645'/%d'/%d'/%d'/%d'/1"

private const val HARDENED_BIT = 0x80000000.toInt()
private val ORDER = BigInteger(
    "0800000000000010ffffffffffffffffb781126dcae7b2321e66a241adc64d2f",
    Constants.HEX_RADIX,
)
private val SECP_ORDER = BigInteger(
    "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",
    Constants.HEX_RADIX,
)
private const val SHA_256 = "SHA-256"

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
     * Grabs the bits from [startFromEnd] to [endFromEnd] in [hex] amd returns
     * the integer representation of it.
     *
     * If [endFromEnd] is null, this function will grab all the bits from [startFromEnd] to the last bit.
     */
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

    /**
     * @return StarkEx and ImmutableX account path from the [ethereumAddress]
     */
    @Suppress("MagicNumber")
    @VisibleForTesting
    internal fun getAccountPath(ethereumAddress: String): String {
        val layerHash = hashSha256(LAYER.toByteArray())
        val applicationHash = hashSha256(APPLICATION.toByteArray())
        val layerInt = getIntFromBits(layerHash, startFromEnd = 31)
        val applicationInt = getIntFromBits(applicationHash, startFromEnd = 31)
        val ethAddressInt1 = getIntFromBits(ethereumAddress.substring(2), 31)
        val ethAddressInt2 = getIntFromBits(ethereumAddress.substring(2), 62, 31)
        return String.format(ACCOUNT_PATH, layerInt, applicationInt, ethAddressInt1, ethAddressInt2)
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
     * Generates the Stark key pair from the [seed] and [path]
     */
    private fun getKeyPairFromPath(seed: String, path: String): ECKeyPair {
        val master =
            Bip32ECKeyPair.generateKeyPair(BigInteger(seed, Constants.HEX_RADIX).toByteArray())
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

    /**
     * @param signature the 's' variable of the signature
     * @param ethereumAddress the connected wallet address
     * @return Stark key pair
     */
    @Suppress("MagicNumber")
    internal fun getKeyFromRawSignature(signature: String?, ethereumAddress: String?): ECKeyPair? {
        return if (signature != null && ethereumAddress != null) {
            val bytes = signature.hexToByteArray()
            val s = bytes.copyOfRange(32, 64).toNoPrefixHexString()
            getKeyPairFromPath(s, getAccountPath(ethereumAddress))
        } else null
    }

    /**
     * Signs the given [msg] with the given [keyPair]
     *
     * @return Stark signature
     */
    @Suppress("MagicNumber")
    fun sign(keyPair: ECKeyPair, msg: String): String {
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
     * Generate a Stark key pair from a L1 wallet.
     */
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun generate(signer: Signer): CompletableFuture<ECKeyPair> {
        return signer.getAddress()
            .thenCompose { address ->
                signer.signMessage(Constants.STARK_MESSAGE).thenApply { address to it }
            }
            .thenCompose { (address, signature) ->
                val keyPairFuture = CompletableFuture<ECKeyPair>()
                CompletableFuture.runAsync {
                    try {
                        val keyPair = getKeyFromRawSignature(signature, address)
                        if (keyPair == null)
                            keyPairFuture.completeExceptionally(
                                ImmutableException.clientError("Failed to generate Stark key pair")
                            )
                        else
                            keyPairFuture.complete(keyPair)
                    } catch (e: Exception) {
                        keyPairFuture.completeExceptionally(
                            ImmutableException.clientError("Failed to generate Stark key pair")
                        )
                    }
                }
                keyPairFuture
            }
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
            "${msg}0"
        }
    }
}
