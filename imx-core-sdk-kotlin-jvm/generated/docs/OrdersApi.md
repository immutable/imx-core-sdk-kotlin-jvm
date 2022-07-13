# OrdersApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**cancelOrder**](OrdersApi.md#cancelOrder) | **DELETE** /v1/orders/{id} | cancel an order
[**createOrder**](OrdersApi.md#createOrder) | **POST** /v1/orders | Create an order
[**getOrder**](OrdersApi.md#getOrder) | **GET** /v1/orders/{id} | Get details of an order with the given ID
[**getSignableCancelOrder**](OrdersApi.md#getSignableCancelOrder) | **POST** /v1/signable-cancel-order-details | Get details a signable cancel order
[**getSignableOrder**](OrdersApi.md#getSignableOrder) | **POST** /v3/signable-order-details | Get a signable order request (V3)
[**listOrders**](OrdersApi.md#listOrders) | **GET** /v1/orders | Get a list of orders


<a name="cancelOrder"></a>
# **cancelOrder**
> CancelOrderResponse cancelOrder(id, cancelOrderRequest, xImxEthAddress, xImxEthSignature)

cancel an order

Cancel an order

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = OrdersApi()
val id : kotlin.String = id_example // kotlin.String | Order ID to cancel
val cancelOrderRequest : CancelOrderRequest =  // CancelOrderRequest | cancel an order
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CancelOrderResponse = apiInstance.cancelOrder(id, cancelOrderRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrdersApi#cancelOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrdersApi#cancelOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Order ID to cancel |
 **cancelOrderRequest** | [**CancelOrderRequest**](CancelOrderRequest.md)| cancel an order |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CancelOrderResponse**](CancelOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createOrder"></a>
# **createOrder**
> CreateOrderResponse createOrder(createOrderRequest, xImxEthAddress, xImxEthSignature)

Create an order

Create an order

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = OrdersApi()
val createOrderRequest : CreateOrderRequest =  // CreateOrderRequest | create an order
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateOrderResponse = apiInstance.createOrder(createOrderRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrdersApi#createOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrdersApi#createOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createOrderRequest** | [**CreateOrderRequest**](CreateOrderRequest.md)| create an order |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateOrderResponse**](CreateOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getOrder"></a>
# **getOrder**
> Order getOrder(id, includeFees, auxiliaryFeePercentages, auxiliaryFeeRecipients)

Get details of an order with the given ID

Get details of an order with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = OrdersApi()
val id : kotlin.String = id_example // kotlin.String | Order ID
val includeFees : kotlin.Boolean = true // kotlin.Boolean | Set flag to true to include fee body for the order
val auxiliaryFeePercentages : kotlin.String = auxiliaryFeePercentages_example // kotlin.String | Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients
val auxiliaryFeeRecipients : kotlin.String = auxiliaryFeeRecipients_example // kotlin.String | Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages
try {
    val result : Order = apiInstance.getOrder(id, includeFees, auxiliaryFeePercentages, auxiliaryFeeRecipients)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrdersApi#getOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrdersApi#getOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Order ID |
 **includeFees** | **kotlin.Boolean**| Set flag to true to include fee body for the order | [optional]
 **auxiliaryFeePercentages** | **kotlin.String**| Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients | [optional]
 **auxiliaryFeeRecipients** | **kotlin.String**| Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages | [optional]

### Return type

[**Order**](Order.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSignableCancelOrder"></a>
# **getSignableCancelOrder**
> GetSignableCancelOrderResponse getSignableCancelOrder(getSignableCancelOrderRequest)

Get details a signable cancel order

Get details a signable cancel order

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = OrdersApi()
val getSignableCancelOrderRequest : GetSignableCancelOrderRequest =  // GetSignableCancelOrderRequest | get a signable cancel order
try {
    val result : GetSignableCancelOrderResponse = apiInstance.getSignableCancelOrder(getSignableCancelOrderRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrdersApi#getSignableCancelOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrdersApi#getSignableCancelOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableCancelOrderRequest** | [**GetSignableCancelOrderRequest**](GetSignableCancelOrderRequest.md)| get a signable cancel order |

### Return type

[**GetSignableCancelOrderResponse**](GetSignableCancelOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSignableOrder"></a>
# **getSignableOrder**
> GetSignableOrderResponse getSignableOrder(getSignableOrderRequestV3)

Get a signable order request (V3)

Get a signable order request (V3)

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = OrdersApi()
val getSignableOrderRequestV3 : GetSignableOrderRequest =  // GetSignableOrderRequest | get a signable order
try {
    val result : GetSignableOrderResponse = apiInstance.getSignableOrder(getSignableOrderRequestV3)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrdersApi#getSignableOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrdersApi#getSignableOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableOrderRequestV3** | [**GetSignableOrderRequest**](GetSignableOrderRequest.md)| get a signable order |

### Return type

[**GetSignableOrderResponse**](GetSignableOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listOrders"></a>
# **listOrders**
> ListOrdersResponse listOrders(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, updatedMinTimestamp, updatedMaxTimestamp, buyTokenType, buyTokenId, buyAssetId, buyTokenAddress, buyTokenName, buyMinQuantity, buyMaxQuantity, buyMetadata, sellTokenType, sellTokenId, sellAssetId, sellTokenAddress, sellTokenName, sellMinQuantity, sellMaxQuantity, sellMetadata, auxiliaryFeePercentages, auxiliaryFeeRecipients, includeFees)

Get a list of orders

Get a list of orders

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = OrdersApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this order
val status : kotlin.String = status_example // kotlin.String | Status of this order
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum created at timestamp for this order, in ISO 8601 UTC format. Example: '2022-05-27T00:10:22Z'
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum created at timestamp for this order, in ISO 8601 UTC format. Example: '2022-05-27T00:10:22Z'
val updatedMinTimestamp : kotlin.String = updatedMinTimestamp_example // kotlin.String | Minimum updated at timestamp for this order, in ISO 8601 UTC format. Example: '2022-05-27T00:10:22Z'
val updatedMaxTimestamp : kotlin.String = updatedMaxTimestamp_example // kotlin.String | Maximum updated at timestamp for this order, in ISO 8601 UTC format. Example: '2022-05-27T00:10:22Z'
val buyTokenType : kotlin.String = buyTokenType_example // kotlin.String | Token type of the asset this order buys
val buyTokenId : kotlin.String = buyTokenId_example // kotlin.String | ERC721 Token ID of the asset this order buys
val buyAssetId : kotlin.String = buyAssetId_example // kotlin.String | Internal IMX ID of the asset this order buys
val buyTokenAddress : kotlin.String = buyTokenAddress_example // kotlin.String | Comma separated string of token addresses of the asset this order buys
val buyTokenName : kotlin.String = buyTokenName_example // kotlin.String | Token name of the asset this order buys
val buyMinQuantity : kotlin.String = buyMinQuantity_example // kotlin.String | Min quantity for the asset this order buys
val buyMaxQuantity : kotlin.String = buyMaxQuantity_example // kotlin.String | Max quantity for the asset this order buys
val buyMetadata : kotlin.String = buyMetadata_example // kotlin.String | JSON-encoded metadata filters for the asset this order buys
val sellTokenType : kotlin.String = sellTokenType_example // kotlin.String | Token type of the asset this order sells
val sellTokenId : kotlin.String = sellTokenId_example // kotlin.String | ERC721 Token ID of the asset this order sells
val sellAssetId : kotlin.String = sellAssetId_example // kotlin.String | Internal IMX ID of the asset this order sells
val sellTokenAddress : kotlin.String = sellTokenAddress_example // kotlin.String | Comma separated string of token addresses of the asset this order sells
val sellTokenName : kotlin.String = sellTokenName_example // kotlin.String | Token name of the asset this order sells
val sellMinQuantity : kotlin.String = sellMinQuantity_example // kotlin.String | Min quantity for the asset this order sells
val sellMaxQuantity : kotlin.String = sellMaxQuantity_example // kotlin.String | Max quantity for the asset this order sells
val sellMetadata : kotlin.String = sellMetadata_example // kotlin.String | JSON-encoded metadata filters for the asset this order sells
val auxiliaryFeePercentages : kotlin.String = auxiliaryFeePercentages_example // kotlin.String | Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients
val auxiliaryFeeRecipients : kotlin.String = auxiliaryFeeRecipients_example // kotlin.String | Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages
val includeFees : kotlin.Boolean = true // kotlin.Boolean | Set flag to true to include fee object for orders
try {
    val result : ListOrdersResponse = apiInstance.listOrders(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, updatedMinTimestamp, updatedMaxTimestamp, buyTokenType, buyTokenId, buyAssetId, buyTokenAddress, buyTokenName, buyMinQuantity, buyMaxQuantity, buyMetadata, sellTokenType, sellTokenId, sellAssetId, sellTokenAddress, sellTokenName, sellMinQuantity, sellMaxQuantity, sellMetadata, auxiliaryFeePercentages, auxiliaryFeeRecipients, includeFees)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrdersApi#listOrders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrdersApi#listOrders")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional] [enum: created_at, expired_at, sell_quantity, buy_quantity, buy_quantity_with_fees, updated_at]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **user** | **kotlin.String**| Ethereum address of the user who submitted this order | [optional]
 **status** | **kotlin.String**| Status of this order | [optional] [enum: active, filled, cancelled, expired, inactive]
 **minTimestamp** | **kotlin.String**| Minimum created at timestamp for this order, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum created at timestamp for this order, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; | [optional]
 **updatedMinTimestamp** | **kotlin.String**| Minimum updated at timestamp for this order, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; | [optional]
 **updatedMaxTimestamp** | **kotlin.String**| Maximum updated at timestamp for this order, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; | [optional]
 **buyTokenType** | **kotlin.String**| Token type of the asset this order buys | [optional]
 **buyTokenId** | **kotlin.String**| ERC721 Token ID of the asset this order buys | [optional]
 **buyAssetId** | **kotlin.String**| Internal IMX ID of the asset this order buys | [optional]
 **buyTokenAddress** | **kotlin.String**| Comma separated string of token addresses of the asset this order buys | [optional]
 **buyTokenName** | **kotlin.String**| Token name of the asset this order buys | [optional]
 **buyMinQuantity** | **kotlin.String**| Min quantity for the asset this order buys | [optional]
 **buyMaxQuantity** | **kotlin.String**| Max quantity for the asset this order buys | [optional]
 **buyMetadata** | **kotlin.String**| JSON-encoded metadata filters for the asset this order buys | [optional]
 **sellTokenType** | **kotlin.String**| Token type of the asset this order sells | [optional]
 **sellTokenId** | **kotlin.String**| ERC721 Token ID of the asset this order sells | [optional]
 **sellAssetId** | **kotlin.String**| Internal IMX ID of the asset this order sells | [optional]
 **sellTokenAddress** | **kotlin.String**| Comma separated string of token addresses of the asset this order sells | [optional]
 **sellTokenName** | **kotlin.String**| Token name of the asset this order sells | [optional]
 **sellMinQuantity** | **kotlin.String**| Min quantity for the asset this order sells | [optional]
 **sellMaxQuantity** | **kotlin.String**| Max quantity for the asset this order sells | [optional]
 **sellMetadata** | **kotlin.String**| JSON-encoded metadata filters for the asset this order sells | [optional]
 **auxiliaryFeePercentages** | **kotlin.String**| Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients | [optional]
 **auxiliaryFeeRecipients** | **kotlin.String**| Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages | [optional]
 **includeFees** | **kotlin.Boolean**| Set flag to true to include fee object for orders | [optional]

### Return type

[**ListOrdersResponse**](ListOrdersResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

