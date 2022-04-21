# TokensApi

All URIs are relative to *http://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getToken**](TokensApi.md#getToken) | **GET** /v1/tokens/{address} | Get details of a token
[**listTokens**](TokensApi.md#listTokens) | **GET** /v1/tokens | Get a list of tokens


<a name="getToken"></a>
# **getToken**
> TokenDetails getToken(address)

Get details of a token

Get details of a token

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TokensApi()
val address : kotlin.String = address_example // kotlin.String | Token Contract Address
try {
    val result : TokenDetails = apiInstance.getToken(address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TokensApi#getToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TokensApi#getToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Token Contract Address |

### Return type

[**TokenDetails**](TokenDetails.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listTokens"></a>
# **listTokens**
> ListTokensResponse listTokens(address, symbols)

Get a list of tokens

Get a list of tokens

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TokensApi()
val address : kotlin.String = address_example // kotlin.String | Contract address of the token
val symbols : kotlin.String = symbols_example // kotlin.String | Token symbols for the token, e.g. ?symbols=IMX,ETH
try {
    val result : ListTokensResponse = apiInstance.listTokens(address, symbols)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TokensApi#listTokens")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TokensApi#listTokens")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Contract address of the token | [optional]
 **symbols** | **kotlin.String**| Token symbols for the token, e.g. ?symbols&#x3D;IMX,ETH | [optional]

### Return type

[**ListTokensResponse**](ListTokensResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

