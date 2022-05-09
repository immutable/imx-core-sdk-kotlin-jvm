# TransfersApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createTransfer**](TransfersApi.md#createTransfer) | **POST** /v2/transfers | Creates a transfer of multiple tokens between two parties
[**createTransferV1**](TransfersApi.md#createTransferV1) | **POST** /v1/transfers | Creates a transfer of tokens between two parties
[**getSignableTransfer**](TransfersApi.md#getSignableTransfer) | **POST** /v2/signable-transfer-details | Gets bulk details of a signable transfer
[**getSignableTransferV1**](TransfersApi.md#getSignableTransferV1) | **POST** /v1/signable-transfer-details | Gets details of a signable transfer
[**getTransfer**](TransfersApi.md#getTransfer) | **GET** /v1/transfers/{id} | Get details of a transfer with the given ID
[**listTransfers**](TransfersApi.md#listTransfers) | **GET** /v1/transfers | Get a list of transfers


<a name="createTransfer"></a>
# **createTransfer**
> CreateTransferResponse createTransfer(createTransferRequestV2, xImxEthAddress, xImxEthSignature)

Creates a transfer of multiple tokens between two parties

Create a new transfer request

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TransfersApi()
val createTransferRequestV2 : CreateTransferRequest =  // CreateTransferRequest | Create transfer
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateTransferResponse = apiInstance.createTransfer(createTransferRequestV2, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TransfersApi#createTransfer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TransfersApi#createTransfer")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createTransferRequestV2** | [**CreateTransferRequest**](CreateTransferRequest.md)| Create transfer |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateTransferResponse**](CreateTransferResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createTransferV1"></a>
# **createTransferV1**
> CreateTransferResponseV1 createTransferV1(createTransferRequest, xImxEthAddress, xImxEthSignature)

Creates a transfer of tokens between two parties

Create a new transfer request

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TransfersApi()
val createTransferRequest : CreateTransferRequestV1 =  // CreateTransferRequestV1 | Create transfer
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateTransferResponseV1 = apiInstance.createTransferV1(createTransferRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TransfersApi#createTransferV1")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TransfersApi#createTransferV1")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createTransferRequest** | [**CreateTransferRequestV1**](CreateTransferRequestV1.md)| Create transfer |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateTransferResponseV1**](CreateTransferResponseV1.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSignableTransfer"></a>
# **getSignableTransfer**
> GetSignableTransferResponse getSignableTransfer(getSignableTransferRequestV2)

Gets bulk details of a signable transfer

Gets bulk details of a signable transfer

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TransfersApi()
val getSignableTransferRequestV2 : GetSignableTransferRequest =  // GetSignableTransferRequest | get details of signable transfer
try {
    val result : GetSignableTransferResponse = apiInstance.getSignableTransfer(getSignableTransferRequestV2)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TransfersApi#getSignableTransfer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TransfersApi#getSignableTransfer")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableTransferRequestV2** | [**GetSignableTransferRequest**](GetSignableTransferRequest.md)| get details of signable transfer |

### Return type

[**GetSignableTransferResponse**](GetSignableTransferResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSignableTransferV1"></a>
# **getSignableTransferV1**
> GetSignableTransferResponseV1 getSignableTransferV1(getSignableTransferRequest)

Gets details of a signable transfer

Gets details of a signable transfer

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TransfersApi()
val getSignableTransferRequest : GetSignableTransferRequestV1 =  // GetSignableTransferRequestV1 | get details of signable transfer
try {
    val result : GetSignableTransferResponseV1 = apiInstance.getSignableTransferV1(getSignableTransferRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TransfersApi#getSignableTransferV1")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TransfersApi#getSignableTransferV1")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableTransferRequest** | [**GetSignableTransferRequestV1**](GetSignableTransferRequestV1.md)| get details of signable transfer |

### Return type

[**GetSignableTransferResponseV1**](GetSignableTransferResponseV1.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTransfer"></a>
# **getTransfer**
> Transfer getTransfer(id)

Get details of a transfer with the given ID

Get details of a transfer with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TransfersApi()
val id : kotlin.String = id_example // kotlin.String | Transfer ID
try {
    val result : Transfer = apiInstance.getTransfer(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TransfersApi#getTransfer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TransfersApi#getTransfer")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Transfer ID |

### Return type

[**Transfer**](Transfer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listTransfers"></a>
# **listTransfers**
> ListTransfersResponse listTransfers(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)

Get a list of transfers

Get a list of transfers

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = TransfersApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this transfer
val status : kotlin.String = status_example // kotlin.String | Status of this transfer
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum timestamp for this transfer
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum timestamp for this transfer
val tokenType : kotlin.String = tokenType_example // kotlin.String | Token type of the transferred asset
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 Token ID of the minted asset
val assetId : kotlin.String = assetId_example // kotlin.String | Internal IMX ID of the minted asset
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Token address of the transferred asset
val tokenName : kotlin.String = tokenName_example // kotlin.String | Token name of the transferred asset
val minQuantity : kotlin.String = minQuantity_example // kotlin.String | Max quantity for the transferred asset
val maxQuantity : kotlin.String = maxQuantity_example // kotlin.String | Max quantity for the transferred asset
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for the transferred asset
try {
    val result : ListTransfersResponse = apiInstance.listTransfers(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TransfersApi#listTransfers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TransfersApi#listTransfers")
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
 **user** | **kotlin.String**| Ethereum address of the user who submitted this transfer | [optional]
 **status** | **kotlin.String**| Status of this transfer | [optional] [enum: success, failure]
 **minTimestamp** | **kotlin.String**| Minimum timestamp for this transfer | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum timestamp for this transfer | [optional]
 **tokenType** | **kotlin.String**| Token type of the transferred asset | [optional]
 **tokenId** | **kotlin.String**| ERC721 Token ID of the minted asset | [optional]
 **assetId** | **kotlin.String**| Internal IMX ID of the minted asset | [optional]
 **tokenAddress** | **kotlin.String**| Token address of the transferred asset | [optional]
 **tokenName** | **kotlin.String**| Token name of the transferred asset | [optional]
 **minQuantity** | **kotlin.String**| Max quantity for the transferred asset | [optional]
 **maxQuantity** | **kotlin.String**| Max quantity for the transferred asset | [optional]
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for the transferred asset | [optional]

### Return type

[**ListTransfersResponse**](ListTransfersResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

