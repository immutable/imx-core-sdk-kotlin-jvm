# EncodingApi

All URIs are relative to *http://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**encodeAsset**](EncodingApi.md#encodeAsset) | **POST** /v1/encode/{assetType} | Retrieves the Starkex Encoded format for a given asset


<a name="encodeAsset"></a>
# **encodeAsset**
> EncodeAssetResponse encodeAsset(assetType, encodeAssetRequest)

Retrieves the Starkex Encoded format for a given asset

Retrieves the Starkex Encoded format for a given asset so that it can be used as parameter for Starkex smart contracts

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = EncodingApi()
val assetType : kotlin.String = assetType_example // kotlin.String | Asset type to be encoded. (asset/mintable-asset)
val encodeAssetRequest : EncodeAssetRequest =  // EncodeAssetRequest | Encode Asset
try {
    val result : EncodeAssetResponse = apiInstance.encodeAsset(assetType, encodeAssetRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling EncodingApi#encodeAsset")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling EncodingApi#encodeAsset")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **assetType** | **kotlin.String**| Asset type to be encoded. (asset/mintable-asset) |
 **encodeAssetRequest** | [**EncodeAssetRequest**](EncodeAssetRequest.md)| Encode Asset |

### Return type

[**EncodeAssetResponse**](EncodeAssetResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

