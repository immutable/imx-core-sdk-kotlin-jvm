# TlvsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getTLVs**](TlvsApi.md#getTLVs) | **GET** /v1/claims/{etherKey}/{tokenAddress} | Get TLV information for a user for a token


<a name="getTLVs"></a>
# **getTLVs**
> GetTLVsResponse getTLVs(etherKey, tokenAddress)

Get TLV information for a user for a token

Get TLV information for a user for a token

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TlvsApi()
val etherKey : kotlin.String = etherKey_example // kotlin.String | User's wallet address
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Token address
try {
    val result : GetTLVsResponse = apiInstance.getTLVs(etherKey, tokenAddress)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TlvsApi#getTLVs")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TlvsApi#getTLVs")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **etherKey** | **kotlin.String**| User&#39;s wallet address |
 **tokenAddress** | **kotlin.String**| Token address |

### Return type

[**GetTLVsResponse**](GetTLVsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

