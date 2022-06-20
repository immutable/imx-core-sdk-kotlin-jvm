# CollectionsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCollection**](CollectionsApi.md#createCollection) | **POST** /v1/collections | Create collection
[**getCollection**](CollectionsApi.md#getCollection) | **GET** /v1/collections/{address} | Get details of a collection at the given address
[**listCollectionFilters**](CollectionsApi.md#listCollectionFilters) | **GET** /v1/collections/{address}/filters | Get a list of collection filters
[**listCollections**](CollectionsApi.md#listCollections) | **GET** /v1/collections | Get a list of collections
[**updateCollection**](CollectionsApi.md#updateCollection) | **PATCH** /v1/collections/{address} | Update collection


<a name="createCollection"></a>
# **createCollection**
> Collection createCollection(imXSignature, imXTimestamp, createCollectionRequest)

Create collection

Create collection

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = CollectionsApi()
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val createCollectionRequest : CreateCollectionRequest =  // CreateCollectionRequest | create a collection
try {
    val result : Collection = apiInstance.createCollection(imXSignature, imXTimestamp, createCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CollectionsApi#createCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CollectionsApi#createCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **imXSignature** | **kotlin.String**| String created by signing wallet address and timestamp |
 **imXTimestamp** | **kotlin.String**| Unix Epoc timestamp |
 **createCollectionRequest** | [**CreateCollectionRequest**](CreateCollectionRequest.md)| create a collection |

### Return type

[**Collection**](Collection.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCollection"></a>
# **getCollection**
> Collection getCollection(address)

Get details of a collection at the given address

Get details of a collection at the given address

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = CollectionsApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
try {
    val result : Collection = apiInstance.getCollection(address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CollectionsApi#getCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CollectionsApi#getCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Collection contract address |

### Return type

[**Collection**](Collection.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listCollectionFilters"></a>
# **listCollectionFilters**
> CollectionFilter listCollectionFilters(address, pageSize, nextPageToken)

Get a list of collection filters

Get a list of collection filters

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = CollectionsApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val nextPageToken : kotlin.String = nextPageToken_example // kotlin.String | Next page token
try {
    val result : CollectionFilter = apiInstance.listCollectionFilters(address, pageSize, nextPageToken)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CollectionsApi#listCollectionFilters")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CollectionsApi#listCollectionFilters")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Collection contract address |
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **nextPageToken** | **kotlin.String**| Next page token | [optional]

### Return type

[**CollectionFilter**](CollectionFilter.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listCollections"></a>
# **listCollections**
> ListCollectionsResponse listCollections(pageSize, cursor, orderBy, direction, blacklist, whitelist, keyword)

Get a list of collections

Get a list of collections

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = CollectionsApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val blacklist : kotlin.String = blacklist_example // kotlin.String | List of collections not to be included, separated by commas
val whitelist : kotlin.String = whitelist_example // kotlin.String | List of collections to be included, separated by commas
val keyword : kotlin.String = keyword_example // kotlin.String | Keyword to search in collection name and description
try {
    val result : ListCollectionsResponse = apiInstance.listCollections(pageSize, cursor, orderBy, direction, blacklist, whitelist, keyword)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CollectionsApi#listCollections")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CollectionsApi#listCollections")
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
 **blacklist** | **kotlin.String**| List of collections not to be included, separated by commas | [optional]
 **whitelist** | **kotlin.String**| List of collections to be included, separated by commas | [optional]
 **keyword** | **kotlin.String**| Keyword to search in collection name and description | [optional]

### Return type

[**ListCollectionsResponse**](ListCollectionsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateCollection"></a>
# **updateCollection**
> Collection updateCollection(address, imXSignature, imXTimestamp, updateCollectionRequest)

Update collection

Update collection

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = CollectionsApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val updateCollectionRequest : UpdateCollectionRequest =  // UpdateCollectionRequest | update a collection
try {
    val result : Collection = apiInstance.updateCollection(address, imXSignature, imXTimestamp, updateCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CollectionsApi#updateCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CollectionsApi#updateCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Collection contract address |
 **imXSignature** | **kotlin.String**| String created by signing wallet address and timestamp |
 **imXTimestamp** | **kotlin.String**| Unix Epoc timestamp |
 **updateCollectionRequest** | [**UpdateCollectionRequest**](UpdateCollectionRequest.md)| update a collection |

### Return type

[**Collection**](Collection.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

