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

import com.immutable.sdk.api.model.GetSnapshotRequest
import com.immutable.sdk.api.model.ListSnapshotBalancesResponse

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

class SnapshotApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Get a snapshot at a specific block
    * Get a list of snapshot balances
    * @param tokenAddress token address to list snapshot balances for 
    * @param getSnapshotRequest req 
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @return ListSnapshotBalancesResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listSnapshotBalances(tokenAddress: kotlin.String, getSnapshotRequest: GetSnapshotRequest, pageSize: kotlin.Int? = null, cursor: kotlin.String? = null) : ListSnapshotBalancesResponse {
        val localVariableConfig = listSnapshotBalancesRequestConfig(tokenAddress = tokenAddress, getSnapshotRequest = getSnapshotRequest, pageSize = pageSize, cursor = cursor)

        val localVarResponse = request<GetSnapshotRequest, ListSnapshotBalancesResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListSnapshotBalancesResponse
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
    * To obtain the request config of the operation listSnapshotBalances
    *
    * @param tokenAddress token address to list snapshot balances for 
    * @param getSnapshotRequest req 
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @return RequestConfig
    */
    fun listSnapshotBalancesRequestConfig(tokenAddress: kotlin.String, getSnapshotRequest: GetSnapshotRequest, pageSize: kotlin.Int?, cursor: kotlin.String?) : RequestConfig<GetSnapshotRequest> {
        val localVariableBody = getSnapshotRequest
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (pageSize != null) {
                    put("page_size", listOf(pageSize.toString()))
                }
                if (cursor != null) {
                    put("cursor", listOf(cursor.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/snapshot/balances/{tokenAddress}".replace("{"+"tokenAddress"+"}", "$tokenAddress"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
