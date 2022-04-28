# SnapshotApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**listSnapshotBalances**](SnapshotApi.md#listSnapshotBalances) | **POST** /v1/snapshot/balances/{tokenAddress} | Get a snapshot at a specific block


<a name="listSnapshotBalances"></a>
# **listSnapshotBalances**
> ListSnapshotBalancesResponse listSnapshotBalances(tokenAddress, getSnapshotRequest, pageSize, cursor)

Get a snapshot at a specific block

Get a list of snapshot balances

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = SnapshotApi()
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | token address to list snapshot balances for
val getSnapshotRequest : GetSnapshotRequest =  // GetSnapshotRequest | req
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
try {
    val result : ListSnapshotBalancesResponse = apiInstance.listSnapshotBalances(tokenAddress, getSnapshotRequest, pageSize, cursor)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SnapshotApi#listSnapshotBalances")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SnapshotApi#listSnapshotBalances")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenAddress** | **kotlin.String**| token address to list snapshot balances for |
 **getSnapshotRequest** | [**GetSnapshotRequest**](GetSnapshotRequest.md)| req |
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]

### Return type

[**ListSnapshotBalancesResponse**](ListSnapshotBalancesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

