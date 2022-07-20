package com.immutable.sdk

import com.google.common.annotations.VisibleForTesting
import com.immutable.sdk.Constants.DEFAULT_MOONPAY_COLOUR_CODE
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.CompletableFuture

/**
 * An enum for defining the environment the SDK will communicate with
 */
enum class ImmutableXBase {
    Production, Ropsten
}

/**
 * An enum for defining the log level of the API HTTP calls
 */
enum class ImmutableXHttpLoggingLevel {
    /**
     * No logs
     *
     * @see [HttpLoggingInterceptor.Level.NONE]
     */
    None,

    /**
     * Logs request and response lines
     *
     * @see [HttpLoggingInterceptor.Level.BASIC]
     */
    Basic,

    /**
     * Logs request and response lines and their respective headers
     *
     * @see [HttpLoggingInterceptor.Level.HEADERS]
     */
    Headers,

    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     *
     * @see [HttpLoggingInterceptor.Level.BODY]
     */
    Body
}

@VisibleForTesting
internal const val KEY_BASE_URL = "org.openapitools.client.baseUrl"

/**
 * This is the entry point for the Immutable X SDK.
 *
 * You can configure the environment or use any of the provided utility workflows which chain
 * the necessary calls to perform standard actions (e.g. buy, sell etc).
 */
object ImmutableXCore {

    private var base: ImmutableXBase = ImmutableXBase.Ropsten
    internal var httpLoggingLevel = ImmutableXHttpLoggingLevel.None

    init {
        setBaseUrl()
    }

    /**
     * Sets the environment the SDK will communicate with
     */
    fun setBase(base: ImmutableXBase) {
        this.base = base
        setBaseUrl()
    }

    private fun setBaseUrl() {
        System.getProperties()
            .setProperty(KEY_BASE_URL, ImmutableConfig.getPublicApiUrl(base))
    }

    /**
     * Sets the API HTTP logging level. The default is [ImmutableXHttpLoggingLevel.None].
     */
    fun setHttpLoggingLevel(level: ImmutableXHttpLoggingLevel) {
        httpLoggingLevel = level
    }

    /**
     * This is a utility function that will register a user to Immutable X if they aren't already.
     *
     * @param signer represents the users L1 wallet to get the address and sign the registration
     *
     * @return a [CompletableFuture] with the value [Unit] being provided on success. If it failed
     * a [Throwable] will be given.
     */
    fun registerOffChain(signer: Signer, starkSigner: StarkSigner): CompletableFuture<Unit> =
        com.immutable.sdk.workflows.registerOffChain(signer, starkSigner)

    /**
     * This is a utility function that will chain the necessary calls to buy an existing order.
     *
     * @param orderId the id of an existing order to be bought
     * @param fees taker fees information to be used in the buy order.
     * @param signer represents the users L1 wallet to get the address
     * @param starkSigner represents the users L2 wallet used to sign and verify the L2 transaction
     *
     * @return a [CompletableFuture] that will provide the Trade id if successful.
     */
    fun buy(
        orderId: String,
        fees: List<FeeEntry> = emptyList(),
        signer: Signer,
        starkSigner: StarkSigner
    ): CompletableFuture<CreateTradeResponse> =
        com.immutable.sdk.workflows.buy(orderId, fees, signer, starkSigner)

    /**
     * This is a utility function that will chain the necessary calls to sell an ERC721 asset.
     *
     * @param asset the ERC721 asset to sell
     * @param sellToken the type of token and how much of it to sell the ERC721 asset for
     * @param fees maker fees information to be used in the sell order.
     * @param signer represents the users L1 wallet to get the address
     * @param starkSigner represents the users L2 wallet used to sign and verify the L2 transaction
     *
     * @return a [CompletableFuture] that will provide the Order id if successful.
     */
    @Suppress("LongParameterList")
    fun sell(
        asset: Erc721Asset,
        sellToken: AssetModel,
        fees: List<FeeEntry> = emptyList(),
        signer: Signer,
        starkSigner: StarkSigner
    ): CompletableFuture<CreateOrderResponse> {
        return com.immutable.sdk.workflows.sell(
            asset,
            sellToken,
            fees,
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
     * @return a [CompletableFuture] that will provide the cancelled Order id if successful.
     */
    @Suppress("LongParameterList")
    fun cancelOrder(
        orderId: String,
        signer: Signer,
        starkSigner: StarkSigner
    ): CompletableFuture<CancelOrderResponse> =
        com.immutable.sdk.workflows.cancel(orderId, signer, starkSigner)

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
    ): CompletableFuture<CreateTransferResponse> =
        com.immutable.sdk.workflows.transfer(token, recipientAddress, signer, starkSigner)

    /**
     * Launches a Chrome Custom Tab to buy cryptocurrencies via Moonpay
     *
     * @param context the context for launching the Custom Tabs activity
     * @param signer represents the users L1 wallet to get the address
     * @param colourInt (optional) the colour of the Chrome Custom Tab address bar. The default
     * value is [DEFAULT_CHROME_CUSTOM_TAB_ADDRESS_BAR_COLOUR]
     * @param colourCodeHex The color code in hex (e.g. #00818e) for the Moon pay widget main color. It is used for buttons,
     * links and highlighted text.
     * @throws Throwable if any error occurs
     */
    fun buyCrypto(
        signer: Signer,
        colourCodeHex: String = DEFAULT_MOONPAY_COLOUR_CODE
    ): CompletableFuture<String> =
        com.immutable.sdk.workflows.buyCrypto(
            base = base,
            signer = signer,
            colourCodeHex = colourCodeHex
        )
}
