# ApplicationsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getApplication**](ApplicationsApi.md#getApplication) | **GET** /v1/applications/{id} | Get details of an application with the given ID
[**listApplications**](ApplicationsApi.md#listApplications) | **GET** /v1/applications | Get a list of applications


<a name="getApplication"></a>
# **getApplication**
> Application getApplication(id)

Get details of an application with the given ID

Get details of an application with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = ApplicationsApi()
val id : kotlin.String = id_example // kotlin.String | Application ID
try {
    val result : Application = apiInstance.getApplication(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ApplicationsApi#getApplication")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ApplicationsApi#getApplication")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Application ID |

### Return type

[**Application**](Application.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listApplications"></a>
# **listApplications**
> ListApplicationsResponse listApplications(pageSize, cursor, orderBy, direction)

Get a list of applications

Get a list of applications

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = ApplicationsApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
try {
    val result : ListApplicationsResponse = apiInstance.listApplications(pageSize, cursor, orderBy, direction)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ApplicationsApi#listApplications")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ApplicationsApi#listApplications")
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

### Return type

[**ListApplicationsResponse**](ListApplicationsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

