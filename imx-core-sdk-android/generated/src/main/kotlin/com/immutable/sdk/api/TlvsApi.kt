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

import com.immutable.sdk.api.model.GetTLVsResponse

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

class TlvsApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Get TLV information for a user for a token
    * Get TLV information for a user for a token
    * @param etherKey User&#39;s wallet address 
    * @param tokenAddress Token address 
    * @return GetTLVsResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getTLVs(etherKey: kotlin.String, tokenAddress: kotlin.String) : GetTLVsResponse {
        val localVariableConfig = getTLVsRequestConfig(etherKey = etherKey, tokenAddress = tokenAddress)

        val localVarResponse = request<Unit, GetTLVsResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetTLVsResponse
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
    * To obtain the request config of the operation getTLVs
    *
    * @param etherKey User&#39;s wallet address 
    * @param tokenAddress Token address 
    * @return RequestConfig
    */
    fun getTLVsRequestConfig(etherKey: kotlin.String, tokenAddress: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/claims/{etherKey}/{tokenAddress}".replace("{"+"etherKey"+"}", "$etherKey").replace("{"+"tokenAddress"+"}", "$tokenAddress"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
