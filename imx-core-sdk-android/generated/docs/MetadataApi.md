# MetadataApi

All URIs are relative to *http://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addMetadataSchemaToCollection**](MetadataApi.md#addMetadataSchemaToCollection) | **POST** /v1/collections/{address}/metadata-schema | Add metadata schema to collection
[**getMetadataSchema**](MetadataApi.md#getMetadataSchema) | **GET** /v1/collections/{address}/metadata-schema | Get collection metadata schema
[**updateMetadataSchemaByName**](MetadataApi.md#updateMetadataSchemaByName) | **PATCH** /v1/collections/{address}/metadata-schema/{name} | Update metadata schema by name


<a name="addMetadataSchemaToCollection"></a>
# **addMetadataSchemaToCollection**
> SuccessResponse addMetadataSchemaToCollection(address, imXSignature, imXTimestamp, addMetadataSchemaToCollectionRequest)

Add metadata schema to collection

Add metadata schema to collection

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = MetadataApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val addMetadataSchemaToCollectionRequest : AddMetadataSchemaToCollectionRequest =  // AddMetadataSchemaToCollectionRequest | add metadata schema to a collection
try {
    val result : SuccessResponse = apiInstance.addMetadataSchemaToCollection(address, imXSignature, imXTimestamp, addMetadataSchemaToCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MetadataApi#addMetadataSchemaToCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MetadataApi#addMetadataSchemaToCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Collection contract address |
 **imXSignature** | **kotlin.String**| String created by signing wallet address and timestamp |
 **imXTimestamp** | **kotlin.String**| Unix Epoc timestamp |
 **addMetadataSchemaToCollectionRequest** | [**AddMetadataSchemaToCollectionRequest**](AddMetadataSchemaToCollectionRequest.md)| add metadata schema to a collection |

### Return type

[**SuccessResponse**](SuccessResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMetadataSchema"></a>
# **getMetadataSchema**
> kotlin.collections.List&lt;MetadataSchemaProperty&gt; getMetadataSchema(address)

Get collection metadata schema

Get collection metadata schema

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = MetadataApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
try {
    val result : kotlin.collections.List<MetadataSchemaProperty> = apiInstance.getMetadataSchema(address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MetadataApi#getMetadataSchema")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MetadataApi#getMetadataSchema")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Collection contract address |

### Return type

[**kotlin.collections.List&lt;MetadataSchemaProperty&gt;**](MetadataSchemaProperty.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateMetadataSchemaByName"></a>
# **updateMetadataSchemaByName**
> SuccessResponse updateMetadataSchemaByName(address, name, imXSignature, imXTimestamp, metadataSchemaRequest)

Update metadata schema by name

Update metadata schema by name

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = MetadataApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val name : kotlin.String = name_example // kotlin.String | Metadata schema name
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val metadataSchemaRequest : MetadataSchemaRequest =  // MetadataSchemaRequest | update metadata schema
try {
    val result : SuccessResponse = apiInstance.updateMetadataSchemaByName(address, name, imXSignature, imXTimestamp, metadataSchemaRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MetadataApi#updateMetadataSchemaByName")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MetadataApi#updateMetadataSchemaByName")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Collection contract address |
 **name** | **kotlin.String**| Metadata schema name |
 **imXSignature** | **kotlin.String**| String created by signing wallet address and timestamp |
 **imXTimestamp** | **kotlin.String**| Unix Epoc timestamp |
 **metadataSchemaRequest** | [**MetadataSchemaRequest**](MetadataSchemaRequest.md)| update metadata schema |

### Return type

[**SuccessResponse**](SuccessResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

