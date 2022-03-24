package com.immutable.sdk.extensions

import com.immutable.sdk.utils.Constants.HEX_PREFIX

/**
 * Converts [this] [ByteArray] into its hexadecimal string representation prepending to it the given [prefix].
 *
 * If [byteLength] is also give, [this] [ByteArray] will also be capped to the given [byteLength].
 */
internal fun ByteArray.toHexString(prefix: String = HEX_PREFIX, byteLength: Int? = null): String {
    return if (byteLength != null && size > byteLength) {
        val diff = size - byteLength
        prefix + drop(diff).joinToString(separator = "") { it.toHex() }
    } else prefix + joinToString(separator = "") { it.toHex() }
}

/**
 * Converts [this] [ByteArray] into its hexadecimal representation without prepending any prefix to it.
 */
internal fun ByteArray.toNoPrefixHexString(): String = toHexString(prefix = "")
