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

import com.immutable.sdk.api.model.CreateWithdrawalRequest
import com.immutable.sdk.api.model.CreateWithdrawalResponse
import com.immutable.sdk.api.model.GetSignableWithdrawalRequest
import com.immutable.sdk.api.model.GetSignableWithdrawalResponse
import com.immutable.sdk.api.model.ListWithdrawalsResponse
import com.immutable.sdk.api.model.Withdrawal

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

class WithdrawalsApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "http://api.ropsten.x.immutable.com")
        }
    }

    /**
    * Creates a withdrawal of a token
    * Creates a withdrawal
    * @param createWithdrawalRequest create a withdrawal 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return CreateWithdrawalResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun createWithdrawal(createWithdrawalRequest: CreateWithdrawalRequest, xImxEthAddress: kotlin.String?, xImxEthSignature: kotlin.String?) : CreateWithdrawalResponse {
        val localVariableConfig = createWithdrawalRequestConfig(createWithdrawalRequest = createWithdrawalRequest, xImxEthAddress = xImxEthAddress, xImxEthSignature = xImxEthSignature)

        val localVarResponse = request<CreateWithdrawalRequest, CreateWithdrawalResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CreateWithdrawalResponse
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
    * To obtain the request config of the operation createWithdrawal
    *
    * @param createWithdrawalRequest create a withdrawal 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return RequestConfig
    */
    fun createWithdrawalRequestConfig(createWithdrawalRequest: CreateWithdrawalRequest, xImxEthAddress: kotlin.String?, xImxEthSignature: kotlin.String?) : RequestConfig<CreateWithdrawalRequest> {
        val localVariableBody = createWithdrawalRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        xImxEthAddress?.apply { localVariableHeaders["x-imx-eth-address"] = this.toString() }
        xImxEthSignature?.apply { localVariableHeaders["x-imx-eth-signature"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/withdrawals",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Gets details of a signable withdrawal
    * Gets details of a signable withdrawal
    * @param getSignableWithdrawalRequest get details of signable withdrawal 
    * @return GetSignableWithdrawalResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getSignableWithdrawal(getSignableWithdrawalRequest: GetSignableWithdrawalRequest) : GetSignableWithdrawalResponse {
        val localVariableConfig = getSignableWithdrawalRequestConfig(getSignableWithdrawalRequest = getSignableWithdrawalRequest)

        val localVarResponse = request<GetSignableWithdrawalRequest, GetSignableWithdrawalResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetSignableWithdrawalResponse
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
    * To obtain the request config of the operation getSignableWithdrawal
    *
    * @param getSignableWithdrawalRequest get details of signable withdrawal 
    * @return RequestConfig
    */
    fun getSignableWithdrawalRequestConfig(getSignableWithdrawalRequest: GetSignableWithdrawalRequest) : RequestConfig<GetSignableWithdrawalRequest> {
        val localVariableBody = getSignableWithdrawalRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/signable-withdrawal-details",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Gets details of withdrawal with the given ID
    * Gets details of withdrawal with the given ID
    * @param id Withdrawal ID 
    * @return Withdrawal
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getWithdrawal(id: kotlin.String) : Withdrawal {
        val localVariableConfig = getWithdrawalRequestConfig(id = id)

        val localVarResponse = request<Unit, Withdrawal>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Withdrawal
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
    * To obtain the request config of the operation getWithdrawal
    *
    * @param id Withdrawal ID 
    * @return RequestConfig
    */
    fun getWithdrawalRequestConfig(id: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/withdrawals/{id}".replace("{"+"id"+"}", "$id"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of withdrawals
    * Get a list of withdrawals
    * @param withdrawnToWallet Withdrawal has been transferred to user&#39;s Layer 1 wallet (optional)
    * @param rollupStatus Status of the on-chain batch confirmation for this withdrawal (optional)
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this withdrawal (optional)
    * @param status Status of this withdrawal (optional)
    * @param minTimestamp Minimum timestamp for this deposit (optional)
    * @param maxTimestamp Maximum timestamp for this deposit (optional)
    * @param tokenType Token type of the withdrawn asset (optional)
    * @param tokenId ERC721 Token ID of the minted asset (optional)
    * @param assetId Internal IMX ID of the minted asset (optional)
    * @param tokenAddress Token address of the withdrawn asset (optional)
    * @param tokenName Token name of the withdrawn asset (optional)
    * @param minQuantity Min quantity for the withdrawn asset (optional)
    * @param maxQuantity Max quantity for the withdrawn asset (optional)
    * @param metadata JSON-encoded metadata filters for the withdrawn asset (optional)
    * @return ListWithdrawalsResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listWithdrawals(withdrawnToWallet: kotlin.Boolean?, rollupStatus: kotlin.String?, pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?, user: kotlin.String?, status: kotlin.String?, minTimestamp: kotlin.String?, maxTimestamp: kotlin.String?, tokenType: kotlin.String?, tokenId: kotlin.String?, assetId: kotlin.String?, tokenAddress: kotlin.String?, tokenName: kotlin.String?, minQuantity: kotlin.String?, maxQuantity: kotlin.String?, metadata: kotlin.String?) : ListWithdrawalsResponse {
        val localVariableConfig = listWithdrawalsRequestConfig(withdrawnToWallet = withdrawnToWallet, rollupStatus = rollupStatus, pageSize = pageSize, cursor = cursor, orderBy = orderBy, direction = direction, user = user, status = status, minTimestamp = minTimestamp, maxTimestamp = maxTimestamp, tokenType = tokenType, tokenId = tokenId, assetId = assetId, tokenAddress = tokenAddress, tokenName = tokenName, minQuantity = minQuantity, maxQuantity = maxQuantity, metadata = metadata)

        val localVarResponse = request<Unit, ListWithdrawalsResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListWithdrawalsResponse
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
    * To obtain the request config of the operation listWithdrawals
    *
    * @param withdrawnToWallet Withdrawal has been transferred to user&#39;s Layer 1 wallet (optional)
    * @param rollupStatus Status of the on-chain batch confirmation for this withdrawal (optional)
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this withdrawal (optional)
    * @param status Status of this withdrawal (optional)
    * @param minTimestamp Minimum timestamp for this deposit (optional)
    * @param maxTimestamp Maximum timestamp for this deposit (optional)
    * @param tokenType Token type of the withdrawn asset (optional)
    * @param tokenId ERC721 Token ID of the minted asset (optional)
    * @param assetId Internal IMX ID of the minted asset (optional)
    * @param tokenAddress Token address of the withdrawn asset (optional)
    * @param tokenName Token name of the withdrawn asset (optional)
    * @param minQuantity Min quantity for the withdrawn asset (optional)
    * @param maxQuantity Max quantity for the withdrawn asset (optional)
    * @param metadata JSON-encoded metadata filters for the withdrawn asset (optional)
    * @return RequestConfig
    */
    fun listWithdrawalsRequestConfig(withdrawnToWallet: kotlin.Boolean?, rollupStatus: kotlin.String?, pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?, user: kotlin.String?, status: kotlin.String?, minTimestamp: kotlin.String?, maxTimestamp: kotlin.String?, tokenType: kotlin.String?, tokenId: kotlin.String?, assetId: kotlin.String?, tokenAddress: kotlin.String?, tokenName: kotlin.String?, minQuantity: kotlin.String?, maxQuantity: kotlin.String?, metadata: kotlin.String?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (withdrawnToWallet != null) {
                    put("withdrawn_to_wallet", listOf(withdrawnToWallet.toString()))
                }
                if (rollupStatus != null) {
                    put("rollup_status", listOf(rollupStatus.toString()))
                }
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
            path = "/v1/withdrawals",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
