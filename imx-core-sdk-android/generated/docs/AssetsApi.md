# AssetsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAsset**](AssetsApi.md#getAsset) | **GET** /v1/assets/{token_address}/{token_id} | Get details of an asset
[**listAssets**](AssetsApi.md#listAssets) | **GET** /v1/assets | Get a list of assets


<a name="getAsset"></a>
# **getAsset**
> Asset getAsset(tokenAddress, tokenId, includeFees)

Get details of an asset

Get details of an asset

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = AssetsApi()
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Address of the ERC721 contract
val tokenId : kotlin.String = tokenId_example // kotlin.String | Either ERC721 token ID or internal IMX ID
val includeFees : kotlin.Boolean = true // kotlin.Boolean | Set flag to include fees associated with the asset
try {
    val result : Asset = apiInstance.getAsset(tokenAddress, tokenId, includeFees)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AssetsApi#getAsset")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AssetsApi#getAsset")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tokenAddress** | **kotlin.String**| Address of the ERC721 contract |
 **tokenId** | **kotlin.String**| Either ERC721 token ID or internal IMX ID |
 **includeFees** | **kotlin.Boolean**| Set flag to include fees associated with the asset | [optional]

### Return type

[**Asset**](Asset.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listAssets"></a>
# **listAssets**
> ListAssetsResponse listAssets(pageSize, cursor, orderBy, direction, user, status, name, metadata, sellOrders, buyOrders, includeFees, collection, updatedMinTimestamp, updatedMaxTimestamp, auxiliaryFeePercentages, auxiliaryFeeRecipients)

Get a list of assets

Get a list of assets

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = AssetsApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who owns these assets
val status : kotlin.String = status_example // kotlin.String | Status of these assets
val name : kotlin.String = name_example // kotlin.String | Name of the asset to search
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for these asset. Example: {'proto':['1147'],'quality':['Meteorite']}
val sellOrders : kotlin.Boolean = true // kotlin.Boolean | Set flag to true to fetch an array of sell order details with accepted status associated with the asset
val buyOrders : kotlin.Boolean = true // kotlin.Boolean | Set flag to true to fetch an array of buy order details  with accepted status associated with the asset
val includeFees : kotlin.Boolean = true // kotlin.Boolean | Set flag to include fees associated with the asset
val collection : kotlin.String = collection_example // kotlin.String | Collection contract address
val updatedMinTimestamp : kotlin.String = updatedMinTimestamp_example // kotlin.String | Minimum timestamp for when these assets were last updated
val updatedMaxTimestamp : kotlin.String = updatedMaxTimestamp_example // kotlin.String | Maximum timestamp for when these assets were last updated
val auxiliaryFeePercentages : kotlin.String = auxiliaryFeePercentages_example // kotlin.String | Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients
val auxiliaryFeeRecipients : kotlin.String = auxiliaryFeeRecipients_example // kotlin.String | Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages
try {
    val result : ListAssetsResponse = apiInstance.listAssets(pageSize, cursor, orderBy, direction, user, status, name, metadata, sellOrders, buyOrders, includeFees, collection, updatedMinTimestamp, updatedMaxTimestamp, auxiliaryFeePercentages, auxiliaryFeeRecipients)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AssetsApi#listAssets")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AssetsApi#listAssets")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional] [enum: updated_at, name]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **user** | **kotlin.String**| Ethereum address of the user who owns these assets | [optional]
 **status** | **kotlin.String**| Status of these assets | [optional]
 **name** | **kotlin.String**| Name of the asset to search | [optional]
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for these asset. Example: {&#39;proto&#39;:[&#39;1147&#39;],&#39;quality&#39;:[&#39;Meteorite&#39;]} | [optional]
 **sellOrders** | **kotlin.Boolean**| Set flag to true to fetch an array of sell order details with accepted status associated with the asset | [optional]
 **buyOrders** | **kotlin.Boolean**| Set flag to true to fetch an array of buy order details  with accepted status associated with the asset | [optional]
 **includeFees** | **kotlin.Boolean**| Set flag to include fees associated with the asset | [optional]
 **collection** | **kotlin.String**| Collection contract address | [optional]
 **updatedMinTimestamp** | **kotlin.String**| Minimum timestamp for when these assets were last updated | [optional]
 **updatedMaxTimestamp** | **kotlin.String**| Maximum timestamp for when these assets were last updated | [optional]
 **auxiliaryFeePercentages** | **kotlin.String**| Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients | [optional]
 **auxiliaryFeeRecipients** | **kotlin.String**| Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages | [optional]

### Return type

[**ListAssetsResponse**](ListAssetsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

