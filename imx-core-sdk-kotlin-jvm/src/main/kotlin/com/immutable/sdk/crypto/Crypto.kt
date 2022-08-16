package com.immutable.sdk.crypto

import com.immutable.sdk.extensions.addHexPrefix
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.Constants
import com.immutable.sdk.Constants.HEX_RADIX
import java.math.BigInteger

internal object Crypto {
    @Suppress("MagicNumber")
    fun serialiseEthSignature(signature: String, size: Int = 64): String {
        val sig = signature.hexRemovePrefix()
        val v = BigInteger(sig.substring(size * 2, size * 2 + 2), HEX_RADIX)

        return (
            sig.substring(0, size).padStart(64, Constants.CHAR_ZERO) +
                sig.substring(size, size * 2).padStart(64, Constants.CHAR_ZERO) +
                importRecoveryParam(v).padStart(2, Constants.CHAR_ZERO)
            ).addHexPrefix()
    }

    private fun importRecoveryParam(v: BigInteger): String {
        val comp = BigInteger("27") // 1b
        return if (v.compareTo(comp) != -1) // if recovery param is greater or equal to 27
            v.subtract(comp).toString(HEX_RADIX)
        else
            v.toString(HEX_RADIX)
    }
}
