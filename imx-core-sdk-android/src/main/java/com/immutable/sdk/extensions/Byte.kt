package com.immutable.sdk.extensions

import com.immutable.sdk.Constants.HEX_PREFIX

private const val HEX_CHARS = "0123456789abcdef"

/**
 * Encodes the byte value as an hexadecimal character.
 */
@Suppress("MagicNumber")
internal fun Byte.toHex(): String =
    HEX_CHARS[toInt().shr(4) and 0x0f].toString() + HEX_CHARS[toInt().and(0x0f)].toString()

/**
 * Converts [this] [Collection] of bytes into its hexadecimal string representation prepending to it the given [prefix].
 *
 * Note that by default the 0x prefix is prepended to the result of the conversion.
 * If you want to have the representation without the 0x prefix, use the [toNoPrefixHexString] method or
 * pass to this method an empty [prefix].
 */
internal fun Collection<Byte>.toHexString(prefix: String = HEX_PREFIX): String =
    toByteArray().toHexString(prefix)
