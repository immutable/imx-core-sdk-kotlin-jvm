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

import com.immutable.sdk.api.model.Collection
import com.immutable.sdk.api.model.CollectionFilter
import com.immutable.sdk.api.model.CreateCollectionRequest
import com.immutable.sdk.api.model.ListCollectionsResponse
import com.immutable.sdk.api.model.UpdateCollectionRequest

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

class CollectionsApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Create collection
    * Create collection
    * @param imXSignature String created by signing wallet address and timestamp. See https://docs.x.immutable.com/docs/generate-imx-signature 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param createCollectionRequest create a collection 
    * @return Collection
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun createCollection(imXSignature: kotlin.String, imXTimestamp: kotlin.String, createCollectionRequest: CreateCollectionRequest) : Collection {
        val localVariableConfig = createCollectionRequestConfig(imXSignature = imXSignature, imXTimestamp = imXTimestamp, createCollectionRequest = createCollectionRequest)

        val localVarResponse = request<CreateCollectionRequest, Collection>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Collection
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ClientException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ServerException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
        }
    }

    /**
    * To obtain the request config of the operation createCollection
    *
    * @param imXSignature String created by signing wallet address and timestamp. See https://docs.x.immutable.com/docs/generate-imx-signature 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param createCollectionRequest create a collection 
    * @return RequestConfig
    */
    fun createCollectionRequestConfig(imXSignature: kotlin.String, imXTimestamp: kotlin.String, createCollectionRequest: CreateCollectionRequest) : RequestConfig<CreateCollectionRequest> {
        val localVariableBody = createCollectionRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        imXSignature.apply { localVariableHeaders["IMX-Signature"] = this.toString() }
        imXTimestamp.apply { localVariableHeaders["IMX-Timestamp"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/collections",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get details of a collection at the given address
    * Get details of a collection at the given address
    * @param address Collection contract address 
    * @return Collection
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getCollection(address: kotlin.String) : Collection {
        val localVariableConfig = getCollectionRequestConfig(address = address)

        val localVarResponse = request<Unit, Collection>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Collection
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ClientException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ServerException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
        }
    }

    /**
    * To obtain the request config of the operation getCollection
    *
    * @param address Collection contract address 
    * @return RequestConfig
    */
    fun getCollectionRequestConfig(address: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/collections/{address}".replace("{"+"address"+"}", "$address"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of collection filters
    * Get a list of collection filters
    * @param address Collection contract address 
    * @param pageSize Page size of the result (optional)
    * @param nextPageToken Next page token (optional)
    * @return CollectionFilter
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listCollectionFilters(address: kotlin.String, pageSize: kotlin.Int? = null, nextPageToken: kotlin.String? = null) : CollectionFilter {
        val localVariableConfig = listCollectionFiltersRequestConfig(address = address, pageSize = pageSize, nextPageToken = nextPageToken)

        val localVarResponse = request<Unit, CollectionFilter>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CollectionFilter
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ClientException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ServerException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
        }
    }

    /**
    * To obtain the request config of the operation listCollectionFilters
    *
    * @param address Collection contract address 
    * @param pageSize Page size of the result (optional)
    * @param nextPageToken Next page token (optional)
    * @return RequestConfig
    */
    fun listCollectionFiltersRequestConfig(address: kotlin.String, pageSize: kotlin.Int?, nextPageToken: kotlin.String?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (pageSize != null) {
                    put("page_size", listOf(pageSize.toString()))
                }
                if (nextPageToken != null) {
                    put("next_page_token", listOf(nextPageToken.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/collections/{address}/filters".replace("{"+"address"+"}", "$address"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of collections
    * Get a list of collections
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param blacklist List of collections not to be included, separated by commas (optional)
    * @param whitelist List of collections to be included, separated by commas (optional)
    * @param keyword Keyword to search in collection name and description (optional)
    * @return ListCollectionsResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listCollections(pageSize: kotlin.Int? = null, cursor: kotlin.String? = null, orderBy: kotlin.String? = null, direction: kotlin.String? = null, blacklist: kotlin.String? = null, whitelist: kotlin.String? = null, keyword: kotlin.String? = null) : ListCollectionsResponse {
        val localVariableConfig = listCollectionsRequestConfig(pageSize = pageSize, cursor = cursor, orderBy = orderBy, direction = direction, blacklist = blacklist, whitelist = whitelist, keyword = keyword)

        val localVarResponse = request<Unit, ListCollectionsResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListCollectionsResponse
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ClientException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ServerException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
        }
    }

    /**
    * To obtain the request config of the operation listCollections
    *
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param blacklist List of collections not to be included, separated by commas (optional)
    * @param whitelist List of collections to be included, separated by commas (optional)
    * @param keyword Keyword to search in collection name and description (optional)
    * @return RequestConfig
    */
    fun listCollectionsRequestConfig(pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?, blacklist: kotlin.String?, whitelist: kotlin.String?, keyword: kotlin.String?) : RequestConfig<Unit> {
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
                if (blacklist != null) {
                    put("blacklist", listOf(blacklist.toString()))
                }
                if (whitelist != null) {
                    put("whitelist", listOf(whitelist.toString()))
                }
                if (keyword != null) {
                    put("keyword", listOf(keyword.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/collections",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Update collection
    * Update collection
    * @param address Collection contract address 
    * @param imXSignature String created by signing wallet address and timestamp 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param updateCollectionRequest update a collection 
    * @return Collection
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun updateCollection(address: kotlin.String, imXSignature: kotlin.String, imXTimestamp: kotlin.String, updateCollectionRequest: UpdateCollectionRequest) : Collection {
        val localVariableConfig = updateCollectionRequestConfig(address = address, imXSignature = imXSignature, imXTimestamp = imXTimestamp, updateCollectionRequest = updateCollectionRequest)

        val localVarResponse = request<UpdateCollectionRequest, Collection>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Collection
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ClientException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                val errorModel = localVarError.body?.let { ApiErrorModel(localVarError.body) }
                throw ServerException("${localVarError.statusCode} ${errorModel?.message ?: localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse, errorModel)
            }
        }
    }

    /**
    * To obtain the request config of the operation updateCollection
    *
    * @param address Collection contract address 
    * @param imXSignature String created by signing wallet address and timestamp 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param updateCollectionRequest update a collection 
    * @return RequestConfig
    */
    fun updateCollectionRequestConfig(address: kotlin.String, imXSignature: kotlin.String, imXTimestamp: kotlin.String, updateCollectionRequest: UpdateCollectionRequest) : RequestConfig<UpdateCollectionRequest> {
        val localVariableBody = updateCollectionRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        imXSignature.apply { localVariableHeaders["IMX-Signature"] = this.toString() }
        imXTimestamp.apply { localVariableHeaders["IMX-Timestamp"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.PATCH,
            path = "/v1/collections/{address}".replace("{"+"address"+"}", "$address"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
