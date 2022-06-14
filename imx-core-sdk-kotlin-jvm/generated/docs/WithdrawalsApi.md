# WithdrawalsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createWithdrawal**](WithdrawalsApi.md#createWithdrawal) | **POST** /v1/withdrawals | Creates a withdrawal of a token
[**getSignableWithdrawal**](WithdrawalsApi.md#getSignableWithdrawal) | **POST** /v1/signable-withdrawal-details | Gets details of a signable withdrawal
[**getWithdrawal**](WithdrawalsApi.md#getWithdrawal) | **GET** /v1/withdrawals/{id} | Gets details of withdrawal with the given ID
[**listWithdrawals**](WithdrawalsApi.md#listWithdrawals) | **GET** /v1/withdrawals | Get a list of withdrawals


<a name="createWithdrawal"></a>
# **createWithdrawal**
> CreateWithdrawalResponse createWithdrawal(createWithdrawalRequest, xImxEthAddress, xImxEthSignature)

Creates a withdrawal of a token

Creates a withdrawal

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = WithdrawalsApi()
val createWithdrawalRequest : CreateWithdrawalRequest =  // CreateWithdrawalRequest | create a withdrawal
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateWithdrawalResponse = apiInstance.createWithdrawal(createWithdrawalRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WithdrawalsApi#createWithdrawal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WithdrawalsApi#createWithdrawal")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createWithdrawalRequest** | [**CreateWithdrawalRequest**](CreateWithdrawalRequest.md)| create a withdrawal |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateWithdrawalResponse**](CreateWithdrawalResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSignableWithdrawal"></a>
# **getSignableWithdrawal**
> GetSignableWithdrawalResponse getSignableWithdrawal(getSignableWithdrawalRequest)

Gets details of a signable withdrawal

Gets details of a signable withdrawal

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = WithdrawalsApi()
val getSignableWithdrawalRequest : GetSignableWithdrawalRequest =  // GetSignableWithdrawalRequest | get details of signable withdrawal
try {
    val result : GetSignableWithdrawalResponse = apiInstance.getSignableWithdrawal(getSignableWithdrawalRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WithdrawalsApi#getSignableWithdrawal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WithdrawalsApi#getSignableWithdrawal")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableWithdrawalRequest** | [**GetSignableWithdrawalRequest**](GetSignableWithdrawalRequest.md)| get details of signable withdrawal |

### Return type

[**GetSignableWithdrawalResponse**](GetSignableWithdrawalResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getWithdrawal"></a>
# **getWithdrawal**
> Withdrawal getWithdrawal(id)

Gets details of withdrawal with the given ID

Gets details of withdrawal with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = WithdrawalsApi()
val id : kotlin.String = id_example // kotlin.String | Withdrawal ID
try {
    val result : Withdrawal = apiInstance.getWithdrawal(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WithdrawalsApi#getWithdrawal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WithdrawalsApi#getWithdrawal")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Withdrawal ID |

### Return type

[**Withdrawal**](Withdrawal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listWithdrawals"></a>
# **listWithdrawals**
> ListWithdrawalsResponse listWithdrawals(withdrawnToWallet, rollupStatus, pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)

Get a list of withdrawals

Get a list of withdrawals

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = WithdrawalsApi()
val withdrawnToWallet : kotlin.Boolean = true // kotlin.Boolean | Withdrawal has been transferred to user's Layer 1 wallet
val rollupStatus : kotlin.String = rollupStatus_example // kotlin.String | Status of the on-chain batch confirmation for this withdrawal
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this withdrawal
val status : kotlin.String = status_example // kotlin.String | Status of this withdrawal
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum timestamp for this deposit
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum timestamp for this deposit
val tokenType : kotlin.String = tokenType_example // kotlin.String | Token type of the withdrawn asset
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 Token ID of the minted asset
val assetId : kotlin.String = assetId_example // kotlin.String | Internal IMX ID of the minted asset
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Token address of the withdrawn asset
val tokenName : kotlin.String = tokenName_example // kotlin.String | Token name of the withdrawn asset
val minQuantity : kotlin.String = minQuantity_example // kotlin.String | Min quantity for the withdrawn asset
val maxQuantity : kotlin.String = maxQuantity_example // kotlin.String | Max quantity for the withdrawn asset
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for the withdrawn asset
try {
    val result : ListWithdrawalsResponse = apiInstance.listWithdrawals(withdrawnToWallet, rollupStatus, pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WithdrawalsApi#listWithdrawals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WithdrawalsApi#listWithdrawals")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **withdrawnToWallet** | **kotlin.Boolean**| Withdrawal has been transferred to user&#39;s Layer 1 wallet | [optional]
 **rollupStatus** | **kotlin.String**| Status of the on-chain batch confirmation for this withdrawal | [optional]
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **user** | **kotlin.String**| Ethereum address of the user who submitted this withdrawal | [optional]
 **status** | **kotlin.String**| Status of this withdrawal | [optional]
 **minTimestamp** | **kotlin.String**| Minimum timestamp for this deposit | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum timestamp for this deposit | [optional]
 **tokenType** | **kotlin.String**| Token type of the withdrawn asset | [optional]
 **tokenId** | **kotlin.String**| ERC721 Token ID of the minted asset | [optional]
 **assetId** | **kotlin.String**| Internal IMX ID of the minted asset | [optional]
 **tokenAddress** | **kotlin.String**| Token address of the withdrawn asset | [optional]
 **tokenName** | **kotlin.String**| Token name of the withdrawn asset | [optional]
 **minQuantity** | **kotlin.String**| Min quantity for the withdrawn asset | [optional]
 **maxQuantity** | **kotlin.String**| Max quantity for the withdrawn asset | [optional]
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for the withdrawn asset | [optional]

### Return type

[**ListWithdrawalsResponse**](ListWithdrawalsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

