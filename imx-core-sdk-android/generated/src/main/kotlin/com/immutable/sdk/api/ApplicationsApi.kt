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

import com.immutable.sdk.api.model.Application
import com.immutable.sdk.api.model.ListApplicationsResponse

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

class ApplicationsApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Get details of an application with the given ID
    * Get details of an application with the given ID
    * @param id Application ID 
    * @return Application
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getApplication(id: kotlin.String) : Application {
        val localVariableConfig = getApplicationRequestConfig(id = id)

        val localVarResponse = request<Unit, Application>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Application
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
    * To obtain the request config of the operation getApplication
    *
    * @param id Application ID 
    * @return RequestConfig
    */
    fun getApplicationRequestConfig(id: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/applications/{id}".replace("{"+"id"+"}", "$id"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of applications
    * Get a list of applications
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @return ListApplicationsResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listApplications(pageSize: kotlin.Int? = null, cursor: kotlin.String? = null, orderBy: kotlin.String? = null, direction: kotlin.String? = null) : ListApplicationsResponse {
        val localVariableConfig = listApplicationsRequestConfig(pageSize = pageSize, cursor = cursor, orderBy = orderBy, direction = direction)

        val localVarResponse = request<Unit, ListApplicationsResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListApplicationsResponse
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
    * To obtain the request config of the operation listApplications
    *
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @return RequestConfig
    */
    fun listApplicationsRequestConfig(pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?) : RequestConfig<Unit> {
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
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/applications",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
