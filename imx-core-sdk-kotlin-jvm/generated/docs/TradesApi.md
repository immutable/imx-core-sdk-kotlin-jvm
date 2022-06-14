# TradesApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createTrade**](TradesApi.md#createTrade) | **POST** /v1/trades | Create a Trade between two parties
[**getSignableTrade**](TradesApi.md#getSignableTrade) | **POST** /v3/signable-trade-details | Get details a signable trade V3
[**getTrade**](TradesApi.md#getTrade) | **GET** /v1/trades/{id} | Get details of a trade with the given ID
[**listTrades**](TradesApi.md#listTrades) | **GET** /v1/trades | Get a list of trades


<a name="createTrade"></a>
# **createTrade**
> CreateTradeResponse createTrade(createTradeRequest, xImxEthAddress, xImxEthSignature)

Create a Trade between two parties

Create a Trade

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TradesApi()
val createTradeRequest : CreateTradeRequestV1 =  // CreateTradeRequestV1 | create a trade
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateTradeResponse = apiInstance.createTrade(createTradeRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TradesApi#createTrade")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TradesApi#createTrade")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createTradeRequest** | [**CreateTradeRequestV1**](CreateTradeRequestV1.md)| create a trade |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateTradeResponse**](CreateTradeResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSignableTrade"></a>
# **getSignableTrade**
> GetSignableTradeResponse getSignableTrade(getSignableTradeRequest)

Get details a signable trade V3

Get details a signable trade V3

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TradesApi()
val getSignableTradeRequest : GetSignableTradeRequest =  // GetSignableTradeRequest | get a signable trade
try {
    val result : GetSignableTradeResponse = apiInstance.getSignableTrade(getSignableTradeRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TradesApi#getSignableTrade")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TradesApi#getSignableTrade")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableTradeRequest** | [**GetSignableTradeRequest**](GetSignableTradeRequest.md)| get a signable trade |

### Return type

[**GetSignableTradeResponse**](GetSignableTradeResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTrade"></a>
# **getTrade**
> Trade getTrade(id)

Get details of a trade with the given ID

Get details of a trade with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TradesApi()
val id : kotlin.String = id_example // kotlin.String | Trade ID
try {
    val result : Trade = apiInstance.getTrade(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TradesApi#getTrade")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TradesApi#getTrade")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Trade ID |

### Return type

[**Trade**](Trade.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listTrades"></a>
# **listTrades**
> ListTradesResponse listTrades(partyATokenType, partyATokenAddress, partyATokenId, partyBTokenType, partyBTokenAddress, partyBTokenId, pageSize, cursor, orderBy, direction, minTimestamp, maxTimestamp)

Get a list of trades

Get a list of trades

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TradesApi()
val partyATokenType : kotlin.String = partyATokenType_example // kotlin.String | Party A's sell token type
val partyATokenAddress : kotlin.String = partyATokenAddress_example // kotlin.String | Party A's sell token address
val partyATokenId : kotlin.String = partyATokenId_example // kotlin.String | Party A's sell token id
val partyBTokenType : kotlin.String = partyBTokenType_example // kotlin.String | Party B's sell token type
val partyBTokenAddress : kotlin.String = partyBTokenAddress_example // kotlin.String | Party B's sell token address
val partyBTokenId : kotlin.String = partyBTokenId_example // kotlin.String | Party B's sell token id
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum timestamp for this trade
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum timestamp for this trade
try {
    val result : ListTradesResponse = apiInstance.listTrades(partyATokenType, partyATokenAddress, partyATokenId, partyBTokenType, partyBTokenAddress, partyBTokenId, pageSize, cursor, orderBy, direction, minTimestamp, maxTimestamp)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TradesApi#listTrades")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TradesApi#listTrades")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **partyATokenType** | **kotlin.String**| Party A&#39;s sell token type | [optional]
 **partyATokenAddress** | **kotlin.String**| Party A&#39;s sell token address | [optional]
 **partyATokenId** | **kotlin.String**| Party A&#39;s sell token id | [optional]
 **partyBTokenType** | **kotlin.String**| Party B&#39;s sell token type | [optional]
 **partyBTokenAddress** | **kotlin.String**| Party B&#39;s sell token address | [optional]
 **partyBTokenId** | **kotlin.String**| Party B&#39;s sell token id | [optional]
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **minTimestamp** | **kotlin.String**| Minimum timestamp for this trade | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum timestamp for this trade | [optional]

### Return type

[**ListTradesResponse**](ListTradesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

