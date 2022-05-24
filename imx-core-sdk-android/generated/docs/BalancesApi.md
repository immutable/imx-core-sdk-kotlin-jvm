# BalancesApi

All URIs are relative to *http://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getBalance**](BalancesApi.md#getBalance) | **GET** /v2/balances/{owner}/{address} | Fetches the token balances of the user
[**listBalances**](BalancesApi.md#listBalances) | **GET** /v2/balances/{owner} | Get a list of balances for given user


<a name="getBalance"></a>
# **getBalance**
> Balance getBalance(owner, address)

Fetches the token balances of the user

Fetches the token balances of the user

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = BalancesApi()
val owner : kotlin.String = owner_example // kotlin.String | Address of the owner/user
val address : kotlin.String = address_example // kotlin.String | Token address
try {
    val result : Balance = apiInstance.getBalance(owner, address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling BalancesApi#getBalance")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling BalancesApi#getBalance")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **owner** | **kotlin.String**| Address of the owner/user |
 **address** | **kotlin.String**| Token address |

### Return type

[**Balance**](Balance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listBalances"></a>
# **listBalances**
> ListBalancesResponse listBalances(owner)

Get a list of balances for given user

Get a list of balances for given user

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = BalancesApi()
val owner : kotlin.String = owner_example // kotlin.String | Ethereum wallet address for user
try {
    val result : ListBalancesResponse = apiInstance.listBalances(owner)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling BalancesApi#listBalances")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling BalancesApi#listBalances")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **owner** | **kotlin.String**| Ethereum wallet address for user |

### Return type

[**ListBalancesResponse**](ListBalancesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

