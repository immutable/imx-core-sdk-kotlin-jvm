package com.immutable.sdk

import com.google.common.annotations.VisibleForTesting
import com.immutable.sdk.Constants.DEFAULT_MOONPAY_COLOUR_CODE
import com.immutable.sdk.api.AssetsApi
import com.immutable.sdk.api.BalancesApi
import com.immutable.sdk.api.CollectionsApi
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.MetadataApi
import com.immutable.sdk.api.MintsApi
import com.immutable.sdk.api.ProjectsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.WithdrawalsApi
import com.immutable.sdk.api.model.Collection
import com.immutable.sdk.api.model.CreateCollectionRequest
import com.immutable.sdk.api.model.CreateTransferResponse
import com.immutable.sdk.api.model.CreateOrderResponse
import com.immutable.sdk.api.model.CreateTradeResponse
import com.immutable.sdk.api.model.FeeEntry
import com.immutable.sdk.api.model.CancelOrderResponse
import com.immutable.sdk.api.model.CreateProjectRequest
import com.immutable.sdk.api.model.Deposit
import com.immutable.sdk.api.model.UpdateCollectionRequest
import com.immutable.sdk.api.model.AddMetadataSchemaToCollectionRequest
import com.immutable.sdk.api.model.MetadataSchemaRequest
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.workflows.call
import com.immutable.sdk.workflows.imxTimestampRequest
import okhttp3.logging.HttpLoggingInterceptor
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ServerException
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
 * @param nodeUrl (optional) Ethereum node URL. This is only required for deposits and withdrawals.
 */
