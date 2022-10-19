package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.extensions.getNonce
import org.web3j.crypto.RawTransaction
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.tx.Contract
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

/**
 * Within the provided call block force unwrap any response values that are required and if they are
 * missing it will automatically be handled and return an invalidResponse exception.
 */
@Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")
internal fun <T> call(callName: String, call: () -> T): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    CompletableFuture.runAsync {
        try {
            future.complete(call())
        } catch (e: Exception) {
            if (e is NullPointerException)
                future.completeExceptionally(ImmutableException.invalidResponse(callName, e))
            else
                future.completeExceptionally(ImmutableException.apiError(callName, e))
        }
    }
    return future
}

internal fun <T> completeExceptionally(ex: Throwable): CompletableFuture<T> =
    CompletableFuture<T>().apply {
        completeExceptionally(ex)
    }

/**
 * Signs a transaction with [data] and calls the given [contract] [contractFunction]
 */
@Suppress("LongParameterList", "TooGenericExceptionCaught")
internal fun sendTransaction(
    contract: Contract,
    contractFunction: String,
    amount: BigInteger = BigInteger.ZERO,
    data: String,
    signer: Signer,
    web3j: Web3j,
): CompletableFuture<EthSendTransaction> {
    val future = CompletableFuture<EthSendTransaction>()
    CompletableFuture.runAsync {
        try {
            val address = signer.getAddress().get()
            val gasProvider = DefaultGasProvider()

            val rawTransaction = RawTransaction.createTransaction(
                web3j.getNonce(address),
                gasProvider.gasPrice,
                gasProvider.getGasLimit(contractFunction),
                contract.contractAddress,
                amount,
                data
            )

            val signedTransaction = signer.signTransaction(rawTransaction).get()
            future.complete(web3j.ethSendRawTransaction(signedTransaction).send())
        } catch (e: Exception) {
            future.completeExceptionally(ImmutableException.contractError(contractFunction, e))
        }
    }
    return future
}

/**
 * Data model for workflows to hold the ethAddress and signatures for transactions
 */
internal class WorkflowSignatures(val ethAddress: String) {
    lateinit var ethSignature: String
    lateinit var starkSignature: String

    // The ethSignature needs to be serialised before being supplied to the API
    val serialisedEthSignature: String
        get() {
            return Crypto.serialiseEthSignature(ethSignature)
        }
}
