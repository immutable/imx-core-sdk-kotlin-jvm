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

import com.immutable.sdk.api.model.Deposit
import com.immutable.sdk.api.model.GetSignableDepositRequest
import com.immutable.sdk.api.model.GetSignableDepositResponse
import com.immutable.sdk.api.model.ListDepositsResponse

import org.openapitools.client.infrastructure.ApiClient
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

class DepositsApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Get details of a deposit with the given ID
    * Get details of a deposit with the given ID
    * @param id Deposit ID 
    * @return Deposit
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getDeposit(id: kotlin.String) : Deposit {
        val localVariableConfig = getDepositRequestConfig(id = id)

        val localVarResponse = request<Unit, Deposit>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Deposit
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * To obtain the request config of the operation getDeposit
    *
    * @param id Deposit ID 
    * @return RequestConfig
    */
    fun getDepositRequestConfig(id: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/deposits/{id}".replace("{"+"id"+"}", "$id"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Gets details of a signable deposit
    * Gets details of a signable deposit
    * @param getSignableDepositRequest Get details of signable deposit 
    * @return GetSignableDepositResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getSignableDeposit(getSignableDepositRequest: GetSignableDepositRequest) : GetSignableDepositResponse {
        val localVariableConfig = getSignableDepositRequestConfig(getSignableDepositRequest = getSignableDepositRequest)

        val localVarResponse = request<GetSignableDepositRequest, GetSignableDepositResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetSignableDepositResponse
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * To obtain the request config of the operation getSignableDeposit
    *
    * @param getSignableDepositRequest Get details of signable deposit 
    * @return RequestConfig
    */
    fun getSignableDepositRequestConfig(getSignableDepositRequest: GetSignableDepositRequest) : RequestConfig<GetSignableDepositRequest> {
        val localVariableBody = getSignableDepositRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/signable-deposit-details",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of deposits
    * Get a list of deposits
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this deposit (optional)
    * @param status Status of this deposit (optional)
    * @param minTimestamp Minimum timestamp for this deposit (optional)
    * @param maxTimestamp Maximum timestamp for this deposit (optional)
    * @param tokenType Token type of the deposited asset (optional)
    * @param tokenId ERC721 Token ID of the minted asset (optional)
    * @param assetId Internal IMX ID of the minted asset (optional)
    * @param tokenAddress Token address of the deposited asset (optional)
    * @param tokenName Token name of the deposited asset (optional)
    * @param minQuantity Min quantity for the deposited asset (optional)
    * @param maxQuantity Max quantity for the deposited asset (optional)
    * @param metadata JSON-encoded metadata filters for the deposited asset (optional)
    * @return ListDepositsResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listDeposits(pageSize: kotlin.Int? = null, cursor: kotlin.String? = null, orderBy: kotlin.String? = null, direction: kotlin.String? = null, user: kotlin.String? = null, status: kotlin.String? = null, minTimestamp: kotlin.String? = null, maxTimestamp: kotlin.String? = null, tokenType: kotlin.String? = null, tokenId: kotlin.String? = null, assetId: kotlin.String? = null, tokenAddress: kotlin.String? = null, tokenName: kotlin.String? = null, minQuantity: kotlin.String? = null, maxQuantity: kotlin.String? = null, metadata: kotlin.String? = null) : ListDepositsResponse {
        val localVariableConfig = listDepositsRequestConfig(pageSize = pageSize, cursor = cursor, orderBy = orderBy, direction = direction, user = user, status = status, minTimestamp = minTimestamp, maxTimestamp = maxTimestamp, tokenType = tokenType, tokenId = tokenId, assetId = assetId, tokenAddress = tokenAddress, tokenName = tokenName, minQuantity = minQuantity, maxQuantity = maxQuantity, metadata = metadata)

        val localVarResponse = request<Unit, ListDepositsResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListDepositsResponse
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * To obtain the request config of the operation listDeposits
    *
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this deposit (optional)
    * @param status Status of this deposit (optional)
    * @param minTimestamp Minimum timestamp for this deposit (optional)
    * @param maxTimestamp Maximum timestamp for this deposit (optional)
    * @param tokenType Token type of the deposited asset (optional)
    * @param tokenId ERC721 Token ID of the minted asset (optional)
    * @param assetId Internal IMX ID of the minted asset (optional)
    * @param tokenAddress Token address of the deposited asset (optional)
    * @param tokenName Token name of the deposited asset (optional)
    * @param minQuantity Min quantity for the deposited asset (optional)
    * @param maxQuantity Max quantity for the deposited asset (optional)
    * @param metadata JSON-encoded metadata filters for the deposited asset (optional)
    * @return RequestConfig
    */
    fun listDepositsRequestConfig(pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?, user: kotlin.String?, status: kotlin.String?, minTimestamp: kotlin.String?, maxTimestamp: kotlin.String?, tokenType: kotlin.String?, tokenId: kotlin.String?, assetId: kotlin.String?, tokenAddress: kotlin.String?, tokenName: kotlin.String?, minQuantity: kotlin.String?, maxQuantity: kotlin.String?, metadata: kotlin.String?) : RequestConfig<Unit> {
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
                if (minTimestamp != null) {
                    put("min_timestamp", listOf(minTimestamp.toString()))
                }
                if (maxTimestamp != null) {
                    put("max_timestamp", listOf(maxTimestamp.toString()))
                }
                if (tokenType != null) {
                    put("token_type", listOf(tokenType.toString()))
                }
                if (tokenId != null) {
                    put("token_id", listOf(tokenId.toString()))
                }
                if (assetId != null) {
                    put("asset_id", listOf(assetId.toString()))
                }
                if (tokenAddress != null) {
                    put("token_address", listOf(tokenAddress.toString()))
                }
                if (tokenName != null) {
                    put("token_name", listOf(tokenName.toString()))
                }
                if (minQuantity != null) {
                    put("min_quantity", listOf(minQuantity.toString()))
                }
                if (maxQuantity != null) {
                    put("max_quantity", listOf(maxQuantity.toString()))
                }
                if (metadata != null) {
                    put("metadata", listOf(metadata.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/deposits",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
