package com.immutable.sdk

import java.util.concurrent.CompletableFuture

interface Signer {
    /**
     * Returns a CompletableFuture that resolves to the account address.
     *
     * This is a CompletableFuture so that a Signer can be designed around an asynchronous source,
     * such as hardware wallets.
     */
    fun getAddress(): CompletableFuture<String>

    /**
     * A signed message is prefixd with "\x19Ethereum Signed Message:\n" and the length of the
     * message, using the hashMessage method, so that it is EIP-191 compliant. If recovering the
     * address in Solidity, this prefix will be required to create a matching hash.
     *
     * Sub-classes must implement this, however they may throw if signing a message is not
     * supported, such as in a Contract-based Wallet or Meta-Transaction-based Wallet.
     */
    fun signMessage(address: String, message: String): CompletableFuture<String>
}
