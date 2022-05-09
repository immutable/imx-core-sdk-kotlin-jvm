
# CreateOrderRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amountBuy** | **kotlin.String** | Amount to buy | 
**amountSell** | **kotlin.String** | Amount to sell | 
**assetIdBuy** | **kotlin.String** | ID of the asset to buy | 
**assetIdSell** | **kotlin.String** | ID of the asset to sell | 
**expirationTimestamp** | **kotlin.Int** | Expiration timestamp for this order | 
**nonce** | **kotlin.Int** | Nonce of the order | 
**starkKey** | **kotlin.String** | Public stark key of the user creating order | 
**starkSignature** | **kotlin.String** | Payload signature | 
**vaultIdBuy** | **kotlin.Int** | ID of the vault into which the bought asset will be placed | 
**vaultIdSell** | **kotlin.Int** | ID of the vault to sell from | 
**fees** | [**kotlin.collections.List&lt;FeeEntry&gt;**](FeeEntry.md) | Fee information |  [optional]
**includeFees** | **kotlin.Boolean** | Whether to include fees in order |  [optional]



