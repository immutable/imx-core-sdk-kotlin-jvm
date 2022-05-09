package com.immutable.sdk

import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture

/**
 * This represents an Ethereum Externally Owned Account (EOA). Any wallet provider can be wrapped
 * with this interface so they can be used with this SDK.
 */
interface Signer {
    /**
     * Returns a CompletableFuture that resolves to the account address.
     *
     * This is a CompletableFuture so that a Signer can be designed around an asynchronous source,
     * such as hardware wallets.
     */
    fun getAddress(): CompletableFuture<String>

    /**
     * A signed message is prefixed with "\x19Ethereum Signed Message:\n" and the length of the
     * message, using the hashMessage method, so that it is EIP-191 compliant. If recovering the
     * address in Solidity, this prefix will be required to create a matching hash.
     *
     * Sub-classes must implement this, however they may throw if signing a message is not
     * supported, such as in a Contract-based Wallet or Meta-Transaction-based Wallet.
     */
    fun signMessage(message: String): CompletableFuture<String>
}

/**
 * This represents the Immutable X Wallet on Layer 2 and will provide the users Stark key pair to be
 * used for signing L2 transactions.
 */
interface StarkSigner {
    /**
     * Returns a CompletableFuture that resolves to the user's L2 Stark keys.
     */
    fun getStarkKeys(): CompletableFuture<ECKeyPair>
}