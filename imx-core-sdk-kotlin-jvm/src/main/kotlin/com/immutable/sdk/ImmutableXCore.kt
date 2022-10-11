package com.immutable.sdk

import com.google.common.annotations.VisibleForTesting
import com.immutable.sdk.Constants.DEFAULT_MOONPAY_COLOUR_CODE
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.CompletableFuture

/**
 * An enum for defining the environment the SDK will communicate with
 */
enum class ImmutableXBase {
    Production, Ropsten, Sandbox
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
 *
 * @param base the environment the SDK will communicate with
 */
class ImmutableXCore(
    private val base: ImmutableXBase = ImmutableXBase.Production
) {
    private val depositsApi: DepositsApi = DepositsApi(ImmutableConfig.getPublicApiUrl(base))

    init {
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
     * Get details of a Deposit with the given ID
     * @parem the deposit ID
     * @throws [ImmutableException.apiError]
     */
    fun getDeposit(id: String): Deposit = apiCall("getDeposit") {
        depositsApi.getDeposit(id)
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
        com.immutable.sdk.workflows.cancelOrder(orderId, signer, starkSigner)

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
     * Gets a URL to MoonPay that provides a service for buying crypto directly on Immutable in exchange for fiat.
     *
     * It is recommended to open this URL in a Chrome Custom Tab.
     *
     * @param signer represents the users L1 wallet to get the address
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

    @Suppress("TooGenericExceptionCaught")
    private fun <T> apiCall(callName: String, call: () -> T): T {
        try {
            return call()
        } catch (e: Exception) {
            throw ImmutableException.apiError(callName, e)
        }
    }

    companion object {
        internal var httpLoggingLevel = ImmutableXHttpLoggingLevel.None
    }
}
