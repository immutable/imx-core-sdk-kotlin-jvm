# MintsApi

All URIs are relative to *http://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getMint**](MintsApi.md#getMint) | **GET** /v1/mints/{id} | Get details of a mint with the given ID
[**getMintableTokenDetailsByClientTokenId**](MintsApi.md#getMintableTokenDetailsByClientTokenId) | **GET** /v1/mintable-token/{token_address}/{token_id} | Get details of a mintable token with the given token address and token ID
[**listMints**](MintsApi.md#listMints) | **GET** /v1/mints | Get a list of mints
[**mintTokens**](MintsApi.md#mintTokens) | **POST** /v2/mints | Mint Tokens V2


<a name="getMint"></a>
# **getMint**
> Mint getMint(id)

Get details of a mint with the given ID

Get details of a mint with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = MintsApi()
val id : kotlin.String = id_example // kotlin.String | Mint ID. This is the transaction_id returned from listMints
try {
    val result : Mint = apiInstance.getMint(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MintsApi#getMint")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MintsApi#getMint")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Mint ID. This is the transaction_id returned from listMints |

### Return type

[**Mint**](Mint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMintableTokenDetailsByClientTokenId"></a>
# **getMintableTokenDetailsByClientTokenId**
> MintableTokenDetails getMintableTokenDetailsByClientTokenId(tokenAddress, tokenId)

Get details of a mintable token with the given token address and token ID

Get details of a mintable token with the given token address and token ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = MintsApi()
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Address of the ERC721 contract
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 token ID
try {
    val result : MintableTokenDetails = apiInstance.getMintableTokenDetailsByClientTokenId(tokenAddress, tokenId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MintsApi#getMintableTokenDetailsByClientTokenId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MintsApi#getMintableTokenDetailsByClientTokenId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenAddress** | **kotlin.String**| Address of the ERC721 contract |
 **tokenId** | **kotlin.String**| ERC721 token ID |

### Return type

[**MintableTokenDetails**](MintableTokenDetails.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listMints"></a>
# **listMints**
> ListMintsResponse listMints(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenName, tokenAddress, minQuantity, maxQuantity, metadata)

Get a list of mints

Get a list of mints

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = MintsApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this mint
val status : kotlin.String = status_example // kotlin.String | Status of this mint
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum timestamp for this mint
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum timestamp for this mint
val tokenType : kotlin.String = tokenType_example // kotlin.String | Token type of the minted asset
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 Token ID of the minted asset
val assetId : kotlin.String = assetId_example // kotlin.String | Internal IMX ID of the minted asset
val tokenName : kotlin.String = tokenName_example // kotlin.String | Token Name of the minted asset
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Token address of the minted asset
val minQuantity : kotlin.String = minQuantity_example // kotlin.String | Min quantity for the minted asset
val maxQuantity : kotlin.String = maxQuantity_example // kotlin.String | Max quantity for the minted asset
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for the minted asset
try {
    val result : ListMintsResponse = apiInstance.listMints(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenName, tokenAddress, minQuantity, maxQuantity, metadata)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MintsApi#listMints")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MintsApi#listMints")
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
 **user** | **kotlin.String**| Ethereum address of the user who submitted this mint | [optional]
 **status** | **kotlin.String**| Status of this mint | [optional]
 **minTimestamp** | **kotlin.String**| Minimum timestamp for this mint | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum timestamp for this mint | [optional]
 **tokenType** | **kotlin.String**| Token type of the minted asset | [optional]
 **tokenId** | **kotlin.String**| ERC721 Token ID of the minted asset | [optional]
 **assetId** | **kotlin.String**| Internal IMX ID of the minted asset | [optional]
 **tokenName** | **kotlin.String**| Token Name of the minted asset | [optional]
 **tokenAddress** | **kotlin.String**| Token address of the minted asset | [optional]
 **minQuantity** | **kotlin.String**| Min quantity for the minted asset | [optional]
 **maxQuantity** | **kotlin.String**| Max quantity for the minted asset | [optional]
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for the minted asset | [optional]

### Return type

[**ListMintsResponse**](ListMintsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="mintTokens"></a>
# **mintTokens**
> MintTokensResponse mintTokens(mintTokensRequestV2)

Mint Tokens V2

Mint tokens in a batch with fees

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = MintsApi()
val mintTokensRequestV2 : kotlin.collections.List<MintRequest> =  // kotlin.collections.List<MintRequest> | details of tokens to mint
try {
    val result : MintTokensResponse = apiInstance.mintTokens(mintTokensRequestV2)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MintsApi#mintTokens")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MintsApi#mintTokens")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mintTokensRequestV2** | [**kotlin.collections.List&lt;MintRequest&gt;**](MintRequest.md)| details of tokens to mint |

### Return type

[**MintTokensResponse**](MintTokensResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

