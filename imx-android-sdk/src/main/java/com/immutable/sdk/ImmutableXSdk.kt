package com.immutable.sdk

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.SellToken
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.utils.Constants.DEFAULT_CHROME_CUSTOM_TAB_ADDRESS_BAR_COLOUR
import com.immutable.sdk.utils.Constants.DEFAULT_MOONPAY_COLOUR_CODE
import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture

enum class ImmutableXBase(
    val publicApiUrl: String,
    val moonpayBuyCryptoUrl: String,
    val moonpayApiKey: String
) {
    Production(
        publicApiUrl = "https://api.x.immutable.com",
        moonpayBuyCryptoUrl = "https://buy.moonpay.io",
        moonpayApiKey = "pk_live_lgGxv3WyWjnWff44ch4gmolN0953"
    ),
    Ropsten(
        publicApiUrl = "https://api.ropsten.x.immutable.com",
        moonpayBuyCryptoUrl = "https://buy-staging.moonpay.io",
        moonpayApiKey = "pk_test_S33EgzH3XY5kfJSOS6GW0uxUWvoIUF"
    )
}

object ImmutableXSdk {

    private var base: ImmutableXBase = ImmutableXBase.Ropsten

    init {
        setBaseUrl()
    }

    /**
     * Sets the base property used by all Immutable X API classes.
     */
    fun setBase(base: ImmutableXBase) {
        this.base = base
        setBaseUrl()
    }

    private fun setBaseUrl() {
        System.getProperties().setProperty("org.openapitools.client.baseUrl", base.publicApiUrl)
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

    /**
     * This is a utility function that will chain the necessary calls to transfer a token.
     *
     * @param token to be transferred (ETH, ERC20, or ERC721)
     * @param recipientAddress of the wallet that will receive the token
     * @param starkSigner represents the users L2 wallet used to sign and verify the L2 transaction
     * @param signer represents the users L1 wallet to get the address
     *
     * @return a [CompletableFuture] that will provide the transfer id if successful.
     */
    @Suppress("LongParameterList")
    fun transfer(
        token: AssetModel,
        recipientAddress: String,
        signer: Signer,
        starkSigner: StarkSigner,
    ): CompletableFuture<Int> =
        com.immutable.sdk.workflows.transfer(token, recipientAddress, signer, starkSigner)

    /**
     * Launches a Chrome Custom Tab to buy cryptocurrencies via Moonpay
     *
     * @param colourInt (optional) the colour of the Chrome Custom Tab address bar. The default
     * value is [DEFAULT_CHROME_CUSTOM_TAB_ADDRESS_BAR_COLOUR]
     * @param colourCodeHex The color code in hex (e.g. #00818e) for the Moon pay widget main color. It is used for buttons,
     * links and highlighted text.
     * @throws Throwable if any error occurs
     */
    fun buyCrypto(
        context: Context,
        signer: Signer,
        @ColorInt colourInt: Int = Color.parseColor(DEFAULT_CHROME_CUSTOM_TAB_ADDRESS_BAR_COLOUR),
        colourCodeHex: String = DEFAULT_MOONPAY_COLOUR_CODE
    ) {
        com.immutable.sdk.workflows.buyCrypto(
            base = base,
            context = context,
            signer = signer,
            colourInt = colourInt,
            colourCodeHex = colourCodeHex
        )
    }
}
