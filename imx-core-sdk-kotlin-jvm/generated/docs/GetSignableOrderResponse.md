
# GetSignableOrderResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amountBuy** | **kotlin.String** | Fee-exclusive amount to buy | 
**amountSell** | **kotlin.String** | Amount to sell | 
**assetIdBuy** | **kotlin.String** | ID of the asset to buy | 
**assetIdSell** | **kotlin.String** | ID of the asset to sell | 
**expirationTimestamp** | **kotlin.Int** | Expiration timestamp for this order | 
**nonce** | **kotlin.Int** | Nonce of the order | 
**payloadHash** | **kotlin.String** | Hash of the payload to be signed for signable order | 
**signableMessage** | **kotlin.String** | Message to sign with L1 wallet to confirm order request | 
**starkKey** | **kotlin.String** | Public stark key of the created user | 
**vaultIdBuy** | **kotlin.Int** | ID of the vault into which the bought asset will be placed | 
**vaultIdSell** | **kotlin.Int** | ID of the vault to sell from | 
**feeInfo** | [**FeeInfo**](FeeInfo.md) |  |  [optional]



