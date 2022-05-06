# org.openapitools.client - Kotlin client library for Immutable X API

## Requires

* Kotlin 1.4.30
* Gradle 6.8.3

## Build

First, create the gradle wrapper script:

```
gradle wrapper
```

Then, run:

```
./gradlew check assemble
```

This runs all tests and packages the library.

## Features/Implementation Notes

* Supports JSON inputs/outputs, File inputs, and Form inputs.
* Supports collection formats for query parameters: csv, tsv, ssv, pipes.
* Some Kotlin and Java types are fully qualified to avoid conflicts with types defined in OpenAPI definitions.
* Implementation of ApiClient is intended to reduce method counts, specifically to benefit Android targets.

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *https://api.ropsten.x.immutable.com*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*ApplicationsApi* | [**getApplication**](docs/ApplicationsApi.md#getapplication) | **GET** /v1/applications/{id} | Get details of an application with the given ID
*ApplicationsApi* | [**listApplications**](docs/ApplicationsApi.md#listapplications) | **GET** /v1/applications | Get a list of applications
*AssetsApi* | [**getAsset**](docs/AssetsApi.md#getasset) | **GET** /v1/assets/{token_address}/{token_id} | Get details of an asset
*AssetsApi* | [**listAssets**](docs/AssetsApi.md#listassets) | **GET** /v1/assets | Get a list of assets
*BalancesApi* | [**getBalance**](docs/BalancesApi.md#getbalance) | **GET** /v2/balances/{owner}/{address} | Fetches the token balances of the user
*BalancesApi* | [**getBalanceOfUser**](docs/BalancesApi.md#getbalanceofuser) | **GET** /v1/balances/{owner} | Fetches the WEI balances of the user
*BalancesApi* | [**listBalances**](docs/BalancesApi.md#listbalances) | **GET** /v2/balances/{owner} | Get a list of balances for given user
*CollectionsApi* | [**createCollection**](docs/CollectionsApi.md#createcollection) | **POST** /v1/collections | Create collection
*CollectionsApi* | [**getCollection**](docs/CollectionsApi.md#getcollection) | **GET** /v1/collections/{address} | Get details of a collection at the given address
*CollectionsApi* | [**listCollectionFilters**](docs/CollectionsApi.md#listcollectionfilters) | **GET** /v1/collections/{address}/filters | Get a list of collection filters
*CollectionsApi* | [**listCollections**](docs/CollectionsApi.md#listcollections) | **GET** /v1/collections | Get a list of collections
*CollectionsApi* | [**updateCollection**](docs/CollectionsApi.md#updatecollection) | **PATCH** /v1/collections/{address} | Update collection
*DepositsApi* | [**getDeposit**](docs/DepositsApi.md#getdeposit) | **GET** /v1/deposits/{id} | Get details of a deposit with the given ID
*DepositsApi* | [**getSignableDeposit**](docs/DepositsApi.md#getsignabledeposit) | **POST** /v1/signable-deposit-details | Gets details of a signable deposit
*DepositsApi* | [**listDeposits**](docs/DepositsApi.md#listdeposits) | **GET** /v1/deposits | Get a list of deposits
*MetadataApi* | [**addMetadataSchemaToCollection**](docs/MetadataApi.md#addmetadataschematocollection) | **POST** /v1/collections/{address}/metadata-schema | Add metadata schema to collection
*MetadataApi* | [**getMetadataSchema**](docs/MetadataApi.md#getmetadataschema) | **GET** /v1/collections/{address}/metadata-schema | Get collection metadata schema
*MetadataApi* | [**updateMetadataSchemaByName**](docs/MetadataApi.md#updatemetadataschemabyname) | **PATCH** /v1/collections/{address}/metadata-schema/{name} | Update metadata schema by name
*MintsApi* | [**getMint**](docs/MintsApi.md#getmint) | **GET** /v1/mints/{id} | Get details of a mint with the given ID
*MintsApi* | [**getMintableTokenDetails**](docs/MintsApi.md#getmintabletokendetails) | **GET** /v1/mintable-token/{id} | Get details of a mintable token with the given IMX token ID
*MintsApi* | [**getMintableTokenDetailsByClientTokenId**](docs/MintsApi.md#getmintabletokendetailsbyclienttokenid) | **GET** /v1/mintable-token/{token_address}/{token_id} | Get details of a mintable token with the given token address and token ID
*MintsApi* | [**listMints**](docs/MintsApi.md#listmints) | **GET** /v1/mints | Get a list of mints
*MintsApi* | [**mintTokens**](docs/MintsApi.md#minttokens) | **POST** /v2/mints | Mint Tokens V2
*MintsApi* | [**mintTokensV1**](docs/MintsApi.md#minttokensv1) | **POST** /v1/mints | Mint tokens
*OrdersApi* | [**cancelOrder**](docs/OrdersApi.md#cancelorder) | **DELETE** /v1/orders/{id} | cancel an order
*OrdersApi* | [**createOrder**](docs/OrdersApi.md#createorder) | **POST** /v1/orders | Create an order
*OrdersApi* | [**getOrder**](docs/OrdersApi.md#getorder) | **GET** /v1/orders/{id} | Get details of an order with the given ID
*OrdersApi* | [**getSignableCancelOrder**](docs/OrdersApi.md#getsignablecancelorder) | **POST** /v1/signable-cancel-order-details | Get details a signable cancel order
*OrdersApi* | [**getSignableOrder**](docs/OrdersApi.md#getsignableorder) | **POST** /v3/signable-order-details | Get details a signable order V3
*OrdersApi* | [**getSignableOrderV1**](docs/OrdersApi.md#getsignableorderv1) | **POST** /v1/signable-order-details | Get details a signable order
*OrdersApi* | [**listOrders**](docs/OrdersApi.md#listorders) | **GET** /v1/orders | Get a list of orders
*ProjectsApi* | [**createProject**](docs/ProjectsApi.md#createproject) | **POST** /v1/projects | Create a project
*ProjectsApi* | [**getProject**](docs/ProjectsApi.md#getproject) | **GET** /v1/projects/{id} | Get a project
*ProjectsApi* | [**getProjects**](docs/ProjectsApi.md#getprojects) | **GET** /v1/projects | Get projects
*PublicApi* | [**addMetadataSchemaToCollection**](docs/PublicApi.md#addmetadataschematocollection) | **POST** /v1/collections/{address}/metadata-schema | Add metadata schema to collection
*PublicApi* | [**cancelOrder**](docs/PublicApi.md#cancelorder) | **DELETE** /v1/orders/{id} | cancel an order
*PublicApi* | [**createCollection**](docs/PublicApi.md#createcollection) | **POST** /v1/collections | Create collection
*PublicApi* | [**createOrder**](docs/PublicApi.md#createorder) | **POST** /v1/orders | Create an order
*PublicApi* | [**createProject**](docs/PublicApi.md#createproject) | **POST** /v1/projects | Create a project
*PublicApi* | [**createTrade**](docs/PublicApi.md#createtrade) | **POST** /v1/trades | Create a Trade between two parties
*PublicApi* | [**createTransfer**](docs/PublicApi.md#createtransfer) | **POST** /v2/transfers | Creates a transfer of multiple tokens between two parties
*PublicApi* | [**createTransferV1**](docs/PublicApi.md#createtransferv1) | **POST** /v1/transfers | Creates a transfer of tokens between two parties
*PublicApi* | [**createWithdrawal**](docs/PublicApi.md#createwithdrawal) | **POST** /v1/withdrawals | Creates a withdrawal of a token
*PublicApi* | [**getAsset**](docs/PublicApi.md#getasset) | **GET** /v1/assets/{token_address}/{token_id} | Get details of an asset
*PublicApi* | [**getBalance**](docs/PublicApi.md#getbalance) | **GET** /v2/balances/{owner}/{address} | Fetches the token balances of the user
*PublicApi* | [**getCollection**](docs/PublicApi.md#getcollection) | **GET** /v1/collections/{address} | Get details of a collection at the given address
*PublicApi* | [**getDeposit**](docs/PublicApi.md#getdeposit) | **GET** /v1/deposits/{id} | Get details of a deposit with the given ID
*PublicApi* | [**getMetadataSchema**](docs/PublicApi.md#getmetadataschema) | **GET** /v1/collections/{address}/metadata-schema | Get collection metadata schema
*PublicApi* | [**getMint**](docs/PublicApi.md#getmint) | **GET** /v1/mints/{id} | Get details of a mint with the given ID
*PublicApi* | [**getMintableTokenDetailsByClientTokenId**](docs/PublicApi.md#getmintabletokendetailsbyclienttokenid) | **GET** /v1/mintable-token/{token_address}/{token_id} | Get details of a mintable token with the given token address and token ID
*PublicApi* | [**getOrder**](docs/PublicApi.md#getorder) | **GET** /v1/orders/{id} | Get details of an order with the given ID
*PublicApi* | [**getProject**](docs/PublicApi.md#getproject) | **GET** /v1/projects/{id} | Get a project
*PublicApi* | [**getProjects**](docs/PublicApi.md#getprojects) | **GET** /v1/projects | Get projects
*PublicApi* | [**getSignableCancelOrder**](docs/PublicApi.md#getsignablecancelorder) | **POST** /v1/signable-cancel-order-details | Get details a signable cancel order
*PublicApi* | [**getSignableDeposit**](docs/PublicApi.md#getsignabledeposit) | **POST** /v1/signable-deposit-details | Gets details of a signable deposit
*PublicApi* | [**getSignableOrder**](docs/PublicApi.md#getsignableorder) | **POST** /v3/signable-order-details | Get details a signable order V3
*PublicApi* | [**getSignableRegistration**](docs/PublicApi.md#getsignableregistration) | **POST** /v1/signable-registration | Get operator signature to allow clients to register the user
*PublicApi* | [**getSignableRegistrationOffchain**](docs/PublicApi.md#getsignableregistrationoffchain) | **POST** /v1/signable-registration-offchain | Get encoded details to allow clients to register the user offchain
*PublicApi* | [**getSignableTrade**](docs/PublicApi.md#getsignabletrade) | **POST** /v3/signable-trade-details | Get details a signable trade V3
*PublicApi* | [**getSignableTransfer**](docs/PublicApi.md#getsignabletransfer) | **POST** /v2/signable-transfer-details | Gets bulk details of a signable transfer
*PublicApi* | [**getSignableTransferV1**](docs/PublicApi.md#getsignabletransferv1) | **POST** /v1/signable-transfer-details | Gets details of a signable transfer
*PublicApi* | [**getSignableWithdrawal**](docs/PublicApi.md#getsignablewithdrawal) | **POST** /v1/signable-withdrawal-details | Gets details of a signable withdrawal
*PublicApi* | [**getToken**](docs/PublicApi.md#gettoken) | **GET** /v1/tokens/{address} | Get details of a token
*PublicApi* | [**getTrade**](docs/PublicApi.md#gettrade) | **GET** /v1/trades/{id} | Get details of a trade with the given ID
*PublicApi* | [**getTransfer**](docs/PublicApi.md#gettransfer) | **GET** /v1/transfers/{id} | Get details of a transfer with the given ID
*PublicApi* | [**getUsers**](docs/PublicApi.md#getusers) | **GET** /v1/users/{user} | Get stark keys for a registered user
*PublicApi* | [**getWithdrawal**](docs/PublicApi.md#getwithdrawal) | **GET** /v1/withdrawals/{id} | Gets details of withdrawal with the given ID
*PublicApi* | [**listAssets**](docs/PublicApi.md#listassets) | **GET** /v1/assets | Get a list of assets
*PublicApi* | [**listBalances**](docs/PublicApi.md#listbalances) | **GET** /v2/balances/{owner} | Get a list of balances for given user
*PublicApi* | [**listCollectionFilters**](docs/PublicApi.md#listcollectionfilters) | **GET** /v1/collections/{address}/filters | Get a list of collection filters
*PublicApi* | [**listCollections**](docs/PublicApi.md#listcollections) | **GET** /v1/collections | Get a list of collections
*PublicApi* | [**listDeposits**](docs/PublicApi.md#listdeposits) | **GET** /v1/deposits | Get a list of deposits
*PublicApi* | [**listMints**](docs/PublicApi.md#listmints) | **GET** /v1/mints | Get a list of mints
*PublicApi* | [**listOrders**](docs/PublicApi.md#listorders) | **GET** /v1/orders | Get a list of orders
*PublicApi* | [**listTokens**](docs/PublicApi.md#listtokens) | **GET** /v1/tokens | Get a list of tokens
*PublicApi* | [**listTrades**](docs/PublicApi.md#listtrades) | **GET** /v1/trades | Get a list of trades
*PublicApi* | [**listTransfers**](docs/PublicApi.md#listtransfers) | **GET** /v1/transfers | Get a list of transfers
*PublicApi* | [**listWithdrawals**](docs/PublicApi.md#listwithdrawals) | **GET** /v1/withdrawals | Get a list of withdrawals
*PublicApi* | [**mintTokens**](docs/PublicApi.md#minttokens) | **POST** /v2/mints | Mint Tokens V2
*PublicApi* | [**registerUser**](docs/PublicApi.md#registeruser) | **POST** /v1/users | Registers a user
*PublicApi* | [**updateCollection**](docs/PublicApi.md#updatecollection) | **PATCH** /v1/collections/{address} | Update collection
*PublicApi* | [**updateMetadataSchemaByName**](docs/PublicApi.md#updatemetadataschemabyname) | **PATCH** /v1/collections/{address}/metadata-schema/{name} | Update metadata schema by name
*SnapshotApi* | [**listSnapshotBalances**](docs/SnapshotApi.md#listsnapshotbalances) | **POST** /v1/snapshot/balances/{tokenAddress} | Get a snapshot at a specific block
*TlvsApi* | [**getTLVs**](docs/TlvsApi.md#gettlvs) | **GET** /v1/claims/{etherKey}/{tokenAddress} | Get TLV information for a user for a token
*TokensApi* | [**getToken**](docs/TokensApi.md#gettoken) | **GET** /v1/tokens/{address} | Get details of a token
*TokensApi* | [**listTokens**](docs/TokensApi.md#listtokens) | **GET** /v1/tokens | Get a list of tokens
*TradesApi* | [**createTrade**](docs/TradesApi.md#createtrade) | **POST** /v1/trades | Create a Trade between two parties
*TradesApi* | [**getSignableTrade**](docs/TradesApi.md#getsignabletrade) | **POST** /v3/signable-trade-details | Get details a signable trade V3
*TradesApi* | [**getTrade**](docs/TradesApi.md#gettrade) | **GET** /v1/trades/{id} | Get details of a trade with the given ID
*TradesApi* | [**listTrades**](docs/TradesApi.md#listtrades) | **GET** /v1/trades | Get a list of trades
*TransfersApi* | [**createTransfer**](docs/TransfersApi.md#createtransfer) | **POST** /v2/transfers | Creates a transfer of multiple tokens between two parties
*TransfersApi* | [**createTransferV1**](docs/TransfersApi.md#createtransferv1) | **POST** /v1/transfers | Creates a transfer of tokens between two parties
*TransfersApi* | [**getSignableTransfer**](docs/TransfersApi.md#getsignabletransfer) | **POST** /v2/signable-transfer-details | Gets bulk details of a signable transfer
*TransfersApi* | [**getSignableTransferV1**](docs/TransfersApi.md#getsignabletransferv1) | **POST** /v1/signable-transfer-details | Gets details of a signable transfer
*TransfersApi* | [**getTransfer**](docs/TransfersApi.md#gettransfer) | **GET** /v1/transfers/{id} | Get details of a transfer with the given ID
*TransfersApi* | [**listTransfers**](docs/TransfersApi.md#listtransfers) | **GET** /v1/transfers | Get a list of transfers
*UsersApi* | [**getSignableRegistration**](docs/UsersApi.md#getsignableregistration) | **POST** /v1/signable-registration | Get operator signature to allow clients to register the user
*UsersApi* | [**getSignableRegistrationOffchain**](docs/UsersApi.md#getsignableregistrationoffchain) | **POST** /v1/signable-registration-offchain | Get encoded details to allow clients to register the user offchain
*UsersApi* | [**getUsers**](docs/UsersApi.md#getusers) | **GET** /v1/users/{user} | Get stark keys for a registered user
*UsersApi* | [**registerUser**](docs/UsersApi.md#registeruser) | **POST** /v1/users | Registers a user
*WithdrawalsApi* | [**createWithdrawal**](docs/WithdrawalsApi.md#createwithdrawal) | **POST** /v1/withdrawals | Creates a withdrawal of a token
*WithdrawalsApi* | [**getSignableWithdrawal**](docs/WithdrawalsApi.md#getsignablewithdrawal) | **POST** /v1/signable-withdrawal-details | Gets details of a signable withdrawal
*WithdrawalsApi* | [**getWithdrawal**](docs/WithdrawalsApi.md#getwithdrawal) | **GET** /v1/withdrawals/{id} | Gets details of withdrawal with the given ID
*WithdrawalsApi* | [**listWithdrawals**](docs/WithdrawalsApi.md#listwithdrawals) | **GET** /v1/withdrawals | Get a list of withdrawals


<a name="documentation-for-models"></a>
## Documentation for Models

 - [com.immutable.sdk.api.model.AddMetadataSchemaToCollectionRequest](docs/AddMetadataSchemaToCollectionRequest.md)
 - [com.immutable.sdk.api.model.Application](docs/Application.md)
 - [com.immutable.sdk.api.model.Asset](docs/Asset.md)
 - [com.immutable.sdk.api.model.AssetProperties](docs/AssetProperties.md)
 - [com.immutable.sdk.api.model.Balance](docs/Balance.md)
 - [com.immutable.sdk.api.model.CancelOrderRequest](docs/CancelOrderRequest.md)
 - [com.immutable.sdk.api.model.CancelOrderResponse](docs/CancelOrderResponse.md)
 - [com.immutable.sdk.api.model.Collection](docs/Collection.md)
 - [com.immutable.sdk.api.model.CollectionDetails](docs/CollectionDetails.md)
 - [com.immutable.sdk.api.model.CollectionFilter](docs/CollectionFilter.md)
 - [com.immutable.sdk.api.model.CreateCollectionRequest](docs/CreateCollectionRequest.md)
 - [com.immutable.sdk.api.model.CreateOrderRequest](docs/CreateOrderRequest.md)
 - [com.immutable.sdk.api.model.CreateOrderResponse](docs/CreateOrderResponse.md)
 - [com.immutable.sdk.api.model.CreateProjectRequest](docs/CreateProjectRequest.md)
 - [com.immutable.sdk.api.model.CreateProjectResponse](docs/CreateProjectResponse.md)
 - [com.immutable.sdk.api.model.CreateTradeRequestV1](docs/CreateTradeRequestV1.md)
 - [com.immutable.sdk.api.model.CreateTradeResponse](docs/CreateTradeResponse.md)
 - [com.immutable.sdk.api.model.CreateTransferRequest](docs/CreateTransferRequest.md)
 - [com.immutable.sdk.api.model.CreateTransferRequestV1](docs/CreateTransferRequestV1.md)
 - [com.immutable.sdk.api.model.CreateTransferResponse](docs/CreateTransferResponse.md)
 - [com.immutable.sdk.api.model.CreateTransferResponseV1](docs/CreateTransferResponseV1.md)
 - [com.immutable.sdk.api.model.CreateWithdrawalRequest](docs/CreateWithdrawalRequest.md)
 - [com.immutable.sdk.api.model.CreateWithdrawalResponse](docs/CreateWithdrawalResponse.md)
 - [com.immutable.sdk.api.model.Deposit](docs/Deposit.md)
 - [com.immutable.sdk.api.model.Fee](docs/Fee.md)
 - [com.immutable.sdk.api.model.FeeData](docs/FeeData.md)
 - [com.immutable.sdk.api.model.FeeEntry](docs/FeeEntry.md)
 - [com.immutable.sdk.api.model.FeeInfo](docs/FeeInfo.md)
 - [com.immutable.sdk.api.model.FeeToken](docs/FeeToken.md)
 - [com.immutable.sdk.api.model.GetBalanceResponse](docs/GetBalanceResponse.md)
 - [com.immutable.sdk.api.model.GetProjectsResponse](docs/GetProjectsResponse.md)
 - [com.immutable.sdk.api.model.GetSignableCancelOrderRequest](docs/GetSignableCancelOrderRequest.md)
 - [com.immutable.sdk.api.model.GetSignableCancelOrderResponse](docs/GetSignableCancelOrderResponse.md)
 - [com.immutable.sdk.api.model.GetSignableDepositRequest](docs/GetSignableDepositRequest.md)
 - [com.immutable.sdk.api.model.GetSignableDepositResponse](docs/GetSignableDepositResponse.md)
 - [com.immutable.sdk.api.model.GetSignableOrderRequest](docs/GetSignableOrderRequest.md)
 - [com.immutable.sdk.api.model.GetSignableOrderRequestV1](docs/GetSignableOrderRequestV1.md)
 - [com.immutable.sdk.api.model.GetSignableOrderResponse](docs/GetSignableOrderResponse.md)
 - [com.immutable.sdk.api.model.GetSignableOrderResponseV1](docs/GetSignableOrderResponseV1.md)
 - [com.immutable.sdk.api.model.GetSignableRegistrationOffchainResponse](docs/GetSignableRegistrationOffchainResponse.md)
 - [com.immutable.sdk.api.model.GetSignableRegistrationRequest](docs/GetSignableRegistrationRequest.md)
 - [com.immutable.sdk.api.model.GetSignableRegistrationResponse](docs/GetSignableRegistrationResponse.md)
 - [com.immutable.sdk.api.model.GetSignableTradeRequest](docs/GetSignableTradeRequest.md)
 - [com.immutable.sdk.api.model.GetSignableTradeResponse](docs/GetSignableTradeResponse.md)
 - [com.immutable.sdk.api.model.GetSignableTransferRequest](docs/GetSignableTransferRequest.md)
 - [com.immutable.sdk.api.model.GetSignableTransferRequestV1](docs/GetSignableTransferRequestV1.md)
 - [com.immutable.sdk.api.model.GetSignableTransferResponse](docs/GetSignableTransferResponse.md)
 - [com.immutable.sdk.api.model.GetSignableTransferResponseV1](docs/GetSignableTransferResponseV1.md)
 - [com.immutable.sdk.api.model.GetSignableWithdrawalRequest](docs/GetSignableWithdrawalRequest.md)
 - [com.immutable.sdk.api.model.GetSignableWithdrawalResponse](docs/GetSignableWithdrawalResponse.md)
 - [com.immutable.sdk.api.model.GetSnapshotRequest](docs/GetSnapshotRequest.md)
 - [com.immutable.sdk.api.model.GetTLVsResponse](docs/GetTLVsResponse.md)
 - [com.immutable.sdk.api.model.GetTLVsResult](docs/GetTLVsResult.md)
 - [com.immutable.sdk.api.model.GetUsersApiResponse](docs/GetUsersApiResponse.md)
 - [com.immutable.sdk.api.model.ListApplicationsResponse](docs/ListApplicationsResponse.md)
 - [com.immutable.sdk.api.model.ListAssetsResponse](docs/ListAssetsResponse.md)
 - [com.immutable.sdk.api.model.ListBalancesResponse](docs/ListBalancesResponse.md)
 - [com.immutable.sdk.api.model.ListCollectionsResponse](docs/ListCollectionsResponse.md)
 - [com.immutable.sdk.api.model.ListDepositsResponse](docs/ListDepositsResponse.md)
 - [com.immutable.sdk.api.model.ListMintsResponse](docs/ListMintsResponse.md)
 - [com.immutable.sdk.api.model.ListOrdersResponse](docs/ListOrdersResponse.md)
 - [com.immutable.sdk.api.model.ListSnapshotBalancesResponse](docs/ListSnapshotBalancesResponse.md)
 - [com.immutable.sdk.api.model.ListTokensResponse](docs/ListTokensResponse.md)
 - [com.immutable.sdk.api.model.ListTradesResponse](docs/ListTradesResponse.md)
 - [com.immutable.sdk.api.model.ListTransfersResponse](docs/ListTransfersResponse.md)
 - [com.immutable.sdk.api.model.ListWithdrawalsResponse](docs/ListWithdrawalsResponse.md)
 - [com.immutable.sdk.api.model.MetadataSchemaProperty](docs/MetadataSchemaProperty.md)
 - [com.immutable.sdk.api.model.MetadataSchemaRequest](docs/MetadataSchemaRequest.md)
 - [com.immutable.sdk.api.model.Mint](docs/Mint.md)
 - [com.immutable.sdk.api.model.MintFee](docs/MintFee.md)
 - [com.immutable.sdk.api.model.MintRequest](docs/MintRequest.md)
 - [com.immutable.sdk.api.model.MintRequestV1](docs/MintRequestV1.md)
 - [com.immutable.sdk.api.model.MintResultDetails](docs/MintResultDetails.md)
 - [com.immutable.sdk.api.model.MintResultV1](docs/MintResultV1.md)
 - [com.immutable.sdk.api.model.MintTokenData](docs/MintTokenData.md)
 - [com.immutable.sdk.api.model.MintTokenDataV2](docs/MintTokenDataV2.md)
 - [com.immutable.sdk.api.model.MintTokens](docs/MintTokens.md)
 - [com.immutable.sdk.api.model.MintTokensRequestV1](docs/MintTokensRequestV1.md)
 - [com.immutable.sdk.api.model.MintTokensResponse](docs/MintTokensResponse.md)
 - [com.immutable.sdk.api.model.MintTokensResponseV1](docs/MintTokensResponseV1.md)
 - [com.immutable.sdk.api.model.MintUser](docs/MintUser.md)
 - [com.immutable.sdk.api.model.MintableTokenDetails](docs/MintableTokenDetails.md)
 - [com.immutable.sdk.api.model.Order](docs/Order.md)
 - [com.immutable.sdk.api.model.OrderDetails](docs/OrderDetails.md)
 - [com.immutable.sdk.api.model.OrderFeeInfo](docs/OrderFeeInfo.md)
 - [com.immutable.sdk.api.model.Project](docs/Project.md)
 - [com.immutable.sdk.api.model.Range](docs/Range.md)
 - [com.immutable.sdk.api.model.RegisterUserRequest](docs/RegisterUserRequest.md)
 - [com.immutable.sdk.api.model.RegisterUserResponse](docs/RegisterUserResponse.md)
 - [com.immutable.sdk.api.model.SignableToken](docs/SignableToken.md)
 - [com.immutable.sdk.api.model.SignableTransferDetails](docs/SignableTransferDetails.md)
 - [com.immutable.sdk.api.model.SignableTransferResponseDetails](docs/SignableTransferResponseDetails.md)
 - [com.immutable.sdk.api.model.SnapshotBalancesRecord](docs/SnapshotBalancesRecord.md)
 - [com.immutable.sdk.api.model.SuccessResponse](docs/SuccessResponse.md)
 - [com.immutable.sdk.api.model.Token](docs/Token.md)
 - [com.immutable.sdk.api.model.TokenData](docs/TokenData.md)
 - [com.immutable.sdk.api.model.TokenDetails](docs/TokenDetails.md)
 - [com.immutable.sdk.api.model.Trade](docs/Trade.md)
 - [com.immutable.sdk.api.model.TradeSide](docs/TradeSide.md)
 - [com.immutable.sdk.api.model.Transfer](docs/Transfer.md)
 - [com.immutable.sdk.api.model.TransferRequest](docs/TransferRequest.md)
 - [com.immutable.sdk.api.model.UpdateCollectionRequest](docs/UpdateCollectionRequest.md)
 - [com.immutable.sdk.api.model.Withdrawal](docs/Withdrawal.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

All endpoints do not require authorization.
