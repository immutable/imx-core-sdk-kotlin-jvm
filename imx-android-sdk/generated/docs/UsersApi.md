# UsersApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getSignableRegistration**](UsersApi.md#getSignableRegistration) | **POST** /v1/signable-registration | Get operator signature to allow clients to register the user
[**getUsers**](UsersApi.md#getUsers) | **GET** /v1/users/{user} | Get stark keys for a registered user
[**registerUser**](UsersApi.md#registerUser) | **POST** /v1/users | Registers a user


<a name="getSignableRegistration"></a>
# **getSignableRegistration**
> GetSignableRegistrationResponse getSignableRegistration(getSignableRegistrationRequest)

Get operator signature to allow clients to register the user

Get operator signature to allow clients to register the user

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = UsersApi()
val getSignableRegistrationRequest : GetSignableRegistrationRequest =  // GetSignableRegistrationRequest | Register User
try {
    val result : GetSignableRegistrationResponse = apiInstance.getSignableRegistration(getSignableRegistrationRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UsersApi#getSignableRegistration")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersApi#getSignableRegistration")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableRegistrationRequest** | [**GetSignableRegistrationRequest**](GetSignableRegistrationRequest.md)| Register User |

### Return type

[**GetSignableRegistrationResponse**](GetSignableRegistrationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUsers"></a>
# **getUsers**
> GetUsersApiResponse getUsers(user)

Get stark keys for a registered user

Get stark keys for a registered user

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = UsersApi()
val user : kotlin.String = user_example // kotlin.String | User
try {
    val result : GetUsersApiResponse = apiInstance.getUsers(user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UsersApi#getUsers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersApi#getUsers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user** | **kotlin.String**| User |

### Return type

[**GetUsersApiResponse**](GetUsersApiResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="registerUser"></a>
# **registerUser**
> RegisterUserResponse registerUser(registerUserRequestVerifyEth)

Registers a user

Registers a user

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = UsersApi()
val registerUserRequestVerifyEth : RegisterUserRequestVerifyEth =  // RegisterUserRequestVerifyEth | Register User
try {
    val result : RegisterUserResponse = apiInstance.registerUser(registerUserRequestVerifyEth)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UsersApi#registerUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersApi#registerUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **registerUserRequestVerifyEth** | [**RegisterUserRequestVerifyEth**](RegisterUserRequestVerifyEth.md)| Register User |

### Return type

[**RegisterUserResponse**](RegisterUserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

