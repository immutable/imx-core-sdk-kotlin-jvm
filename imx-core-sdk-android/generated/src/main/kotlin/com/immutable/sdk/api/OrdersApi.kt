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

import com.immutable.sdk.api.model.CancelOrderRequest
import com.immutable.sdk.api.model.CancelOrderResponse
import com.immutable.sdk.api.model.CreateOrderRequest
import com.immutable.sdk.api.model.CreateOrderResponse
import com.immutable.sdk.api.model.GetSignableCancelOrderRequest
import com.immutable.sdk.api.model.GetSignableCancelOrderResponse
import com.immutable.sdk.api.model.GetSignableOrderRequest
import com.immutable.sdk.api.model.GetSignableOrderRequestV1
import com.immutable.sdk.api.model.GetSignableOrderResponse
import com.immutable.sdk.api.model.GetSignableOrderResponseV1
import com.immutable.sdk.api.model.ListOrdersResponse
import com.immutable.sdk.api.model.Order

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

class OrdersApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.ropsten.x.immutable.com")
        }
    }

    /**
    * cancel an order
    * Cancel an order
    * @param id Order ID to cancel 
    * @param cancelOrderRequest cancel an order 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return CancelOrderResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun cancelOrder(id: kotlin.String, cancelOrderRequest: CancelOrderRequest, xImxEthAddress: kotlin.String? = null, xImxEthSignature: kotlin.String? = null) : CancelOrderResponse {
        val localVariableConfig = cancelOrderRequestConfig(id = id, cancelOrderRequest = cancelOrderRequest, xImxEthAddress = xImxEthAddress, xImxEthSignature = xImxEthSignature)

        val localVarResponse = request<CancelOrderRequest, CancelOrderResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CancelOrderResponse
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
    * To obtain the request config of the operation cancelOrder
    *
    * @param id Order ID to cancel 
    * @param cancelOrderRequest cancel an order 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return RequestConfig
    */
    fun cancelOrderRequestConfig(id: kotlin.String, cancelOrderRequest: CancelOrderRequest, xImxEthAddress: kotlin.String?, xImxEthSignature: kotlin.String?) : RequestConfig<CancelOrderRequest> {
        val localVariableBody = cancelOrderRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        xImxEthAddress?.apply { localVariableHeaders["x-imx-eth-address"] = this.toString() }
        xImxEthSignature?.apply { localVariableHeaders["x-imx-eth-signature"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.DELETE,
            path = "/v1/orders/{id}".replace("{"+"id"+"}", "$id"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Create an order
    * Create an order
    * @param createOrderRequest create an order 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return CreateOrderResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun createOrder(createOrderRequest: CreateOrderRequest, xImxEthAddress: kotlin.String? = null, xImxEthSignature: kotlin.String? = null) : CreateOrderResponse {
        val localVariableConfig = createOrderRequestConfig(createOrderRequest = createOrderRequest, xImxEthAddress = xImxEthAddress, xImxEthSignature = xImxEthSignature)

        val localVarResponse = request<CreateOrderRequest, CreateOrderResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CreateOrderResponse
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
    * To obtain the request config of the operation createOrder
    *
    * @param createOrderRequest create an order 
    * @param xImxEthAddress eth address (optional)
    * @param xImxEthSignature eth signature (optional)
    * @return RequestConfig
    */
    fun createOrderRequestConfig(createOrderRequest: CreateOrderRequest, xImxEthAddress: kotlin.String?, xImxEthSignature: kotlin.String?) : RequestConfig<CreateOrderRequest> {
        val localVariableBody = createOrderRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        xImxEthAddress?.apply { localVariableHeaders["x-imx-eth-address"] = this.toString() }
        xImxEthSignature?.apply { localVariableHeaders["x-imx-eth-signature"] = this.toString() }

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/orders",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get details of an order with the given ID
    * Get details of an order with the given ID
    * @param id Order ID 
    * @param includeFees Set flag to true to include fee body for the order (optional)
    * @param auxiliaryFeePercentages Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients (optional)
    * @param auxiliaryFeeRecipients Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages (optional)
    * @return Order
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getOrder(id: kotlin.String, includeFees: kotlin.Boolean? = null, auxiliaryFeePercentages: kotlin.String? = null, auxiliaryFeeRecipients: kotlin.String? = null) : Order {
        val localVariableConfig = getOrderRequestConfig(id = id, includeFees = includeFees, auxiliaryFeePercentages = auxiliaryFeePercentages, auxiliaryFeeRecipients = auxiliaryFeeRecipients)

        val localVarResponse = request<Unit, Order>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Order
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
    * To obtain the request config of the operation getOrder
    *
    * @param id Order ID 
    * @param includeFees Set flag to true to include fee body for the order (optional)
    * @param auxiliaryFeePercentages Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients (optional)
    * @param auxiliaryFeeRecipients Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages (optional)
    * @return RequestConfig
    */
    fun getOrderRequestConfig(id: kotlin.String, includeFees: kotlin.Boolean?, auxiliaryFeePercentages: kotlin.String?, auxiliaryFeeRecipients: kotlin.String?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (includeFees != null) {
                    put("include_fees", listOf(includeFees.toString()))
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
            path = "/v1/orders/{id}".replace("{"+"id"+"}", "$id"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get details a signable cancel order
    * Get details a signable cancel order
    * @param getSignableCancelOrderRequest get a signable cancel order 
    * @return GetSignableCancelOrderResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getSignableCancelOrder(getSignableCancelOrderRequest: GetSignableCancelOrderRequest) : GetSignableCancelOrderResponse {
        val localVariableConfig = getSignableCancelOrderRequestConfig(getSignableCancelOrderRequest = getSignableCancelOrderRequest)

        val localVarResponse = request<GetSignableCancelOrderRequest, GetSignableCancelOrderResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetSignableCancelOrderResponse
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
    * To obtain the request config of the operation getSignableCancelOrder
    *
    * @param getSignableCancelOrderRequest get a signable cancel order 
    * @return RequestConfig
    */
    fun getSignableCancelOrderRequestConfig(getSignableCancelOrderRequest: GetSignableCancelOrderRequest) : RequestConfig<GetSignableCancelOrderRequest> {
        val localVariableBody = getSignableCancelOrderRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/signable-cancel-order-details",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get details a signable order V3
    * Get details a signable order V3
    * @param getSignableOrderRequestV3 get a signable order 
    * @return GetSignableOrderResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getSignableOrder(getSignableOrderRequestV3: GetSignableOrderRequest) : GetSignableOrderResponse {
        val localVariableConfig = getSignableOrderRequestConfig(getSignableOrderRequestV3 = getSignableOrderRequestV3)

        val localVarResponse = request<GetSignableOrderRequest, GetSignableOrderResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetSignableOrderResponse
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
    * To obtain the request config of the operation getSignableOrder
    *
    * @param getSignableOrderRequestV3 get a signable order 
    * @return RequestConfig
    */
    fun getSignableOrderRequestConfig(getSignableOrderRequestV3: GetSignableOrderRequest) : RequestConfig<GetSignableOrderRequest> {
        val localVariableBody = getSignableOrderRequestV3
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v3/signable-order-details",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get details a signable order
    * Get details a signable order
    * @param getSignableOrderRequest get a signable order 
    * @return GetSignableOrderResponseV1
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getSignableOrderV1(getSignableOrderRequest: GetSignableOrderRequestV1) : GetSignableOrderResponseV1 {
        val localVariableConfig = getSignableOrderV1RequestConfig(getSignableOrderRequest = getSignableOrderRequest)

        val localVarResponse = request<GetSignableOrderRequestV1, GetSignableOrderResponseV1>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GetSignableOrderResponseV1
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
    * To obtain the request config of the operation getSignableOrderV1
    *
    * @param getSignableOrderRequest get a signable order 
    * @return RequestConfig
    */
    fun getSignableOrderV1RequestConfig(getSignableOrderRequest: GetSignableOrderRequestV1) : RequestConfig<GetSignableOrderRequestV1> {
        val localVariableBody = getSignableOrderRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v1/signable-order-details",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get a list of orders
    * Get a list of orders
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this order (optional)
    * @param status Status of this order (optional)
    * @param minTimestamp Minimum created at timestamp for this order (optional)
    * @param maxTimestamp Maximum created at timestamp for this order (optional)
    * @param updatedMinTimestamp Minimum updated at timestamp for this order (optional)
    * @param updatedMaxTimestamp Maximum updated at timestamp for this order (optional)
    * @param buyTokenType Token type of the asset this order buys (optional)
    * @param buyTokenId ERC721 Token ID of the asset this order buys (optional)
    * @param buyAssetId Internal IMX ID of the asset this order buys (optional)
    * @param buyTokenAddress Comma separated string of token addresses of the asset this order buys (optional)
    * @param buyTokenName Token name of the asset this order buys (optional)
    * @param buyMinQuantity Min quantity for the asset this order buys (optional)
    * @param buyMaxQuantity Max quantity for the asset this order buys (optional)
    * @param buyMetadata JSON-encoded metadata filters for the asset this order buys (optional)
    * @param sellTokenType Token type of the asset this order sells (optional)
    * @param sellTokenId ERC721 Token ID of the asset this order sells (optional)
    * @param sellAssetId Internal IMX ID of the asset this order sells (optional)
    * @param sellTokenAddress Comma separated string of token addresses of the asset this order sells (optional)
    * @param sellTokenName Token name of the asset this order sells (optional)
    * @param sellMinQuantity Min quantity for the asset this order sells (optional)
    * @param sellMaxQuantity Max quantity for the asset this order sells (optional)
    * @param sellMetadata JSON-encoded metadata filters for the asset this order sells (optional)
    * @param auxiliaryFeePercentages Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients (optional)
    * @param auxiliaryFeeRecipients Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages (optional)
    * @return ListOrdersResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun listOrders(pageSize: kotlin.Int? = null, cursor: kotlin.String? = null, orderBy: kotlin.String? = null, direction: kotlin.String? = null, user: kotlin.String? = null, status: kotlin.String? = null, minTimestamp: kotlin.String? = null, maxTimestamp: kotlin.String? = null, updatedMinTimestamp: kotlin.String? = null, updatedMaxTimestamp: kotlin.String? = null, buyTokenType: kotlin.String? = null, buyTokenId: kotlin.String? = null, buyAssetId: kotlin.String? = null, buyTokenAddress: kotlin.String? = null, buyTokenName: kotlin.String? = null, buyMinQuantity: kotlin.String? = null, buyMaxQuantity: kotlin.String? = null, buyMetadata: kotlin.String? = null, sellTokenType: kotlin.String? = null, sellTokenId: kotlin.String? = null, sellAssetId: kotlin.String? = null, sellTokenAddress: kotlin.String? = null, sellTokenName: kotlin.String? = null, sellMinQuantity: kotlin.String? = null, sellMaxQuantity: kotlin.String? = null, sellMetadata: kotlin.String? = null, auxiliaryFeePercentages: kotlin.String? = null, auxiliaryFeeRecipients: kotlin.String? = null) : ListOrdersResponse {
        val localVariableConfig = listOrdersRequestConfig(pageSize = pageSize, cursor = cursor, orderBy = orderBy, direction = direction, user = user, status = status, minTimestamp = minTimestamp, maxTimestamp = maxTimestamp, updatedMinTimestamp = updatedMinTimestamp, updatedMaxTimestamp = updatedMaxTimestamp, buyTokenType = buyTokenType, buyTokenId = buyTokenId, buyAssetId = buyAssetId, buyTokenAddress = buyTokenAddress, buyTokenName = buyTokenName, buyMinQuantity = buyMinQuantity, buyMaxQuantity = buyMaxQuantity, buyMetadata = buyMetadata, sellTokenType = sellTokenType, sellTokenId = sellTokenId, sellAssetId = sellAssetId, sellTokenAddress = sellTokenAddress, sellTokenName = sellTokenName, sellMinQuantity = sellMinQuantity, sellMaxQuantity = sellMaxQuantity, sellMetadata = sellMetadata, auxiliaryFeePercentages = auxiliaryFeePercentages, auxiliaryFeeRecipients = auxiliaryFeeRecipients)

        val localVarResponse = request<Unit, ListOrdersResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ListOrdersResponse
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
    * To obtain the request config of the operation listOrders
    *
    * @param pageSize Page size of the result (optional)
    * @param cursor Cursor (optional)
    * @param orderBy Property to sort by (optional)
    * @param direction Direction to sort (asc/desc) (optional)
    * @param user Ethereum address of the user who submitted this order (optional)
    * @param status Status of this order (optional)
    * @param minTimestamp Minimum created at timestamp for this order (optional)
    * @param maxTimestamp Maximum created at timestamp for this order (optional)
    * @param updatedMinTimestamp Minimum updated at timestamp for this order (optional)
    * @param updatedMaxTimestamp Maximum updated at timestamp for this order (optional)
    * @param buyTokenType Token type of the asset this order buys (optional)
    * @param buyTokenId ERC721 Token ID of the asset this order buys (optional)
    * @param buyAssetId Internal IMX ID of the asset this order buys (optional)
    * @param buyTokenAddress Comma separated string of token addresses of the asset this order buys (optional)
    * @param buyTokenName Token name of the asset this order buys (optional)
    * @param buyMinQuantity Min quantity for the asset this order buys (optional)
    * @param buyMaxQuantity Max quantity for the asset this order buys (optional)
    * @param buyMetadata JSON-encoded metadata filters for the asset this order buys (optional)
    * @param sellTokenType Token type of the asset this order sells (optional)
    * @param sellTokenId ERC721 Token ID of the asset this order sells (optional)
    * @param sellAssetId Internal IMX ID of the asset this order sells (optional)
    * @param sellTokenAddress Comma separated string of token addresses of the asset this order sells (optional)
    * @param sellTokenName Token name of the asset this order sells (optional)
    * @param sellMinQuantity Min quantity for the asset this order sells (optional)
    * @param sellMaxQuantity Max quantity for the asset this order sells (optional)
    * @param sellMetadata JSON-encoded metadata filters for the asset this order sells (optional)
    * @param auxiliaryFeePercentages Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients (optional)
    * @param auxiliaryFeeRecipients Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages (optional)
    * @return RequestConfig
    */
    fun listOrdersRequestConfig(pageSize: kotlin.Int?, cursor: kotlin.String?, orderBy: kotlin.String?, direction: kotlin.String?, user: kotlin.String?, status: kotlin.String?, minTimestamp: kotlin.String?, maxTimestamp: kotlin.String?, updatedMinTimestamp: kotlin.String?, updatedMaxTimestamp: kotlin.String?, buyTokenType: kotlin.String?, buyTokenId: kotlin.String?, buyAssetId: kotlin.String?, buyTokenAddress: kotlin.String?, buyTokenName: kotlin.String?, buyMinQuantity: kotlin.String?, buyMaxQuantity: kotlin.String?, buyMetadata: kotlin.String?, sellTokenType: kotlin.String?, sellTokenId: kotlin.String?, sellAssetId: kotlin.String?, sellTokenAddress: kotlin.String?, sellTokenName: kotlin.String?, sellMinQuantity: kotlin.String?, sellMaxQuantity: kotlin.String?, sellMetadata: kotlin.String?, auxiliaryFeePercentages: kotlin.String?, auxiliaryFeeRecipients: kotlin.String?) : RequestConfig<Unit> {
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
                if (updatedMinTimestamp != null) {
                    put("updated_min_timestamp", listOf(updatedMinTimestamp.toString()))
                }
                if (updatedMaxTimestamp != null) {
                    put("updated_max_timestamp", listOf(updatedMaxTimestamp.toString()))
                }
                if (buyTokenType != null) {
                    put("buy_token_type", listOf(buyTokenType.toString()))
                }
                if (buyTokenId != null) {
                    put("buy_token_id", listOf(buyTokenId.toString()))
                }
                if (buyAssetId != null) {
                    put("buy_asset_id", listOf(buyAssetId.toString()))
                }
                if (buyTokenAddress != null) {
                    put("buy_token_address", listOf(buyTokenAddress.toString()))
                }
                if (buyTokenName != null) {
                    put("buy_token_name", listOf(buyTokenName.toString()))
                }
                if (buyMinQuantity != null) {
                    put("buy_min_quantity", listOf(buyMinQuantity.toString()))
                }
                if (buyMaxQuantity != null) {
                    put("buy_max_quantity", listOf(buyMaxQuantity.toString()))
                }
                if (buyMetadata != null) {
                    put("buy_metadata", listOf(buyMetadata.toString()))
                }
                if (sellTokenType != null) {
                    put("sell_token_type", listOf(sellTokenType.toString()))
                }
                if (sellTokenId != null) {
                    put("sell_token_id", listOf(sellTokenId.toString()))
                }
                if (sellAssetId != null) {
                    put("sell_asset_id", listOf(sellAssetId.toString()))
                }
                if (sellTokenAddress != null) {
                    put("sell_token_address", listOf(sellTokenAddress.toString()))
                }
                if (sellTokenName != null) {
                    put("sell_token_name", listOf(sellTokenName.toString()))
                }
                if (sellMinQuantity != null) {
                    put("sell_min_quantity", listOf(sellMinQuantity.toString()))
                }
                if (sellMaxQuantity != null) {
                    put("sell_max_quantity", listOf(sellMaxQuantity.toString()))
                }
                if (sellMetadata != null) {
                    put("sell_metadata", listOf(sellMetadata.toString()))
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
            path = "/v1/orders",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
