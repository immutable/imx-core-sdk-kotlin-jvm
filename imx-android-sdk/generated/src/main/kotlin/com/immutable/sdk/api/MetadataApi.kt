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

import com.immutable.sdk.api.model.AddMetadataSchemaToCollectionRequest
import com.immutable.sdk.api.model.MetadataSchemaProperty
import com.immutable.sdk.api.model.MetadataSchemaRequest
import com.immutable.sdk.api.model.SuccessResponse

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

class MetadataApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "http://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Add metadata schema to collection
    * Add metadata schema to collection
    * @param address Collection contract address 
    * @param imXSignature String created by signing wallet address and timestamp 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param addMetadataSchemaToCollectionRequest add metadata schema to a collection 
    * @return SuccessResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun addMetadataSchemaToCollection(address: kotlin.String, imXSignature: kotlin.String, imXTimestamp: kotlin.String, addMetadataSchemaToCollectionRequest: AddMetadataSchemaToCollectionRequest) : SuccessResponse {
        val localVariableConfig = addMetadataSchemaToCollectionRequestConfig(address = address, imXSignature = imXSignature, imXTimestamp = imXTimestamp, addMetadataSchemaToCollectionRequest = addMetadataSchemaToCollectionRequest)

        val localVarResponse = request<AddMetadataSchemaToCollectionRequest, SuccessResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as SuccessResponse
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
    * To obtain the request config of the operation addMetadataSchemaToCollection
    *
    * @param address Collection contract address 
    * @param imXSignature String created by signing wallet address and timestamp 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param addMetadataSchemaToCollectionRequest add metadata schema to a collection 
    * @return RequestConfig
    */
    fun addMetadataSchemaToCollectionRequestConfig(address: kotlin.String, imXSignature: kotlin.String, imXTimestamp: kotlin.String, addMetadataSchemaToCollectionRequest: AddMetadataSchemaToCollectionRequest) : RequestConfig<AddMetadataSchemaToCollectionRequest> {
        val localVariableBody = addMetadataSchemaToCollectionRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        imXSignature.apply { localVariableHeaders["IMX-Signature"] = this.toString() }
        imXTimestamp.apply { localVariableHeaders["IMX-Timestamp"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/collections/{address}/metadata-schema".replace("{"+"address"+"}", "$address"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get collection metadata schema
    * Get collection metadata schema
    * @param address Collection contract address 
    * @return kotlin.collections.List<MetadataSchemaProperty>
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getMetadataSchema(address: kotlin.String) : kotlin.collections.List<MetadataSchemaProperty> {
        val localVariableConfig = getMetadataSchemaRequestConfig(address = address)

        val localVarResponse = request<Unit, kotlin.collections.List<MetadataSchemaProperty>>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.List<MetadataSchemaProperty>
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
    * To obtain the request config of the operation getMetadataSchema
    *
    * @param address Collection contract address 
    * @return RequestConfig
    */
    fun getMetadataSchemaRequestConfig(address: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/collections/{address}/metadata-schema".replace("{"+"address"+"}", "$address"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Update metadata schema by name
    * Update metadata schema by name
    * @param address Collection contract address 
    * @param name Metadata schema name 
    * @param imXSignature String created by signing wallet address and timestamp 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param metadataSchemaRequest update metadata schema 
    * @return SuccessResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun updateMetadataSchemaByName(address: kotlin.String, name: kotlin.String, imXSignature: kotlin.String, imXTimestamp: kotlin.String, metadataSchemaRequest: MetadataSchemaRequest) : SuccessResponse {
        val localVariableConfig = updateMetadataSchemaByNameRequestConfig(address = address, name = name, imXSignature = imXSignature, imXTimestamp = imXTimestamp, metadataSchemaRequest = metadataSchemaRequest)

        val localVarResponse = request<MetadataSchemaRequest, SuccessResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as SuccessResponse
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
    * To obtain the request config of the operation updateMetadataSchemaByName
    *
    * @param address Collection contract address 
    * @param name Metadata schema name 
    * @param imXSignature String created by signing wallet address and timestamp 
    * @param imXTimestamp Unix Epoc timestamp 
    * @param metadataSchemaRequest update metadata schema 
    * @return RequestConfig
    */
    fun updateMetadataSchemaByNameRequestConfig(address: kotlin.String, name: kotlin.String, imXSignature: kotlin.String, imXTimestamp: kotlin.String, metadataSchemaRequest: MetadataSchemaRequest) : RequestConfig<MetadataSchemaRequest> {
        val localVariableBody = metadataSchemaRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        imXSignature.apply { localVariableHeaders["IMX-Signature"] = this.toString() }
        imXTimestamp.apply { localVariableHeaders["IMX-Timestamp"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.PATCH,
            path = "/v1/collections/{address}/metadata-schema/{name}".replace("{"+"address"+"}", "$address").replace("{"+"name"+"}", "$name"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
