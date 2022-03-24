package com.immutable.sdk.extensions

import androidx.annotation.VisibleForTesting
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.Constants.CHAR_ZERO
import com.immutable.sdk.utils.Constants.HEX_PREFIX
import java.math.BigInteger

/**
 * Parses the string reading it as an hexadecimal string, and returns its byte array representation.
 *
 * Note that either 0x-prefixed string and no-prefixed hex strings are supported.
 *
 * @throws IllegalArgumentException if the value is not an hexadecimal string.
 */
@Suppress("MagicNumber")
internal fun String.hexToByteArray(): ByteArray {
    // An hex string must always have length multiple of 2
    if (length % 2 != 0) {
        throw IllegalArgumentException("hex-string must have an even number of digits (nibbles)")
    }

    // Remove the 0x prefix if it is set
    val cleanInput = if (startsWith(HEX_PREFIX)) substring(2) else this

    return ByteArray(cleanInput.length / 2).apply {
        var i = 0
        while (i < cleanInput.length) {
            this[i / 2] = (
                    (hexToBin(cleanInput[i]) shl 4) +
                            hexToBin(cleanInput[i + 1])
                    ).toByte()
            i += 2
        }
    }
}

/**
 * Converts the given ch into its integer representation considering it as an hexadecimal character.
 */
@Suppress("MagicNumber")
private fun hexToBin(ch: Char): Int = when (ch) {
    in '0'..'9' -> ch - '0'
    in 'A'..'F' -> ch - 'A' + 10
    in 'a'..'f' -> ch - 'a' + 10
    else -> throw(IllegalArgumentException("'$ch' is not a valid hex character"))
}

internal fun String.hexToBinary(): String =
    BigInteger(hexRemovePrefix(), Constants.HEX_RADIX).toString(2)

internal fun String.hexRemovePrefix(): String = replaceFirst(HEX_PREFIX, "")

internal fun String.addHexPrefix(): String = "$HEX_PREFIX$this"

internal fun String.sanitizeBytes(
    byteSize: Int = 8,
    padding: Char = CHAR_ZERO
): String {
    return padStart(length = calcByteLength(length, byteSize), padding)
}

@VisibleForTesting
internal fun calcByteLength(length: Int, byteSize: Int = 8): Int {
    val remainder = length % byteSize
    return if (remainder != 0) ((length - remainder) / byteSize) * byteSize + byteSize else length
}
