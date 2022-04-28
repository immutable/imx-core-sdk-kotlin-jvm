# ClaimsApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**claims**](ClaimsApi.md#claims) | **POST** /v1/rewards | User claim to redeem campaign rewards
[**listClaims**](ClaimsApi.md#listClaims) | **GET** /v1/rewards/{etherKey} | Get list of reward claims for a user


<a name="claims"></a>
# **claims**
> ClaimRewardResponse claims(claimRewardRequest)

User claim to redeem campaign rewards

Distribute campaign rewards

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = ClaimsApi()
val claimRewardRequest : ClaimRewardRequest =  // ClaimRewardRequest | details of claim
try {
    val result : ClaimRewardResponse = apiInstance.claims(claimRewardRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ClaimsApi#claims")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ClaimsApi#claims")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **claimRewardRequest** | [**ClaimRewardRequest**](ClaimRewardRequest.md)| details of claim |

### Return type

[**ClaimRewardResponse**](ClaimRewardResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listClaims"></a>
# **listClaims**
> ListRewardsResponse listClaims(etherKey)

Get list of reward claims for a user

Get list of claims a user can make to redeem campaign rewards

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = ClaimsApi()
val etherKey : kotlin.String = etherKey_example // kotlin.String | User's wallet address
try {
    val result : ListRewardsResponse = apiInstance.listClaims(etherKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ClaimsApi#listClaims")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ClaimsApi#listClaims")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **etherKey** | **kotlin.String**| User&#39;s wallet address |

### Return type

[**ListRewardsResponse**](ListRewardsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

