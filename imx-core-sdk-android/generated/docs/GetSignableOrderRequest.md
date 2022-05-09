
# GetSignableOrderRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amountBuy** | **kotlin.String** | Amount to buy | 
**amountSell** | **kotlin.String** | Amount to sell | 
**tokenBuy** | [**SignableToken**](SignableToken.md) |  | 
**tokenSell** | [**SignableToken**](SignableToken.md) |  | 
**user** | **kotlin.String** | Ethereum address of the submitting user | 
**expirationTimestamp** | **kotlin.Int** | ExpirationTimestamp in Unix time. Note: will be rounded down to the nearest hour |  [optional]
**fees** | [**kotlin.collections.List&lt;FeeEntry&gt;**](FeeEntry.md) | Inclusion of either maker or taker fees |  [optional]



