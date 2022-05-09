
# GetSignableOrderResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amountBuy** | **kotlin.String** | Amount to buy |  [optional]
**amountSell** | **kotlin.String** | Amount to sell |  [optional]
**assetIdBuy** | **kotlin.String** | ID of the asset to buy |  [optional]
**assetIdSell** | **kotlin.String** | ID of the asset to sell |  [optional]
**expirationTimestamp** | **kotlin.Int** | Expiration timestamp for this order |  [optional]
**feeInfo** | [**FeeInfo**](FeeInfo.md) |  |  [optional]
**nonce** | **kotlin.Int** | Nonce of the order |  [optional]
**payloadHash** | **kotlin.String** | Hash of the payload to be signed for signable order |  [optional]
**signableMessage** | **kotlin.String** | Message to sign with L1 wallet to confirm order request |  [optional]
**starkKey** | **kotlin.String** | Public stark key of the created user |  [optional]
**vaultIdBuy** | **kotlin.Int** | ID of the vault into which the bought asset will be placed |  [optional]
**vaultIdSell** | **kotlin.Int** | ID of the vault to sell from |  [optional]



