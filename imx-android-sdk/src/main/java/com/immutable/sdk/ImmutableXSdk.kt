package com.immutable.sdk

import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.workflows.SellToken
import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture

enum class ImmutableXBase(val url: String) {
    Production("https://api.x.immutable.com"),
    Ropsten("https://api.ropsten.x.immutable.com")
}

object ImmutableXSdk {
    /**
     * Sets the base property used by all Immutable X API classes.
     */
    fun setBase(base: ImmutableXBase) {
        System.getProperties().setProperty("org.openapitools.client.baseUrl", base.url)
    }

    /**
     * This function is for signing L2 transactions
     *
     * @param keyPair keys used for the users L2 wallet
     * @param message to be signed by the key pair
     */
    fun starkSign(keyPair: ECKeyPair, message: String): String {
        return StarkCurve.sign(keyPair, message)
    }

    /**
     * This is a utility function that will register a user to Immutable X if they aren't already
     * and then return their Stark key pair.
     *
     * @param signer represents the users L1 wallet to get the address and sign the registration
     *
     * @return a [CompletableFuture] that will provide the Stark key pair if successful. This
     * key pair needs to be securely stored as exposing this risks the users assets and wallet.
     */
    fun login(signer: Signer): CompletableFuture<ECKeyPair> =
        com.immutable.sdk.workflows.login(signer)

    /**
     * This is a utility function that will chain the necessary calls to buy an existing order.
     *
     * @param orderId the id of an existing order to be bought
     * @param signer represents the users L1 wallet to get the address
     * @param starkSigner represents the users L2 wallet used to sign and verify the L2 transaction
     *
     * @return a [CompletableFuture] that will provide the Trade id if successful.
     */
    fun buy(
        orderId: String,
        signer: Signer,
        starkSigner: StarkSigner
    ): CompletableFuture<Int> =
        com.immutable.sdk.workflows.buy(orderId, signer, starkSigner)

    /**
     * This is a utility function that will chain the necessary calls to sell an ERC721 asset.
     *
     * @param asset the ERC721 asset to sell
     * @param sellAmount the amount to sell the ERC721 asset
     * @param sellToken the type of token to be used for the [sellAmount]. See [SellToken].
     * @param signer represents the users L1 wallet to get the address
     * @param starkSigner represents the users L2 wallet used to sign and verify the L2 transaction
     *
     * @return a [CompletableFuture] that will provide the cancelled Order id if successful.
     */
    fun sell(
        asset: Erc721Asset,
        sellAmount: String,
        sellToken: SellToken,
        signer: Signer,
        starkSigner: StarkSigner
    ): CompletableFuture<Int> {
        return com.immutable.sdk.workflows.sell(
            asset,
            sellAmount,
            sellToken,
            signer,
            starkSigner
        )
    }

    /**
     * This is a utility function that will chain the necessary calls to cancel an existing order.
     *
     * @param orderId the id of an existing order to be bought
     * @param starkSigner represents the users L2 wallet used to sign and verify the L2 transaction
     *
     * @return a [CompletableFuture] that will provide the Order id if successful.
     */
    @Suppress("LongParameterList")
    fun cancel(
        orderId: String,
        starkSigner: StarkSigner
    ): CompletableFuture<Int> =
        com.immutable.sdk.workflows.cancel(orderId, starkSigner)
}
