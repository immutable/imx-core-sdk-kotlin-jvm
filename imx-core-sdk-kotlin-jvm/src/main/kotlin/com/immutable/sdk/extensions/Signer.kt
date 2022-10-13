package com.immutable.sdk.extensions

import com.immutable.sdk.Signer
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.rlp.RlpEncoder
import org.web3j.rlp.RlpList
import org.web3j.utils.Numeric

/**
 * Signs the [rawTransaction]
 *
 * @return signed [rawTransaction] in hex format
 */
fun Signer.signTransaction(rawTransaction: RawTransaction): String {
    val encodedTransaction = TransactionEncoder.encode(rawTransaction)
    val signatureData = signMessage(Numeric.toHexString(encodedTransaction)).get()
    val values = TransactionEncoder.asRlpValues(rawTransaction, signatureData)
    val rlpList = RlpList(values)
    val signedData = RlpEncoder.encode(rlpList)
    return Numeric.toHexString(signedData)
}
