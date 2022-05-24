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

import com.immutable.sdk.api.model.CreateTransferRequest
import com.immutable.sdk.api.model.CreateTransferRequestV1
import com.immutable.sdk.api.model.CreateTransferResponse
import com.immutable.sdk.api.model.CreateTransferResponseV1
import com.immutable.sdk.api.model.GetSignableTransferRequest
import com.immutable.sdk.api.model.GetSignableTransferRequestV1
import com.immutable.sdk.api.model.GetSignableTransferResponse
import com.immutable.sdk.api.model.GetSignableTransferResponseV1
import com.immutable.sdk.api.model.ListTransfersResponse
import com.immutable.sdk.api.model.Transfer

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

class TransfersApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "http://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Creates a transfer of multiple tokens between two parties
    * Create a new transfer request
    * @param createTransferRequestV2 Create transfer 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return CreateTransferResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun createTransfer(createTransferRequestV2: CreateTransferRequest, xImxEthAddress: kotlin.String? = null, xImxEthSignature: kotlin.String? = null) : CreateTransferResponse {
        val localVariableConfig = createTransferRequestConfig(createTransferRequestV2 = createTransferRequestV2, xImxEthAddress = xImxEthAddress, xImxEthSignature = xImxEthSignature)

        val localVarResponse = request<CreateTransferRequest, CreateTransferResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CreateTransferResponse
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
    * To obtain the request config of the operation createTransfer
    *
    * @param createTransferRequestV2 Create transfer 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return RequestConfig
    */
    fun createTransferRequestConfig(createTransferRequestV2: CreateTransferRequest, xImxEthAddress: kotlin.String?, xImxEthSignature: kotlin.String?) : RequestConfig<CreateTransferRequest> {
        val localVariableBody = createTransferRequestV2
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        xImxEthAddress?.apply { localVariableHeaders["x-imx-eth-address"] = this.toString() }
        xImxEthSignature?.apply { localVariableHeaders["x-imx-eth-signature"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v2/transfers",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Creates a transfer of tokens between two parties
    * Create a new transfer request
    * @param createTransferRequest Create transfer 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return CreateTransferResponseV1
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun createTransferV1(createTransferRequest: CreateTransferRequestV1, xImxEthAddress: kotlin.String? = null, xImxEthSignature: kotlin.String? = null) : CreateTransferResponseV1 {
        val localVariableConfig = createTransferV1RequestConfig(createTransferRequest = createTransferRequest, xImxEthAddress = xImxEthAddress, xImxEthSignature = xImxEthSignature)

        val localVarResponse = request<CreateTransferRequestV1, CreateTransferResponseV1>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CreateTransferResponseV1
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
    * To obtain the request config of the operation createTransferV1
    *
    * @param createTransferRequest Create transfer 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return RequestConfig
    */
    fun createTransferV1RequestConfig(createTransferRequest: CreateTransferRequestV1, xImxEthAddress: kotlin.String?, xImxEthSignature: kotlin.String?) : RequestConfig<CreateTransferRequestV1> {
        val localVariableBody = createTransferRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        xImxEthAddress?.apply { localVariableHeaders["x-imx-eth-address"] = this.toString() }
        xImxEthSignature?.apply { localVariableHeaders["x-imx-eth-signature"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/transfers",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Gets bulk details of a signable transfer
    * Gets bulk details of a signable transfer
    * @param getSignableTransferRequestV2 get details of signable transfer 
    * @return GetSignableTransferResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getSignableTransfer(getSignableTransferRequestV2: GetSignableTransferRequest) : GetSignableTransferResponse {
        val localVariableConfig = getSignableTransferRequestConfig(getSignableTransferRequestV2 = getSignableTransferRequestV2)

        val localVarResponse = request<GetSignableTransferRequest, GetSignableTransferResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetSignableTransferResponse
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
    * To obtain the request config of the operation getSignableTransfer
    *
    * @param getSignableTransferRequestV2 get details of signable transfer 
    * @return RequestConfig
    */
    fun getSignableTransferRequestConfig(getSignableTransferRequestV2: GetSignableTransferRequest) : RequestConfig<GetSignableTransferRequest> {
        val localVariableBody = getSignableTransferRequestV2
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v2/signable-transfer-details",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Gets details of a signable transfer
    * Gets details of a signable transfer
    * @param getSignableTransferRequest get details of signable transfer 
    * @return GetSignableTransferResponseV1
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getSignableTransferV1(getSignableTransferRequest: GetSignableTransferRequestV1) : GetSignableTransferResponseV1 {
        val localVariableConfig = getSignableTransferV1RequestConfig(getSignableTransferRequest = getSignableTransferRequest)

        val localVarResponse = request<GetSignableTransferRequestV1, GetSignableTransferResponseV1>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetSignableTransferResponseV1
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
    * To obtain the request config of the operation getSignableTransferV1
    *
    * @param getSignableTransferRequest get details of signable transfer 
    * @return RequestConfig
    */
    fun getSignableTransferV1RequestConfig(getSignableTransferRequest: GetSignableTransferRequestV1) : RequestConfig<GetSignableTransferRequestV1> {
        val localVariableBody = getSignableTransferRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/signable-transfer-details",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get details of a transfer with the given ID
    * Get details of a transfer with the given ID
    * @param id Transfer ID 
    * @return Transfer
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getTransfer(id: kotlin.String) : Transfer {
        val localVariableConfig = getTransferRequestConfig(id = id)

        val localVarResponse = request<Unit, Transfer>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Transfer
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
    * To obtain the request config of the operation getTransfer
    *
    * @param id Transfer ID 
    * @return RequestConfig
    */
    fun getTransferRequestConfig(id: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/transfers/{id}".replace("{"+"id"+"}", "$id"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of transfers
    * Get a list of transfers
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this transfer (optional)
    * @param status Status of this transfer (optional)
    * @param minTimestamp Minimum timestamp for this transfer (optional)
    * @param maxTimestamp Maximum timestamp for this transfer (optional)
    * @param tokenType Token type of the transferred asset (optional)
    * @param tokenId ERC721 Token ID of the minted asset (optional)
    * @param assetId Internal IMX ID of the minted asset (optional)
    * @param tokenAddress Token address of the transferred asset (optional)
    * @param tokenName Token name of the transferred asset (optional)
    * @param minQuantity Max quantity for the transferred asset (optional)
    * @param maxQuantity Max quantity for the transferred asset (optional)
    * @param metadata JSON-encoded metadata filters for the transferred asset (optional)
    * @return ListTransfersResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listTransfers(pageSize: kotlin.Int? = null, cursor: kotlin.String? = null, orderBy: kotlin.String? = null, direction: kotlin.String? = null, user: kotlin.String? = null, status: kotlin.String? = null, minTimestamp: kotlin.String? = null, maxTimestamp: kotlin.String? = null, tokenType: kotlin.String? = null, tokenId: kotlin.String? = null, assetId: kotlin.String? = null, tokenAddress: kotlin.String? = null, tokenName: kotlin.String? = null, minQuantity: kotlin.String? = null, maxQuantity: kotlin.String? = null, metadata: kotlin.String? = null) : ListTransfersResponse {
        val localVariableConfig = listTransfersRequestConfig(pageSize = pageSize, cursor = cursor, orderBy = orderBy, direction = direction, user = user, status = status, minTimestamp = minTimestamp, maxTimestamp = maxTimestamp, tokenType = tokenType, tokenId = tokenId, assetId = assetId, tokenAddress = tokenAddress, tokenName = tokenName, minQuantity = minQuantity, maxQuantity = maxQuantity, metadata = metadata)

        val localVarResponse = request<Unit, ListTransfersResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListTransfersResponse
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
    * To obtain the request config of the operation listTransfers
    *
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this transfer (optional)
    * @param status Status of this transfer (optional)
    * @param minTimestamp Minimum timestamp for this transfer (optional)
    * @param maxTimestamp Maximum timestamp for this transfer (optional)
    * @param tokenType Token type of the transferred asset (optional)
    * @param tokenId ERC721 Token ID of the minted asset (optional)
    * @param assetId Internal IMX ID of the minted asset (optional)
    * @param tokenAddress Token address of the transferred asset (optional)
    * @param tokenName Token name of the transferred asset (optional)
    * @param minQuantity Max quantity for the transferred asset (optional)
    * @param maxQuantity Max quantity for the transferred asset (optional)
    * @param metadata JSON-encoded metadata filters for the transferred asset (optional)
    * @return RequestConfig
    */
    fun listTransfersRequestConfig(pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?, user: kotlin.String?, status: kotlin.String?, minTimestamp: kotlin.String?, maxTimestamp: kotlin.String?, tokenType: kotlin.String?, tokenId: kotlin.String?, assetId: kotlin.String?, tokenAddress: kotlin.String?, tokenName: kotlin.String?, minQuantity: kotlin.String?, maxQuantity: kotlin.String?, metadata: kotlin.String?) : RequestConfig<Unit> {
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
            path = "/v1/transfers",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
