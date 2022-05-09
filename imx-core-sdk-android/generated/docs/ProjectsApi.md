# ProjectsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createProject**](ProjectsApi.md#createProject) | **POST** /v1/projects | Create a project
[**getProject**](ProjectsApi.md#getProject) | **GET** /v1/projects/{id} | Get a project
[**getProjects**](ProjectsApi.md#getProjects) | **GET** /v1/projects | Get projects


<a name="createProject"></a>
# **createProject**
> CreateProjectResponse createProject(imXSignature, imXTimestamp, createProjectRequest)

Create a project

Create a project

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = ProjectsApi()
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val createProjectRequest : CreateProjectRequest =  // CreateProjectRequest | create a project
try {
    val result : CreateProjectResponse = apiInstance.createProject(imXSignature, imXTimestamp, createProjectRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProjectsApi#createProject")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProjectsApi#createProject")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **imXSignature** | **kotlin.String**| String created by signing wallet address and timestamp |
 **imXTimestamp** | **kotlin.String**| Unix Epoc timestamp |
 **createProjectRequest** | [**CreateProjectRequest**](CreateProjectRequest.md)| create a project |

### Return type

[**CreateProjectResponse**](CreateProjectResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getProject"></a>
# **getProject**
> Project getProject(id, imXSignature, imXTimestamp)

Get a project

Get a project

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = ProjectsApi()
val id : kotlin.String = id_example // kotlin.String | Project ID
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
try {
    val result : Project = apiInstance.getProject(id, imXSignature, imXTimestamp)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProjectsApi#getProject")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProjectsApi#getProject")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Project ID |
 **imXSignature** | **kotlin.String**| String created by signing wallet address and timestamp |
 **imXTimestamp** | **kotlin.String**| Unix Epoc timestamp |

### Return type

[**Project**](Project.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getProjects"></a>
# **getProjects**
> GetProjectsResponse getProjects(imXSignature, imXTimestamp, pageSize, cursor, orderBy, direction)

Get projects

Get projects

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = ProjectsApi()
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
try {
    val result : GetProjectsResponse = apiInstance.getProjects(imXSignature, imXTimestamp, pageSize, cursor, orderBy, direction)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProjectsApi#getProjects")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProjectsApi#getProjects")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **imXSignature** | **kotlin.String**| String created by signing wallet address and timestamp |
 **imXTimestamp** | **kotlin.String**| Unix Epoc timestamp |
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]

### Return type

[**GetProjectsResponse**](GetProjectsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

