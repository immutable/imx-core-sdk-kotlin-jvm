# PublicApi

All URIs are relative to *https://api.ropsten.x.immutable.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addMetadataSchemaToCollection**](PublicApi.md#addMetadataSchemaToCollection) | **POST** /v1/collections/{address}/metadata-schema | Add metadata schema to collection
[**cancelOrder**](PublicApi.md#cancelOrder) | **DELETE** /v1/orders/{id} | cancel an order
[**createCollection**](PublicApi.md#createCollection) | **POST** /v1/collections | Create collection
[**createOrder**](PublicApi.md#createOrder) | **POST** /v1/orders | Create an order
[**createProject**](PublicApi.md#createProject) | **POST** /v1/projects | Create a project
[**createTrade**](PublicApi.md#createTrade) | **POST** /v1/trades | Create a Trade between two parties
[**createTransfer**](PublicApi.md#createTransfer) | **POST** /v2/transfers | Creates a transfer of multiple tokens between two parties
[**createTransferV1**](PublicApi.md#createTransferV1) | **POST** /v1/transfers | Creates a transfer of tokens between two parties
[**createWithdrawal**](PublicApi.md#createWithdrawal) | **POST** /v1/withdrawals | Creates a withdrawal of a token
[**getAsset**](PublicApi.md#getAsset) | **GET** /v1/assets/{token_address}/{token_id} | Get details of an asset
[**getBalance**](PublicApi.md#getBalance) | **GET** /v2/balances/{owner}/{address} | Fetches the token balances of the user
[**getCollection**](PublicApi.md#getCollection) | **GET** /v1/collections/{address} | Get details of a collection at the given address
[**getDeposit**](PublicApi.md#getDeposit) | **GET** /v1/deposits/{id} | Get details of a deposit with the given ID
[**getMetadataSchema**](PublicApi.md#getMetadataSchema) | **GET** /v1/collections/{address}/metadata-schema | Get collection metadata schema
[**getMint**](PublicApi.md#getMint) | **GET** /v1/mints/{id} | Get details of a mint with the given ID
[**getMintableTokenDetailsByClientTokenId**](PublicApi.md#getMintableTokenDetailsByClientTokenId) | **GET** /v1/mintable-token/{token_address}/{token_id} | Get details of a mintable token with the given token address and token ID
[**getOrder**](PublicApi.md#getOrder) | **GET** /v1/orders/{id} | Get details of an order with the given ID
[**getProject**](PublicApi.md#getProject) | **GET** /v1/projects/{id} | Get a project
[**getProjects**](PublicApi.md#getProjects) | **GET** /v1/projects | Get projects
[**getSignableCancelOrder**](PublicApi.md#getSignableCancelOrder) | **POST** /v1/signable-cancel-order-details | Get details a signable cancel order
[**getSignableDeposit**](PublicApi.md#getSignableDeposit) | **POST** /v1/signable-deposit-details | Gets details of a signable deposit
[**getSignableOrder**](PublicApi.md#getSignableOrder) | **POST** /v3/signable-order-details | Get details a signable order V3
[**getSignableRegistration**](PublicApi.md#getSignableRegistration) | **POST** /v1/signable-registration | Get operator signature to allow clients to register the user
[**getSignableRegistrationOffchain**](PublicApi.md#getSignableRegistrationOffchain) | **POST** /v1/signable-registration-offchain | Get encoded details to allow clients to register the user offchain
[**getSignableTrade**](PublicApi.md#getSignableTrade) | **POST** /v3/signable-trade-details | Get details a signable trade V3
[**getSignableTransfer**](PublicApi.md#getSignableTransfer) | **POST** /v2/signable-transfer-details | Gets bulk details of a signable transfer
[**getSignableTransferV1**](PublicApi.md#getSignableTransferV1) | **POST** /v1/signable-transfer-details | Gets details of a signable transfer
[**getSignableWithdrawal**](PublicApi.md#getSignableWithdrawal) | **POST** /v1/signable-withdrawal-details | Gets details of a signable withdrawal
[**getToken**](PublicApi.md#getToken) | **GET** /v1/tokens/{address} | Get details of a token
[**getTrade**](PublicApi.md#getTrade) | **GET** /v1/trades/{id} | Get details of a trade with the given ID
[**getTransfer**](PublicApi.md#getTransfer) | **GET** /v1/transfers/{id} | Get details of a transfer with the given ID
[**getUsers**](PublicApi.md#getUsers) | **GET** /v1/users/{user} | Get stark keys for a registered user
[**getWithdrawal**](PublicApi.md#getWithdrawal) | **GET** /v1/withdrawals/{id} | Gets details of withdrawal with the given ID
[**listAssets**](PublicApi.md#listAssets) | **GET** /v1/assets | Get a list of assets
[**listBalances**](PublicApi.md#listBalances) | **GET** /v2/balances/{owner} | Get a list of balances for given user
[**listCollectionFilters**](PublicApi.md#listCollectionFilters) | **GET** /v1/collections/{address}/filters | Get a list of collection filters
[**listCollections**](PublicApi.md#listCollections) | **GET** /v1/collections | Get a list of collections
[**listDeposits**](PublicApi.md#listDeposits) | **GET** /v1/deposits | Get a list of deposits
[**listMints**](PublicApi.md#listMints) | **GET** /v1/mints | Get a list of mints
[**listOrders**](PublicApi.md#listOrders) | **GET** /v1/orders | Get a list of orders
[**listTokens**](PublicApi.md#listTokens) | **GET** /v1/tokens | Get a list of tokens
[**listTrades**](PublicApi.md#listTrades) | **GET** /v1/trades | Get a list of trades
[**listTransfers**](PublicApi.md#listTransfers) | **GET** /v1/transfers | Get a list of transfers
[**listWithdrawals**](PublicApi.md#listWithdrawals) | **GET** /v1/withdrawals | Get a list of withdrawals
[**mintTokens**](PublicApi.md#mintTokens) | **POST** /v2/mints | Mint Tokens V2
[**registerUser**](PublicApi.md#registerUser) | **POST** /v1/users | Registers a user
[**updateCollection**](PublicApi.md#updateCollection) | **PATCH** /v1/collections/{address} | Update collection
[**updateMetadataSchemaByName**](PublicApi.md#updateMetadataSchemaByName) | **PATCH** /v1/collections/{address}/metadata-schema/{name} | Update metadata schema by name


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

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val addMetadataSchemaToCollectionRequest : AddMetadataSchemaToCollectionRequest =  // AddMetadataSchemaToCollectionRequest | add metadata schema to a collection
try {
    val result : SuccessResponse = apiInstance.addMetadataSchemaToCollection(address, imXSignature, imXTimestamp, addMetadataSchemaToCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#addMetadataSchemaToCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#addMetadataSchemaToCollection")
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

<a name="cancelOrder"></a>
# **cancelOrder**
> CancelOrderResponse cancelOrder(id, cancelOrderRequest, xImxEthAddress, xImxEthSignature)

cancel an order

Cancel an order

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Order ID to cancel
val cancelOrderRequest : CancelOrderRequest =  // CancelOrderRequest | cancel an order
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CancelOrderResponse = apiInstance.cancelOrder(id, cancelOrderRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#cancelOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#cancelOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Order ID to cancel |
 **cancelOrderRequest** | [**CancelOrderRequest**](CancelOrderRequest.md)| cancel an order |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CancelOrderResponse**](CancelOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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

val apiInstance = PublicApi()
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val createCollectionRequest : CreateCollectionRequest =  // CreateCollectionRequest | create a collection
try {
    val result : Collection = apiInstance.createCollection(imXSignature, imXTimestamp, createCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#createCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#createCollection")
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

<a name="createOrder"></a>
# **createOrder**
> CreateOrderResponse createOrder(createOrderRequest, xImxEthAddress, xImxEthSignature)

Create an order

Create an order

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val createOrderRequest : CreateOrderRequest =  // CreateOrderRequest | create an order
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateOrderResponse = apiInstance.createOrder(createOrderRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#createOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#createOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createOrderRequest** | [**CreateOrderRequest**](CreateOrderRequest.md)| create an order |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateOrderResponse**](CreateOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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

val apiInstance = PublicApi()
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val createProjectRequest : CreateProjectRequest =  // CreateProjectRequest | create a project
try {
    val result : CreateProjectResponse = apiInstance.createProject(imXSignature, imXTimestamp, createProjectRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#createProject")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#createProject")
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

<a name="createTrade"></a>
# **createTrade**
> CreateTradeResponse createTrade(createTradeRequest, xImxEthAddress, xImxEthSignature)

Create a Trade between two parties

Create a Trade

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val createTradeRequest : CreateTradeRequestV1 =  // CreateTradeRequestV1 | create a trade
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateTradeResponse = apiInstance.createTrade(createTradeRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#createTrade")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#createTrade")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createTradeRequest** | [**CreateTradeRequestV1**](CreateTradeRequestV1.md)| create a trade |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateTradeResponse**](CreateTradeResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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

val apiInstance = PublicApi()
val createTransferRequestV2 : CreateTransferRequest =  // CreateTransferRequest | Create transfer
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateTransferResponse = apiInstance.createTransfer(createTransferRequestV2, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#createTransfer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#createTransfer")
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

val apiInstance = PublicApi()
val createTransferRequest : CreateTransferRequestV1 =  // CreateTransferRequestV1 | Create transfer
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateTransferResponseV1 = apiInstance.createTransferV1(createTransferRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#createTransferV1")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#createTransferV1")
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

<a name="createWithdrawal"></a>
# **createWithdrawal**
> CreateWithdrawalResponse createWithdrawal(createWithdrawalRequest, xImxEthAddress, xImxEthSignature)

Creates a withdrawal of a token

Creates a withdrawal

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val createWithdrawalRequest : CreateWithdrawalRequest =  // CreateWithdrawalRequest | create a withdrawal
val xImxEthAddress : kotlin.String = xImxEthAddress_example // kotlin.String | eth address
val xImxEthSignature : kotlin.String = xImxEthSignature_example // kotlin.String | eth signature
try {
    val result : CreateWithdrawalResponse = apiInstance.createWithdrawal(createWithdrawalRequest, xImxEthAddress, xImxEthSignature)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#createWithdrawal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#createWithdrawal")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createWithdrawalRequest** | [**CreateWithdrawalRequest**](CreateWithdrawalRequest.md)| create a withdrawal |
 **xImxEthAddress** | **kotlin.String**| eth address | [optional]
 **xImxEthSignature** | **kotlin.String**| eth signature | [optional]

### Return type

[**CreateWithdrawalResponse**](CreateWithdrawalResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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

val apiInstance = PublicApi()
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Address of the ERC721 contract
val tokenId : kotlin.String = tokenId_example // kotlin.String | Either ERC721 token ID or internal IMX ID
val includeFees : kotlin.Boolean = true // kotlin.Boolean | Set flag to include fees associated with the asset
try {
    val result : Asset = apiInstance.getAsset(tokenAddress, tokenId, includeFees)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getAsset")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getAsset")
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

<a name="getBalance"></a>
# **getBalance**
> GetBalanceResponse getBalance(owner, address)

Fetches the token balances of the user

Fetches the token balances of the user

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val owner : kotlin.String = owner_example // kotlin.String | Address of the owner/user
val address : kotlin.String = address_example // kotlin.String | Token address
try {
    val result : GetBalanceResponse = apiInstance.getBalance(owner, address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getBalance")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getBalance")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **owner** | **kotlin.String**| Address of the owner/user |
 **address** | **kotlin.String**| Token address |

### Return type

[**GetBalanceResponse**](GetBalanceResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
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

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
try {
    val result : Collection = apiInstance.getCollection(address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getCollection")
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

<a name="getDeposit"></a>
# **getDeposit**
> Deposit getDeposit(id)

Get details of a deposit with the given ID

Get details of a deposit with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Deposit ID
try {
    val result : Deposit = apiInstance.getDeposit(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getDeposit")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getDeposit")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Deposit ID |

### Return type

[**Deposit**](Deposit.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
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

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
try {
    val result : kotlin.collections.List<MetadataSchemaProperty> = apiInstance.getMetadataSchema(address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getMetadataSchema")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getMetadataSchema")
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

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Mint ID. This is the transaction_id returned from listMints
try {
    val result : Mint = apiInstance.getMint(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getMint")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getMint")
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

val apiInstance = PublicApi()
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Address of the ERC721 contract
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 token ID
try {
    val result : MintableTokenDetails = apiInstance.getMintableTokenDetailsByClientTokenId(tokenAddress, tokenId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getMintableTokenDetailsByClientTokenId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getMintableTokenDetailsByClientTokenId")
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

<a name="getOrder"></a>
# **getOrder**
> Order getOrder(id, includeFees, auxiliaryFeePercentages, auxiliaryFeeRecipients)

Get details of an order with the given ID

Get details of an order with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Order ID
val includeFees : kotlin.Boolean = true // kotlin.Boolean | Set flag to true to include fee body for the order
val auxiliaryFeePercentages : kotlin.String = auxiliaryFeePercentages_example // kotlin.String | Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients
val auxiliaryFeeRecipients : kotlin.String = auxiliaryFeeRecipients_example // kotlin.String | Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages
try {
    val result : Order = apiInstance.getOrder(id, includeFees, auxiliaryFeePercentages, auxiliaryFeeRecipients)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Order ID |
 **includeFees** | **kotlin.Boolean**| Set flag to true to include fee body for the order | [optional]
 **auxiliaryFeePercentages** | **kotlin.String**| Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients | [optional]
 **auxiliaryFeeRecipients** | **kotlin.String**| Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages | [optional]

### Return type

[**Order**](Order.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

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

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Project ID
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
try {
    val result : Project = apiInstance.getProject(id, imXSignature, imXTimestamp)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getProject")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getProject")
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

val apiInstance = PublicApi()
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
    println("4xx response calling PublicApi#getProjects")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getProjects")
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

<a name="getSignableCancelOrder"></a>
# **getSignableCancelOrder**
> GetSignableCancelOrderResponse getSignableCancelOrder(getSignableCancelOrderRequest)

Get details a signable cancel order

Get details a signable cancel order

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val getSignableCancelOrderRequest : GetSignableCancelOrderRequest =  // GetSignableCancelOrderRequest | get a signable cancel order
try {
    val result : GetSignableCancelOrderResponse = apiInstance.getSignableCancelOrder(getSignableCancelOrderRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableCancelOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableCancelOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableCancelOrderRequest** | [**GetSignableCancelOrderRequest**](GetSignableCancelOrderRequest.md)| get a signable cancel order |

### Return type

[**GetSignableCancelOrderResponse**](GetSignableCancelOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSignableDeposit"></a>
# **getSignableDeposit**
> GetSignableDepositResponse getSignableDeposit(getSignableDepositRequest)

Gets details of a signable deposit

Gets details of a signable deposit

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val getSignableDepositRequest : GetSignableDepositRequest =  // GetSignableDepositRequest | Get details of signable deposit
try {
    val result : GetSignableDepositResponse = apiInstance.getSignableDeposit(getSignableDepositRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableDeposit")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableDeposit")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableDepositRequest** | [**GetSignableDepositRequest**](GetSignableDepositRequest.md)| Get details of signable deposit |

### Return type

[**GetSignableDepositResponse**](GetSignableDepositResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSignableOrder"></a>
# **getSignableOrder**
> GetSignableOrderResponse getSignableOrder(getSignableOrderRequestV3)

Get details a signable order V3

Get details a signable order V3

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val getSignableOrderRequestV3 : GetSignableOrderRequest =  // GetSignableOrderRequest | get a signable order
try {
    val result : GetSignableOrderResponse = apiInstance.getSignableOrder(getSignableOrderRequestV3)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableOrder")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableOrder")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableOrderRequestV3** | [**GetSignableOrderRequest**](GetSignableOrderRequest.md)| get a signable order |

### Return type

[**GetSignableOrderResponse**](GetSignableOrderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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

val apiInstance = PublicApi()
val getSignableRegistrationRequest : GetSignableRegistrationRequest =  // GetSignableRegistrationRequest | Register User
try {
    val result : GetSignableRegistrationResponse = apiInstance.getSignableRegistration(getSignableRegistrationRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableRegistration")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableRegistration")
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

<a name="getSignableRegistrationOffchain"></a>
# **getSignableRegistrationOffchain**
> GetSignableRegistrationOffchainResponse getSignableRegistrationOffchain(getSignableRegistrationRequest)

Get encoded details to allow clients to register the user offchain

Get encoded details to allow clients to register the user offchain

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val getSignableRegistrationRequest : GetSignableRegistrationRequest =  // GetSignableRegistrationRequest | Register User Offchain
try {
    val result : GetSignableRegistrationOffchainResponse = apiInstance.getSignableRegistrationOffchain(getSignableRegistrationRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableRegistrationOffchain")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableRegistrationOffchain")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableRegistrationRequest** | [**GetSignableRegistrationRequest**](GetSignableRegistrationRequest.md)| Register User Offchain |

### Return type

[**GetSignableRegistrationOffchainResponse**](GetSignableRegistrationOffchainResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSignableTrade"></a>
# **getSignableTrade**
> GetSignableTradeResponse getSignableTrade(getSignableTradeRequest)

Get details a signable trade V3

Get details a signable trade V3

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val getSignableTradeRequest : GetSignableTradeRequest =  // GetSignableTradeRequest | get a signable trade
try {
    val result : GetSignableTradeResponse = apiInstance.getSignableTrade(getSignableTradeRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableTrade")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableTrade")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableTradeRequest** | [**GetSignableTradeRequest**](GetSignableTradeRequest.md)| get a signable trade |

### Return type

[**GetSignableTradeResponse**](GetSignableTradeResponse.md)

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

val apiInstance = PublicApi()
val getSignableTransferRequestV2 : GetSignableTransferRequest =  // GetSignableTransferRequest | get details of signable transfer
try {
    val result : GetSignableTransferResponse = apiInstance.getSignableTransfer(getSignableTransferRequestV2)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableTransfer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableTransfer")
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

val apiInstance = PublicApi()
val getSignableTransferRequest : GetSignableTransferRequestV1 =  // GetSignableTransferRequestV1 | get details of signable transfer
try {
    val result : GetSignableTransferResponseV1 = apiInstance.getSignableTransferV1(getSignableTransferRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableTransferV1")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableTransferV1")
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

<a name="getSignableWithdrawal"></a>
# **getSignableWithdrawal**
> GetSignableWithdrawalResponse getSignableWithdrawal(getSignableWithdrawalRequest)

Gets details of a signable withdrawal

Gets details of a signable withdrawal

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val getSignableWithdrawalRequest : GetSignableWithdrawalRequest =  // GetSignableWithdrawalRequest | get details of signable withdrawal
try {
    val result : GetSignableWithdrawalResponse = apiInstance.getSignableWithdrawal(getSignableWithdrawalRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getSignableWithdrawal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getSignableWithdrawal")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **getSignableWithdrawalRequest** | [**GetSignableWithdrawalRequest**](GetSignableWithdrawalRequest.md)| get details of signable withdrawal |

### Return type

[**GetSignableWithdrawalResponse**](GetSignableWithdrawalResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getToken"></a>
# **getToken**
> TokenDetails getToken(address)

Get details of a token

Get details of a token

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Token Contract Address
try {
    val result : TokenDetails = apiInstance.getToken(address)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Token Contract Address |

### Return type

[**TokenDetails**](TokenDetails.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTrade"></a>
# **getTrade**
> Trade getTrade(id)

Get details of a trade with the given ID

Get details of a trade with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Trade ID
try {
    val result : Trade = apiInstance.getTrade(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getTrade")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getTrade")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Trade ID |

### Return type

[**Trade**](Trade.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
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

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Transfer ID
try {
    val result : Transfer = apiInstance.getTransfer(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getTransfer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getTransfer")
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

val apiInstance = PublicApi()
val user : kotlin.String = user_example // kotlin.String | User
try {
    val result : GetUsersApiResponse = apiInstance.getUsers(user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getUsers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getUsers")
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

<a name="getWithdrawal"></a>
# **getWithdrawal**
> Withdrawal getWithdrawal(id)

Gets details of withdrawal with the given ID

Gets details of withdrawal with the given ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val id : kotlin.String = id_example // kotlin.String | Withdrawal ID
try {
    val result : Withdrawal = apiInstance.getWithdrawal(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#getWithdrawal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#getWithdrawal")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**| Withdrawal ID |

### Return type

[**Withdrawal**](Withdrawal.md)

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

val apiInstance = PublicApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who owns these assets
val status : kotlin.String = status_example // kotlin.String | Status of these assets
val name : kotlin.String = name_example // kotlin.String | Name of the asset to search
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for these asset. Example: {
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
    println("4xx response calling PublicApi#listAssets")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listAssets")
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
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for these asset. Example: { | [optional]
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

val apiInstance = PublicApi()
val owner : kotlin.String = owner_example // kotlin.String | Ethereum wallet address for user
try {
    val result : ListBalancesResponse = apiInstance.listBalances(owner)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listBalances")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listBalances")
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

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val nextPageToken : kotlin.String = nextPageToken_example // kotlin.String | Next page token
try {
    val result : CollectionFilter = apiInstance.listCollectionFilters(address, pageSize, nextPageToken)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listCollectionFilters")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listCollectionFilters")
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
> ListCollectionsResponse listCollections(pageSize, cursor, orderBy, direction, blacklist)

Get a list of collections

Get a list of collections

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val blacklist : kotlin.String = blacklist_example // kotlin.String | List of collections not to be displayed, separated by commas
try {
    val result : ListCollectionsResponse = apiInstance.listCollections(pageSize, cursor, orderBy, direction, blacklist)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listCollections")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listCollections")
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
 **blacklist** | **kotlin.String**| List of collections not to be displayed, separated by commas | [optional]

### Return type

[**ListCollectionsResponse**](ListCollectionsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listDeposits"></a>
# **listDeposits**
> ListDepositsResponse listDeposits(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)

Get a list of deposits

Get a list of deposits

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this deposit
val status : kotlin.String = status_example // kotlin.String | Status of this deposit
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum timestamp for this deposit
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum timestamp for this deposit
val tokenType : kotlin.String = tokenType_example // kotlin.String | Token type of the deposited asset
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 Token ID of the minted asset
val assetId : kotlin.String = assetId_example // kotlin.String | Internal IMX ID of the minted asset
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Token address of the deposited asset
val tokenName : kotlin.String = tokenName_example // kotlin.String | Token name of the deposited asset
val minQuantity : kotlin.String = minQuantity_example // kotlin.String | Min quantity for the deposited asset
val maxQuantity : kotlin.String = maxQuantity_example // kotlin.String | Max quantity for the deposited asset
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for the deposited asset
try {
    val result : ListDepositsResponse = apiInstance.listDeposits(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listDeposits")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listDeposits")
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
 **user** | **kotlin.String**| Ethereum address of the user who submitted this deposit | [optional]
 **status** | **kotlin.String**| Status of this deposit | [optional]
 **minTimestamp** | **kotlin.String**| Minimum timestamp for this deposit | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum timestamp for this deposit | [optional]
 **tokenType** | **kotlin.String**| Token type of the deposited asset | [optional]
 **tokenId** | **kotlin.String**| ERC721 Token ID of the minted asset | [optional]
 **assetId** | **kotlin.String**| Internal IMX ID of the minted asset | [optional]
 **tokenAddress** | **kotlin.String**| Token address of the deposited asset | [optional]
 **tokenName** | **kotlin.String**| Token name of the deposited asset | [optional]
 **minQuantity** | **kotlin.String**| Min quantity for the deposited asset | [optional]
 **maxQuantity** | **kotlin.String**| Max quantity for the deposited asset | [optional]
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for the deposited asset | [optional]

### Return type

[**ListDepositsResponse**](ListDepositsResponse.md)

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

val apiInstance = PublicApi()
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
    println("4xx response calling PublicApi#listMints")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listMints")
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

<a name="listOrders"></a>
# **listOrders**
> ListOrdersResponse listOrders(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, updatedMinTimestamp, updatedMaxTimestamp, buyTokenType, buyTokenId, buyAssetId, buyTokenAddress, buyTokenName, buyMinQuantity, buyMaxQuantity, buyMetadata, sellTokenType, sellTokenId, sellAssetId, sellTokenAddress, sellTokenName, sellMinQuantity, sellMaxQuantity, sellMetadata, auxiliaryFeePercentages, auxiliaryFeeRecipients)

Get a list of orders

Get a list of orders

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this order
val status : kotlin.String = status_example // kotlin.String | Status of this order
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum created at timestamp for this order
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum created at timestamp for this order
val updatedMinTimestamp : kotlin.String = updatedMinTimestamp_example // kotlin.String | Minimum updated at timestamp for this order
val updatedMaxTimestamp : kotlin.String = updatedMaxTimestamp_example // kotlin.String | Maximum updated at timestamp for this order
val buyTokenType : kotlin.String = buyTokenType_example // kotlin.String | Token type of the asset this order buys
val buyTokenId : kotlin.String = buyTokenId_example // kotlin.String | ERC721 Token ID of the asset this order buys
val buyAssetId : kotlin.String = buyAssetId_example // kotlin.String | Internal IMX ID of the asset this order buys
val buyTokenAddress : kotlin.String = buyTokenAddress_example // kotlin.String | Comma separated string of token addresses of the asset this order buys
val buyTokenName : kotlin.String = buyTokenName_example // kotlin.String | Token name of the asset this order buys
val buyMinQuantity : kotlin.String = buyMinQuantity_example // kotlin.String | Min quantity for the asset this order buys
val buyMaxQuantity : kotlin.String = buyMaxQuantity_example // kotlin.String | Max quantity for the asset this order buys
val buyMetadata : kotlin.String = buyMetadata_example // kotlin.String | JSON-encoded metadata filters for the asset this order buys
val sellTokenType : kotlin.String = sellTokenType_example // kotlin.String | Token type of the asset this order sells
val sellTokenId : kotlin.String = sellTokenId_example // kotlin.String | ERC721 Token ID of the asset this order sells
val sellAssetId : kotlin.String = sellAssetId_example // kotlin.String | Internal IMX ID of the asset this order sells
val sellTokenAddress : kotlin.String = sellTokenAddress_example // kotlin.String | Comma separated string of token addresses of the asset this order sells
val sellTokenName : kotlin.String = sellTokenName_example // kotlin.String | Token name of the asset this order sells
val sellMinQuantity : kotlin.String = sellMinQuantity_example // kotlin.String | Min quantity for the asset this order sells
val sellMaxQuantity : kotlin.String = sellMaxQuantity_example // kotlin.String | Max quantity for the asset this order sells
val sellMetadata : kotlin.String = sellMetadata_example // kotlin.String | JSON-encoded metadata filters for the asset this order sells
val auxiliaryFeePercentages : kotlin.String = auxiliaryFeePercentages_example // kotlin.String | Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients
val auxiliaryFeeRecipients : kotlin.String = auxiliaryFeeRecipients_example // kotlin.String | Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages
try {
    val result : ListOrdersResponse = apiInstance.listOrders(pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, updatedMinTimestamp, updatedMaxTimestamp, buyTokenType, buyTokenId, buyAssetId, buyTokenAddress, buyTokenName, buyMinQuantity, buyMaxQuantity, buyMetadata, sellTokenType, sellTokenId, sellAssetId, sellTokenAddress, sellTokenName, sellMinQuantity, sellMaxQuantity, sellMetadata, auxiliaryFeePercentages, auxiliaryFeeRecipients)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listOrders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listOrders")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional] [enum: created_at, expired_at, sell_quantity, buy_quantity, buy_quantity_with_fees, updated_at]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **user** | **kotlin.String**| Ethereum address of the user who submitted this order | [optional]
 **status** | **kotlin.String**| Status of this order | [optional] [enum: active, filled, cancelled, expired, inactive]
 **minTimestamp** | **kotlin.String**| Minimum created at timestamp for this order | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum created at timestamp for this order | [optional]
 **updatedMinTimestamp** | **kotlin.String**| Minimum updated at timestamp for this order | [optional]
 **updatedMaxTimestamp** | **kotlin.String**| Maximum updated at timestamp for this order | [optional]
 **buyTokenType** | **kotlin.String**| Token type of the asset this order buys | [optional]
 **buyTokenId** | **kotlin.String**| ERC721 Token ID of the asset this order buys | [optional]
 **buyAssetId** | **kotlin.String**| Internal IMX ID of the asset this order buys | [optional]
 **buyTokenAddress** | **kotlin.String**| Comma separated string of token addresses of the asset this order buys | [optional]
 **buyTokenName** | **kotlin.String**| Token name of the asset this order buys | [optional]
 **buyMinQuantity** | **kotlin.String**| Min quantity for the asset this order buys | [optional]
 **buyMaxQuantity** | **kotlin.String**| Max quantity for the asset this order buys | [optional]
 **buyMetadata** | **kotlin.String**| JSON-encoded metadata filters for the asset this order buys | [optional]
 **sellTokenType** | **kotlin.String**| Token type of the asset this order sells | [optional]
 **sellTokenId** | **kotlin.String**| ERC721 Token ID of the asset this order sells | [optional]
 **sellAssetId** | **kotlin.String**| Internal IMX ID of the asset this order sells | [optional]
 **sellTokenAddress** | **kotlin.String**| Comma separated string of token addresses of the asset this order sells | [optional]
 **sellTokenName** | **kotlin.String**| Token name of the asset this order sells | [optional]
 **sellMinQuantity** | **kotlin.String**| Min quantity for the asset this order sells | [optional]
 **sellMaxQuantity** | **kotlin.String**| Max quantity for the asset this order sells | [optional]
 **sellMetadata** | **kotlin.String**| JSON-encoded metadata filters for the asset this order sells | [optional]
 **auxiliaryFeePercentages** | **kotlin.String**| Comma separated string of fee percentages that are to be paired with auxiliary_fee_recipients | [optional]
 **auxiliaryFeeRecipients** | **kotlin.String**| Comma separated string of fee recipients that are to be paired with auxiliary_fee_percentages | [optional]

### Return type

[**ListOrdersResponse**](ListOrdersResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="listTokens"></a>
# **listTokens**
> ListTokensResponse listTokens(address, symbols)

Get a list of tokens

Get a list of tokens

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Contract address of the token
val symbols : kotlin.String = symbols_example // kotlin.String | Token symbols for the token, e.g. ?symbols=IMX,ETH
try {
    val result : ListTokensResponse = apiInstance.listTokens(address, symbols)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listTokens")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listTokens")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **address** | **kotlin.String**| Contract address of the token | [optional]
 **symbols** | **kotlin.String**| Token symbols for the token, e.g. ?symbols&#x3D;IMX,ETH | [optional]

### Return type

[**ListTokensResponse**](ListTokensResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listTrades"></a>
# **listTrades**
> ListTradesResponse listTrades(partyATokenType, partyATokenAddress, partyATokenId, partyBTokenType, partyBTokenAddress, partyBTokenId, pageSize, cursor, orderBy, direction, minTimestamp, maxTimestamp)

Get a list of trades

Get a list of trades

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val partyATokenType : kotlin.String = partyATokenType_example // kotlin.String | Party A's sell token type
val partyATokenAddress : kotlin.String = partyATokenAddress_example // kotlin.String | Party A's sell token address
val partyATokenId : kotlin.String = partyATokenId_example // kotlin.String | Party A's sell token id
val partyBTokenType : kotlin.String = partyBTokenType_example // kotlin.String | Party B's sell token type
val partyBTokenAddress : kotlin.String = partyBTokenAddress_example // kotlin.String | Party B's sell token address
val partyBTokenId : kotlin.String = partyBTokenId_example // kotlin.String | Party B's sell token id
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum timestamp for this trade
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum timestamp for this trade
try {
    val result : ListTradesResponse = apiInstance.listTrades(partyATokenType, partyATokenAddress, partyATokenId, partyBTokenType, partyBTokenAddress, partyBTokenId, pageSize, cursor, orderBy, direction, minTimestamp, maxTimestamp)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listTrades")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listTrades")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **partyATokenType** | **kotlin.String**| Party A&#39;s sell token type | [optional]
 **partyATokenAddress** | **kotlin.String**| Party A&#39;s sell token address | [optional]
 **partyATokenId** | **kotlin.String**| Party A&#39;s sell token id | [optional]
 **partyBTokenType** | **kotlin.String**| Party B&#39;s sell token type | [optional]
 **partyBTokenAddress** | **kotlin.String**| Party B&#39;s sell token address | [optional]
 **partyBTokenId** | **kotlin.String**| Party B&#39;s sell token id | [optional]
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **minTimestamp** | **kotlin.String**| Minimum timestamp for this trade | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum timestamp for this trade | [optional]

### Return type

[**ListTradesResponse**](ListTradesResponse.md)

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

val apiInstance = PublicApi()
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
    println("4xx response calling PublicApi#listTransfers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listTransfers")
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

<a name="listWithdrawals"></a>
# **listWithdrawals**
> ListWithdrawalsResponse listWithdrawals(withdrawnToWallet, rollupStatus, pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)

Get a list of withdrawals

Get a list of withdrawals

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val withdrawnToWallet : kotlin.Boolean = true // kotlin.Boolean | Withdrawal has been transferred to user's Layer 1 wallet
val rollupStatus : kotlin.String = rollupStatus_example // kotlin.String | Status of the on-chain batch confirmation for this withdrawal
val pageSize : kotlin.Int = 56 // kotlin.Int | Page size of the result
val cursor : kotlin.String = cursor_example // kotlin.String | Cursor
val orderBy : kotlin.String = orderBy_example // kotlin.String | Property to sort by
val direction : kotlin.String = direction_example // kotlin.String | Direction to sort (asc/desc)
val user : kotlin.String = user_example // kotlin.String | Ethereum address of the user who submitted this withdrawal
val status : kotlin.String = status_example // kotlin.String | Status of this withdrawal
val minTimestamp : kotlin.String = minTimestamp_example // kotlin.String | Minimum timestamp for this deposit
val maxTimestamp : kotlin.String = maxTimestamp_example // kotlin.String | Maximum timestamp for this deposit
val tokenType : kotlin.String = tokenType_example // kotlin.String | Token type of the withdrawn asset
val tokenId : kotlin.String = tokenId_example // kotlin.String | ERC721 Token ID of the minted asset
val assetId : kotlin.String = assetId_example // kotlin.String | Internal IMX ID of the minted asset
val tokenAddress : kotlin.String = tokenAddress_example // kotlin.String | Token address of the withdrawn asset
val tokenName : kotlin.String = tokenName_example // kotlin.String | Token name of the withdrawn asset
val minQuantity : kotlin.String = minQuantity_example // kotlin.String | Min quantity for the withdrawn asset
val maxQuantity : kotlin.String = maxQuantity_example // kotlin.String | Max quantity for the withdrawn asset
val metadata : kotlin.String = metadata_example // kotlin.String | JSON-encoded metadata filters for the withdrawn asset
try {
    val result : ListWithdrawalsResponse = apiInstance.listWithdrawals(withdrawnToWallet, rollupStatus, pageSize, cursor, orderBy, direction, user, status, minTimestamp, maxTimestamp, tokenType, tokenId, assetId, tokenAddress, tokenName, minQuantity, maxQuantity, metadata)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#listWithdrawals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#listWithdrawals")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **withdrawnToWallet** | **kotlin.Boolean**| Withdrawal has been transferred to user&#39;s Layer 1 wallet | [optional]
 **rollupStatus** | **kotlin.String**| Status of the on-chain batch confirmation for this withdrawal | [optional]
 **pageSize** | **kotlin.Int**| Page size of the result | [optional]
 **cursor** | **kotlin.String**| Cursor | [optional]
 **orderBy** | **kotlin.String**| Property to sort by | [optional]
 **direction** | **kotlin.String**| Direction to sort (asc/desc) | [optional]
 **user** | **kotlin.String**| Ethereum address of the user who submitted this withdrawal | [optional]
 **status** | **kotlin.String**| Status of this withdrawal | [optional]
 **minTimestamp** | **kotlin.String**| Minimum timestamp for this deposit | [optional]
 **maxTimestamp** | **kotlin.String**| Maximum timestamp for this deposit | [optional]
 **tokenType** | **kotlin.String**| Token type of the withdrawn asset | [optional]
 **tokenId** | **kotlin.String**| ERC721 Token ID of the minted asset | [optional]
 **assetId** | **kotlin.String**| Internal IMX ID of the minted asset | [optional]
 **tokenAddress** | **kotlin.String**| Token address of the withdrawn asset | [optional]
 **tokenName** | **kotlin.String**| Token name of the withdrawn asset | [optional]
 **minQuantity** | **kotlin.String**| Min quantity for the withdrawn asset | [optional]
 **maxQuantity** | **kotlin.String**| Max quantity for the withdrawn asset | [optional]
 **metadata** | **kotlin.String**| JSON-encoded metadata filters for the withdrawn asset | [optional]

### Return type

[**ListWithdrawalsResponse**](ListWithdrawalsResponse.md)

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

val apiInstance = PublicApi()
val mintTokensRequestV2 : kotlin.collections.List<MintRequest> =  // kotlin.collections.List<MintRequest> | details of tokens to mint
try {
    val result : MintTokensResponse = apiInstance.mintTokens(mintTokensRequestV2)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#mintTokens")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#mintTokens")
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

<a name="registerUser"></a>
# **registerUser**
> RegisterUserResponse registerUser(registerUserRequest)

Registers a user

Registers a user

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.immutable.sdk.api.model.*

val apiInstance = PublicApi()
val registerUserRequest : RegisterUserRequest =  // RegisterUserRequest | Register User
try {
    val result : RegisterUserResponse = apiInstance.registerUser(registerUserRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#registerUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#registerUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **registerUserRequest** | [**RegisterUserRequest**](RegisterUserRequest.md)| Register User |

### Return type

[**RegisterUserResponse**](RegisterUserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val updateCollectionRequest : UpdateCollectionRequest =  // UpdateCollectionRequest | update a collection
try {
    val result : Collection = apiInstance.updateCollection(address, imXSignature, imXTimestamp, updateCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#updateCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#updateCollection")
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

val apiInstance = PublicApi()
val address : kotlin.String = address_example // kotlin.String | Collection contract address
val name : kotlin.String = name_example // kotlin.String | Metadata schema name
val imXSignature : kotlin.String = imXSignature_example // kotlin.String | String created by signing wallet address and timestamp
val imXTimestamp : kotlin.String = imXTimestamp_example // kotlin.String | Unix Epoc timestamp
val metadataSchemaRequest : MetadataSchemaRequest =  // MetadataSchemaRequest | update metadata schema
try {
    val result : SuccessResponse = apiInstance.updateMetadataSchemaByName(address, name, imXSignature, imXTimestamp, metadataSchemaRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublicApi#updateMetadataSchemaByName")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublicApi#updateMetadataSchemaByName")
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

