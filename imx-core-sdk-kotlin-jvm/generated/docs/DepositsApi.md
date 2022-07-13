# DepositsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDeposit**](DepositsApi.md#getDeposit) | **GET** /v1/deposits/{id} | Get details of a deposit with the given ID
[**getSignableDeposit**](DepositsApi.md#getSignableDeposit) | **POST** /v1/signable-deposit-details | Gets details of a signable deposit
[**listDeposits**](DepositsApi.md#listDeposits) | **GET** /v1/deposits | Get a list of deposits


<a name="getDeposit"></a>
# **getDeposit**
> Deposit getDeposit(id)

Get details of a deposit with the given ID

Get details of a deposit with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = DepositsApi()
val id : kotlin.String = id_example // kotlin.String | Deposit ID
try {
    val result : Deposit = apiInstance.getDeposit(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepositsApi#getDeposit")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepositsApi#getDeposit")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Deposit ID |

### Return type

[**Deposit**](Deposit.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSignableDeposit"></a>
# **getSignableDeposit**
> GetSignableDepositResponse getSignableDeposit(getSignableDepositRequest)

Gets details of a signable deposit

Gets details of a signable deposit

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = DepositsApi()
val getSignableDepositRequest : GetSignableDepositRequest =  // GetSignableDepositRequest | Get details of signable deposit
try {
    val result : GetSignableDepositResponse = apiInstance.getSignableDeposit(getSignableDepositRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepositsApi#getSignableDeposit")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepositsApi#getSignableDeposit")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableDepositRequest** | [**GetSignableDepositRequest**](GetSignableDepositRequest.md)| Get details of signable deposit |

### Return type

[**GetSignableDepositResponse**](GetSignableDepositResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listDeposits"></a>
# **listDeposits**
> ListDepositsResponse listDeposits(pageSize, cursor, orderBy, direction, user, status, updatedMinTimestamp, updatedMaxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)

Get a list of deposits

Get a list of deposits

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = DepositsApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this deposit
val status : kotlin.String = status_example // kotlin.String | Status of this deposit
val updatedMinTimestamp : kotlin.String = updatedMinTimestamp_example // kotlin.String | Minimum timestamp for this deposit, in ISO 8601 UTC format. Example: '2022-05-27T00:10:22Z'
val updatedMaxTimestamp : kotlin.String = updatedMaxTimestamp_example // kotlin.String | Maximum timestamp for this deposit, in ISO 8601 UTC format. Example: '2022-05-27T00:10:22Z'
val tokenType : kotlin.String = tokenType_example // kotlin.String | Token type of the deposited asset
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 Token ID of the minted asset
val assetId : kotlin.String = assetId_example // kotlin.String | Internal IMX ID of the minted asset
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Token address of the deposited asset
val tokenName : kotlin.String = tokenName_example // kotlin.String | Token name of the deposited asset
val minQuantity : kotlin.String = minQuantity_example // kotlin.String | Min quantity for the deposited asset
val maxQuantity : kotlin.String = maxQuantity_example // kotlin.String | Max quantity for the deposited asset
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for the deposited asset
try {
    val result : ListDepositsResponse = apiInstance.listDeposits(pageSize, cursor, orderBy, direction, user, status, updatedMinTimestamp, updatedMaxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepositsApi#listDeposits")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepositsApi#listDeposits")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **user** | **kotlin.String**| Ethereum address of the user who submitted this deposit | [optional]
 **status** | **kotlin.String**| Status of this deposit | [optional]
 **updatedMinTimestamp** | **kotlin.String**| Minimum timestamp for this deposit, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; | [optional]
 **updatedMaxTimestamp** | **kotlin.String**| Maximum timestamp for this deposit, in ISO 8601 UTC format. Example: &#39;2022-05-27T00:10:22Z&#39; | [optional]
 **tokenType** | **kotlin.String**| Token type of the deposited asset | [optional]
 **tokenId** | **kotlin.String**| ERC721 Token ID of the minted asset | [optional]
 **assetId** | **kotlin.String**| Internal IMX ID of the minted asset | [optional]
 **tokenAddress** | **kotlin.String**| Token address of the deposited asset | [optional]
 **tokenName** | **kotlin.String**| Token name of the deposited asset | [optional]
 **minQuantity** | **kotlin.String**| Min quantity for the deposited asset | [optional]
 **maxQuantity** | **kotlin.String**| Max quantity for the deposited asset | [optional]
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for the deposited asset | [optional]

### Return type

[**ListDepositsResponse**](ListDepositsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