@Suppress("TooManyFunctions")
class ImmutableX(
    private val base: ImmutableXBase = ImmutableXBase.Production,
    private val nodeUrl: String? = null
) {
    private val assetsApi: AssetsApi by lazy { AssetsApi() }
    private val balancesApi: BalancesApi by lazy { BalancesApi() }
    private val collectionsApi: CollectionsApi by lazy { CollectionsApi() }
    private val depositsApi: DepositsApi by lazy { DepositsApi() }
    private val metadataApi: MetadataApi by lazy { MetadataApi() }
    private val mintsApi: MintsApi by lazy { MintsApi() }
    private val projectsApi: ProjectsApi by lazy { ProjectsApi() }
    private val usersApi: UsersApi by lazy { UsersApi() }
    private val withdrawalsApi: WithdrawalsApi by lazy { WithdrawalsApi() }
    private val encodingApi: EncodingApi by lazy { EncodingApi() }

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
     * Deposit based on a token type.
     * For unregistered users, the deposit will be combined with a registration in order to
     * register the user first.
     * @param signer the L1 signer
     * @param token the token type amount in its corresponding unit
     * @returns a [CompletableFuture] that will provide the transaction hash if successful.
     */
    fun deposit(signer: Signer, token: AssetModel): CompletableFuture<String> {
        checkNotNull(nodeUrl) { "nodeUrl cannot be null when depositing" }
        return com.immutable.sdk.workflows.deposit(
            base,
            nodeUrl,
            token,
            signer,
            depositsApi,
            usersApi,
            encodingApi
        )
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
     * Get stark keys for a registered user
     * @param ethAddress The user's Ethereum address
     * @return GetUsersApiResponse
     * @throws [ImmutableException.apiError]
     */
    fun getUser(ethAddress: String) = apiCall("getUser") {
        usersApi.getUsers(ethAddress)
    }

    /**
     * Get details of an asset
     *
     * @param tokenAddress Address of the ERC721 contract
     * @param tokenId Either ERC721 token ID or internal IMX ID
     * @param includeFees Set flag to include fees associated with the asset (optional)
     * @return Asset
     * @throws [ImmutableException.apiError]
     */
    fun getAsset(tokenAddress: String, tokenId: String, includeFees: Boolean? = null) = apiCall("getAsset") {
        assetsApi.getAsset(tokenAddress, tokenId, includeFees)
    }

    /**
     * Get a list of assets
     *
     * @param pageSize Page size of the result (optional)
     * @param cursor Cursor (optional)
     * @param orderBy Property to sort by (optional)
     * @param direction Direction to sort (asc/desc) (optional)
     * @param user Ethereum address of the user who owns these assets (optional)
     * @param status Status of these assets (optional)
     * @param name Name of the asset to search (optional)
     * @param metadata JSON-encoded metadata filters for these asset. Example: {&#39;proto&#39;:[&#39;1147&#39;],&#39;quality&#39;:[&#39;Meteorite&#39;]} (optional)
     * @param sellOrders Set flag to true to fetch an array of sell order details with accepted status associated with the asset (optional)
     * @param buyOrders Set flag to true to fetch an array of buy order details  with accepted status associated with the asset (optional)
     * @param includeFees Set flag to include fees associated with the asset (optional)
     * @param collection Collection contract address (optional)
     * @param updatedMinTimestamp Minimum timestamp for when these assets were last updated, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; (optional)
     * @param updatedMaxTimestamp Maximum timestamp for when these assets were last updated, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; (optional)
     * @param auxiliaryFeePercentages Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients (optional)
     * @param auxiliaryFeeRecipients Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages (optional)
     * @return ListAssetsResponse
     * @throws [ImmutableException.apiError]
     */
    @Suppress("LongParameterList")
    fun listAssets(
        pageSize: Int? = null,
        cursor: String? = null,
        orderBy: String? = null,
        direction: String? = null,
        user: String? = null,
        status: String? = null,
        name: String? = null,
        metadata: String? = null,
        sellOrders: Boolean? = null,
        buyOrders: Boolean? = null,
        includeFees: Boolean? = null,
        collection: String? = null,
        updatedMinTimestamp: String? = null,
        updatedMaxTimestamp: String? = null,
        auxiliaryFeePercentages: String? = null,
        auxiliaryFeeRecipients: String? = null
    ) = apiCall("listAssets") {
        assetsApi.listAssets(
            pageSize,
            cursor,
            orderBy,
            direction,
            user,
            status,
            name,
            metadata,
            sellOrders,
            buyOrders,
            includeFees,
            collection,
            updatedMinTimestamp,
            updatedMaxTimestamp,
            auxiliaryFeePercentages,
            auxiliaryFeeRecipients
        )
    }

    /**
     * Create collection
     *
     * @param createCollectionRequest create a collection
     * @param signer represents the users L1 wallet to get the address and sign the registration
     * @return CompletableFuture<Collection>
     */
    fun createCollection(request: CreateCollectionRequest, signer: Signer): CompletableFuture<Collection> =
        imxTimestampRequest(signer) { timestamp ->
            call("createCollection") {
                collectionsApi.createCollection(timestamp.signature, timestamp.timestamp, request)
            }
        }

    /**
     * Get details of a collection at the given address
     *
     * @param address Collection contract address
     * @return Collection
     * @throws [ImmutableException.apiError]
     */
    fun getCollection(address: String) = apiCall("getCollection") { collectionsApi.getCollection(address) }

    /**
     * Get a list of collection filters
     *
     * @param address Collection contract address
     * @param pageSize Page size of the result (optional)
     * @param nextPageToken Next page token (optional)
     * @return CollectionFilter
     * @throws [ImmutableException.apiError]
     */
    fun listCollectionFilters(
        address: String,
        pageSize: Int? = null,
        nextPageToken: String? = null
    ) = apiCall("listCollectionFilters") {
        collectionsApi.listCollectionFilters(address, pageSize, nextPageToken)
    }

    /**
     * Get a list of collections
     *
     * @param pageSize Page size of the result (optional)
     * @param cursor Cursor (optional)
     * @param orderBy Property to sort by (optional)
     * @param direction Direction to sort (asc/desc) (optional)
     * @param blacklist List of collections not to be included, separated by commas (optional)
     * @param whitelist List of collections to be included, separated by commas (optional)
     * @param keyword Keyword to search in collection name and description (optional)
     * @return ListCollectionsResponse
     * @throws [ImmutableException.apiError]
     */
    @Suppress("LongParameterList")
    fun listCollections(
        pageSize: Int? = null,
        cursor: String? = null,
        orderBy: String? = null,
        direction: String? = null,
        blacklist: String? = null,
        whitelist: String? = null,
        keyword: String? = null
    ) = apiCall("listCollections") {
        collectionsApi.listCollections(pageSize, cursor, orderBy, direction, blacklist, whitelist, keyword)
    }

    /**
     * Update collection
     *
     * @param address Collection contract address
     * @param updateCollectionRequest update a collection
     * @param signer represents the users L1 wallet to get the address and sign the registration
     * @return CompletableFuture<Collection>
     */
    fun updateCollection(
        address: String,
        updateCollectionRequest: UpdateCollectionRequest,
        signer: Signer
    ) =
        imxTimestampRequest(signer) { timestamp ->
            call("updateCollection") {
                collectionsApi.updateCollection(
                    address,
                    timestamp.signature,
                    timestamp.timestamp,
                    updateCollectionRequest
                )
            }
        }

    /**
     * Add metadata schema to collection
     *
     * @param address Collection contract address
     * @param addMetadataSchemaToCollectionRequest add metadata schema to a collection
     * @param signer represents the users L1 wallet to get the address and sign the registration
     * @return CompletableFuture<SuccessResponse>
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    fun addMetadataSchemaToCollection(
        address: String,
        addMetadataSchemaToCollectionRequest: AddMetadataSchemaToCollectionRequest,
        signer: Signer
    ) =
        imxTimestampRequest(signer) { timestamp ->
            call("addMetadataSchemaToCollection") {
                metadataApi.addMetadataSchemaToCollection(
                    address,
                    timestamp.signature,
                    timestamp.timestamp,
                    addMetadataSchemaToCollectionRequest
                )
            }
        }

    /**
     * Get collection metadata schema
     *
     * @param address Collection contract address
     * @return kotlin.collections.List<MetadataSchemaProperty>
     * @throws [ImmutableException.apiError]
     */
    fun getMetadataSchema(address: String) = apiCall("getMetadataSchema") {
        metadataApi.getMetadataSchema(address)
    }

    /**
     * Update metadata schema by name
     *
     * @param address Collection contract address
     * @param name Metadata schema name
     * @param metadataSchemaRequest update metadata schema
     * @param signer represents the users L1 wallet to get the address and sign the registration
     * @return CompletableFuture<SuccessResponse>
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    fun updateMetadataSchemaByName(
        address: String,
        name: String,
        metadataSchemaRequest: MetadataSchemaRequest,
        signer: Signer
    ) = imxTimestampRequest(signer) { timestamp ->
        call("updateMetadataSchemaByName") {
            metadataApi.updateMetadataSchemaByName(
                address,
                name,
                timestamp.signature,
                timestamp.timestamp,
                metadataSchemaRequest
            )
        }
    }

    /**
     * Create a project
     *
     * @param createProjectRequest create a project
     * @param signer represents the users L1 wallet to get the address and sign the registration
     * @return CompletableFuture<CreateProjectResponse>
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    fun createProject(
        createProjectRequest: CreateProjectRequest,
        signer: Signer
    ) = imxTimestampRequest(signer) { timestamp ->
        call("createProject") {
            projectsApi.createProject(
                timestamp.signature,
                timestamp.timestamp,
                createProjectRequest
            )
        }
    }

    /**
     * Get a project
     *
     * @param id Project ID
     * @param signer represents the users L1 wallet to get the address and sign the registration
     * @return CompletableFuture<Project>
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    fun getProject(id: String, signer: Signer) = imxTimestampRequest(signer) { timestamp ->
        call("getProject") {
            projectsApi.getProject(
                id,
                timestamp.signature,
                timestamp.timestamp
            )
        }
    }

    /**
     * Get projects
     *
     * @param pageSize Page size of the result (optional)
     * @param cursor Cursor (optional)
     * @param orderBy Property to sort by (optional)
     * @param direction Direction to sort (asc/desc) (optional)
     * * @param signer represents the users L1 wallet to get the address and sign the registration
     * @return CompletableFuture<GetProjectsResponse>
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    fun getProjects(
        pageSize: Int? = null,
        cursor: String? = null,
        orderBy: String? = null,
        direction: String? = null,
        signer: Signer
    ) = imxTimestampRequest(signer) { timestamp ->
        call("getProjects") {
            projectsApi.getProjects(
                timestamp.signature,
                timestamp.timestamp,
                pageSize,
                cursor,
                orderBy,
                direction
            )
        }
    }

    /**
     * Fetches the token balances of the user
     *
     * @param owner Address of the owner/user
     * @param address Token address
     * @return Balance
     * @throws [ImmutableException.apiError]
     */
    fun getBalance(owner: String, address: String) = apiCall("getBalance") {
        balancesApi.getBalance(owner, address)
    }

    /**
     * Get a list of balances for given user
     *
     * @param owner Ethereum wallet address for user
     * @return ListBalancesResponse
     * @throws [ImmutableException.apiError]
     */
    fun listBalances(owner: String) = apiCall("listBalances") {
        balancesApi.listBalances(owner)
    }

    /**
     * Get details of a mint with the given ID
     *
     * @param id Mint ID. This is the transaction_id returned from listMints
     * @return Mint
     * @throws [ImmutableException.apiError]
     */
    fun getMint(id: String) = apiCall("getMint") {
        mintsApi.getMint(id)
    }

    /**
     * Get a list of mints
     *
     * @param pageSize Page size of the result (optional)
     * @param cursor Cursor (optional)
     * @param orderBy Property to sort by (optional)
     * @param direction Direction to sort (asc/desc) (optional)
     * @param user Ethereum address of the user who submitted this mint (optional)
     * @param status Status of this mint (optional)
     * @param updatedMinTimestamp Minimum timestamp for this mint, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; (optional)
     * @param updatedMaxTimestamp Maximum timestamp for this mint, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; (optional)
     * @param tokenType Token type of the minted asset (optional)
     * @param tokenId ERC721 Token ID of the minted asset (optional)
     * @param assetId Internal IMX ID of the minted asset (optional)
     * @param tokenName Token Name of the minted asset (optional)
     * @param tokenAddress Token address of the minted asset (optional)
     * @param minQuantity Min quantity for the minted asset (optional)
     * @param maxQuantity Max quantity for the minted asset (optional)
     * @param metadata JSON-encoded metadata filters for the minted asset (optional)
     * @return ListMintsResponse
     * @throws [ImmutableException.apiError]
     */
    @Suppress("LongParameterList")
    fun listMints(
        pageSize: Int? = null,
        cursor: String? = null,
        orderBy: String? = null,
        direction: String? = null,
        user: String? = null,
        status: String? = null,
        updatedMinTimestamp: String? = null,
        updatedMaxTimestamp: String? = null,
        tokenType: String? = null,
        tokenId: String? = null,
        assetId: String? = null,
        tokenName: String? = null,
        tokenAddress: String? = null,
        minQuantity: String? = null,
        maxQuantity: String? = null,
        metadata: String? = null
    ) = apiCall("listMints") {
        mintsApi.listMints(
            pageSize,
            cursor,
            orderBy,
            direction,
            user,
            status,
            updatedMinTimestamp,
            updatedMaxTimestamp,
            tokenType,
            tokenId,
            assetId,
            tokenName,
            tokenAddress,
            minQuantity,
            maxQuantity,
            metadata
        )
    }

    /**
     * Get a list of withdrawals
     *
     * @param withdrawnToWallet Withdrawal has been transferred to user&#39;s Layer 1 wallet (optional)
     * @param rollupStatus Status of the on-chain batch confirmation for this withdrawal (optional)
     * @param pageSize Page size of the result (optional)
     * @param cursor Cursor (optional)
     * @param orderBy Property to sort by (optional)
     * @param direction Direction to sort (asc/desc) (optional)
     * @param user Ethereum address of the user who submitted this withdrawal (optional)
     * @param status Status of this withdrawal (optional)
     * @param minTimestamp Minimum timestamp for this deposit, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; (optional)
     * @param maxTimestamp Maximum timestamp for this deposit, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; (optional)
     * @param tokenType Token type of the withdrawn asset (optional)
     * @param tokenId ERC721 Token ID of the minted asset (optional)
     * @param assetId Internal IMX ID of the minted asset (optional)
     * @param tokenAddress Token address of the withdrawn asset (optional)
     * @param tokenName Token name of the withdrawn asset (optional)
     * @param minQuantity Min quantity for the withdrawn asset (optional)
     * @param maxQuantity Max quantity for the withdrawn asset (optional)
     * @param metadata JSON-encoded metadata filters for the withdrawn asset (optional)
     * @return ListWithdrawalsResponse
     * @throws [ImmutableException.apiError]
     */
    @Suppress("LongParameterList")
    fun listWithdrawals(
        withdrawnToWallet: Boolean? = null,
        rollupStatus: String? = null,
        pageSize: Int? = null,
        cursor: String? = null,
        orderBy: String? = null,
        direction: String? = null,
        user: String? = null,
        status: String? = null,
        minTimestamp: String? = null,
        maxTimestamp: String? = null,
        tokenType: String? = null,
        tokenId: String? = null,
        assetId: String? = null,
        tokenAddress: String? = null,
        tokenName: String? = null,
        minQuantity: String? = null,
        maxQuantity: String? = null,
        metadata: String? = null
    ) = apiCall("listWithdrawals") {
        withdrawalsApi.listWithdrawals(
            withdrawnToWallet,
            rollupStatus,
            pageSize,
            cursor,
            orderBy,
            direction,
            user,
            status,
            minTimestamp,
            maxTimestamp,
            tokenType,
            tokenId,
            assetId,
            tokenAddress,
            tokenName,
            minQuantity,
            maxQuantity,
            metadata
        )
    }

    /**
     * Gets details of withdrawal with the given ID
     *
     * @param id Withdrawal ID
     * @return Withdrawal
     * @throws [ImmutableException.apiError]
     */
    fun getWithdrawal(id: String) = apiCall("getWithdrawal") {
        withdrawalsApi.getWithdrawal(id)
    }

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
