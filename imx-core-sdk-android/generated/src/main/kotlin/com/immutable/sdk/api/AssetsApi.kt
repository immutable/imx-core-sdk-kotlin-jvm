/**
 * Immutable X API
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1
 * Contact: support@immutable.com
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.immutable.sdk.api

import com.immutable.sdk.api.model.Asset
import com.immutable.sdk.api.model.ListAssetsResponse

import org.json.JSONObject

import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.infrastructure.ApiErrorModel
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ClientError
import org.openapitools.client.infrastructure.ServerException
import org.openapitools.client.infrastructure.ServerError
import org.openapitools.client.infrastructure.MultiValueMap
import org.openapitools.client.infrastructure.RequestConfig
import org.openapitools.client.infrastructure.RequestMethod
import org.openapitools.client.infrastructure.ResponseType
import org.openapitools.client.infrastructure.Success
import org.openapitools.client.infrastructure.toMultiValue

class AssetsApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Get details of an asset
    * Get details of an asset
    * @param tokenAddress Address of the ERC721 contract 
    * @param tokenId Either ERC721 token ID or internal IMX ID 
    * @param includeFees Set flag to include fees associated with the asset (optional)
    * @return Asset
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getAsset(tokenAddress: kotlin.String, tokenId: kotlin.String, includeFees: kotlin.Boolean? = null) : Asset {
        val localVariableConfig = getAssetRequestConfig(tokenAddress = tokenAddress, tokenId = tokenId, includeFees = includeFees)

        val localVarResponse = request<Unit, Asset>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Asset
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                val errorModel = (localVarError.body as? String)?.let {
                    val json = JSONObject(it)
                    ApiErrorModel(json.optString("code"), json.optString("message"))
                }
                throw ClientException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                val errorModel = (localVarError.body as? String)?.let {
                    val json = JSONObject(it)
                    ApiErrorModel(json.optString("code"), json.optString("message"))
                }
                throw ServerException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
        }
    }

    /**
    * To obtain the request config of the operation getAsset
    *
    * @param tokenAddress Address of the ERC721 contract 
    * @param tokenId Either ERC721 token ID or internal IMX ID 
    * @param includeFees Set flag to include fees associated with the asset (optional)
    * @return RequestConfig
    */
    fun getAssetRequestConfig(tokenAddress: kotlin.String, tokenId: kotlin.String, includeFees: kotlin.Boolean?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (includeFees != null) {
                    put("include_fees", listOf(includeFees.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/assets/{token_address}/{token_id}".replace("{"+"token_address"+"}", "$tokenAddress").replace("{"+"token_id"+"}", "$tokenId"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of assets
    * Get a list of assets
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who owns these assets (optional)
    * @param status Status of these assets (optional)
    * @param name Name of the asset to search (optional)
    * @param metadata JSON-encoded metadata filters for these asset. Example: { (optional)
    * @param sellOrders Set flag to true to fetch an array of sell order details with accepted status associated with the asset (optional)
    * @param buyOrders Set flag to true to fetch an array of buy order details  with accepted status associated with the asset (optional)
    * @param includeFees Set flag to include fees associated with the asset (optional)
    * @param collection Collection contract address (optional)
    * @param updatedMinTimestamp Minimum timestamp for when these assets were last updated (optional)
    * @param updatedMaxTimestamp Maximum timestamp for when these assets were last updated (optional)
    * @param auxiliaryFeePercentages Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients (optional)
    * @param auxiliaryFeeRecipients Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages (optional)
    * @return ListAssetsResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listAssets(pageSize: kotlin.Int? = null, cursor: kotlin.String? = null, orderBy: kotlin.String? = null, direction: kotlin.String? = null, user: kotlin.String? = null, status: kotlin.String? = null, name: kotlin.String? = null, metadata: kotlin.String? = null, sellOrders: kotlin.Boolean? = null, buyOrders: kotlin.Boolean? = null, includeFees: kotlin.Boolean? = null, collection: kotlin.String? = null, updatedMinTimestamp: kotlin.String? = null, updatedMaxTimestamp: kotlin.String? = null, auxiliaryFeePercentages: kotlin.String? = null, auxiliaryFeeRecipients: kotlin.String? = null) : ListAssetsResponse {
        val localVariableConfig = listAssetsRequestConfig(pageSize = pageSize, cursor = cursor, orderBy = orderBy, direction = direction, user = user, status = status, name = name, metadata = metadata, sellOrders = sellOrders, buyOrders = buyOrders, includeFees = includeFees, collection = collection, updatedMinTimestamp = updatedMinTimestamp, updatedMaxTimestamp = updatedMaxTimestamp, auxiliaryFeePercentages = auxiliaryFeePercentages, auxiliaryFeeRecipients = auxiliaryFeeRecipients)

        val localVarResponse = request<Unit, ListAssetsResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListAssetsResponse
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                val errorModel = (localVarError.body as? String)?.let {
                    val json = JSONObject(it)
                    ApiErrorModel(json.optString("code"), json.optString("message"))
                }
                throw ClientException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                val errorModel = (localVarError.body as? String)?.let {
                    val json = JSONObject(it)
                    ApiErrorModel(json.optString("code"), json.optString("message"))
                }
                throw ServerException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
        }
    }

    /**
    * To obtain the request config of the operation listAssets
    *
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who owns these assets (optional)
    * @param status Status of these assets (optional)
    * @param name Name of the asset to search (optional)
    * @param metadata JSON-encoded metadata filters for these asset. Example: { (optional)
    * @param sellOrders Set flag to true to fetch an array of sell order details with accepted status associated with the asset (optional)
    * @param buyOrders Set flag to true to fetch an array of buy order details  with accepted status associated with the asset (optional)
    * @param includeFees Set flag to include fees associated with the asset (optional)
    * @param collection Collection contract address (optional)
    * @param updatedMinTimestamp Minimum timestamp for when these assets were last updated (optional)
    * @param updatedMaxTimestamp Maximum timestamp for when these assets were last updated (optional)
    * @param auxiliaryFeePercentages Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients (optional)
    * @param auxiliaryFeeRecipients Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages (optional)
    * @return RequestConfig
    */
    fun listAssetsRequestConfig(pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?, user: kotlin.String?, status: kotlin.String?, name: kotlin.String?, metadata: kotlin.String?, sellOrders: kotlin.Boolean?, buyOrders: kotlin.Boolean?, includeFees: kotlin.Boolean?, collection: kotlin.String?, updatedMinTimestamp: kotlin.String?, updatedMaxTimestamp: kotlin.String?, auxiliaryFeePercentages: kotlin.String?, auxiliaryFeeRecipients: kotlin.String?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (pageSize != null) {
                    put("page_size", listOf(pageSize.toString()))
                }
                if (cursor != null) {
                    put("cursor", listOf(cursor.toString()))
                }
                if (orderBy != null) {
                    put("order_by", listOf(orderBy.toString()))
                }
                if (direction != null) {
                    put("direction", listOf(direction.toString()))
                }
                if (user != null) {
                    put("user", listOf(user.toString()))
                }
                if (status != null) {
                    put("status", listOf(status.toString()))
                }
                if (name != null) {
                    put("name", listOf(name.toString()))
                }
                if (metadata != null) {
                    put("metadata", listOf(metadata.toString()))
                }
                if (sellOrders != null) {
                    put("sell_orders", listOf(sellOrders.toString()))
                }
                if (buyOrders != null) {
                    put("buy_orders", listOf(buyOrders.toString()))
                }
                if (includeFees != null) {
                    put("include_fees", listOf(includeFees.toString()))
                }
                if (collection != null) {
                    put("collection", listOf(collection.toString()))
                }
                if (updatedMinTimestamp != null) {
                    put("updated_min_timestamp", listOf(updatedMinTimestamp.toString()))
                }
                if (updatedMaxTimestamp != null) {
                    put("updated_max_timestamp", listOf(updatedMaxTimestamp.toString()))
                }
                if (auxiliaryFeePercentages != null) {
                    put("auxiliary_fee_percentages", listOf(auxiliaryFeePercentages.toString()))
                }
                if (auxiliaryFeeRecipients != null) {
                    put("auxiliary_fee_recipients", listOf(auxiliaryFeeRecipients.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/assets",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
