package com.immutable.sdk.extensions

import com.immutable.sdk.Constants
import org.web3j.crypto.ECKeyPair

/**
 * Utility to convert the public key into a Stark friendly hex address
 */
fun ECKeyPair.getStarkPublicKey() =
    publicKey.toString(Constants.HEX_RADIX)
        .sanitizeBytes()
        .hexToByteArray()
        .toHexString(byteLength = Constants.STARK_KEY_PUBLIC_BYTE_LENGTH)
