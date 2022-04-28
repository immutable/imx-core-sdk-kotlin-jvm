package com.immutable.sdk.crypto

import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.utils.Constants.HEX_RADIX
import java.math.BigInteger

private const val REGISTER_USER = "1000"

// Can be removed once API returns encoded and serialised message ready to be signed with the Stark keys
@Suppress("TooManyFunctions")
object CryptoUtil {
    internal fun getRegisterUserMsgVerifyEth(
        etherKey: String,
        starkPublicKey: String,
    ): String {
        return Crypto.pedersenHash(
            arrayOf(
                Crypto.pedersenHash(arrayOf(starkPublicKey)),
                packRegisterUserMsgVerifyEth(etherKey),
            )
        )
    }

    @Suppress("MagicNumber")
    private fun packRegisterUserMsgVerifyEth(etherKey: String): String {
        var serialized = BigInteger(REGISTER_USER)
        serialized = serialized
            .shl(160)
            .add(BigInteger(etherKey.hexRemovePrefix(), HEX_RADIX))
        return sanitizeHex(serialized.toString(HEX_RADIX))
    }

    private fun sanitizeHex(hexString: String): String {
        var hex = hexString.replaceFirst("0x", "")
        hex = hex.padStart(length = calcByteLength(hex.length, 2), '0')
        hex = "0x$hex"
        return hex
    }

    private fun calcByteLength(length: Int, byteSize: Int): Int {
        val remainder = length % byteSize
        return if (remainder != 0) ((length - remainder) / byteSize) * byteSize + byteSize else length
    }
}
