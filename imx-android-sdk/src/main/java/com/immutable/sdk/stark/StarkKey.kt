package com.immutable.sdk.stark

import androidx.annotation.VisibleForTesting
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.extensions.toNoPrefixHexString
import org.web3j.crypto.ECKeyPair

private const val LAYER = "starkex"
private const val APPLICATION = "immutablex"
private const val ACCOUNT_PATH = "m/2645'/%d'/%d'/%d'/%d'/1"

internal object StarkKey {
    @Suppress("MagicNumber")
    @VisibleForTesting
    internal fun getAccountPath(ethereumAddress: String): String {
        val layerHash = Crypto.hashSha256Update(LAYER.toByteArray())
        val applicationHash = Crypto.hashSha256Update(APPLICATION.toByteArray())
        val layerInt = Crypto.getIntFromBits(layerHash, startFromEnd = 31)
        val applicationInt = Crypto.getIntFromBits(applicationHash, startFromEnd = 31)
        val ethAddressInt1 = Crypto.getIntFromBits(ethereumAddress.substring(2), 31)
        val ethAddressInt2 = Crypto.getIntFromBits(ethereumAddress.substring(2), 62, 31)
        return String.format(ACCOUNT_PATH, layerInt, applicationInt, ethAddressInt1, ethAddressInt2)
    }

    /**
     * @param signature the 's' variable of the signature
     * @param ethereumAddress the connected wallet address
     * @return Stark key pair
     */
    @Suppress("MagicNumber")
    fun getKeyFromRawSignature(signature: String?, ethereumAddress: String?): ECKeyPair? {
        return if (signature != null && ethereumAddress != null) {
            val bytes = signature.hexToByteArray()
            val s = bytes.copyOfRange(32, 64).toNoPrefixHexString()
            Crypto.getKeyPairFromPath(s, getAccountPath(ethereumAddress))
        } else null
    }
}
