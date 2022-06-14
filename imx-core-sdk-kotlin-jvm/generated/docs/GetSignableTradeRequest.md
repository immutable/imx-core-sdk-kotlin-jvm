
# GetSignableTradeRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**orderId** | **kotlin.Int** | The ID of the maker order involved | 
**user** | **kotlin.String** | Ethereum address of the submitting user | 
**expirationTimestamp** | **kotlin.Int** | ExpirationTimestamp in Unix time. Note: will be rounded down to the nearest hour |  [optional]
**fees** | [**kotlin.collections.List&lt;FeeEntry&gt;**](FeeEntry.md) | Inclusion of either maker or taker fees |  [optional]



