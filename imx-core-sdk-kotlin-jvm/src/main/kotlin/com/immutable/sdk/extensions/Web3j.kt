package com.immutable.sdk.extensions

import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import java.math.BigInteger

/**
 * @return nonce for the given [address]
 */
fun Web3j.getNonce(address: String): BigInteger {
    val ethGetTransactionCount = ethGetTransactionCount(
        address, DefaultBlockParameterName.PENDING
    ).sendAsync().get()
    return ethGetTransactionCount.transactionCount
}
