
# SignableTransferResponseDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amount** | **kotlin.String** | Amount of the asset being transferred |  [optional]
**assetId** | **kotlin.String** | ID of the asset being transferred |  [optional]
**expirationTimestamp** | **kotlin.Int** | Timestamp when this transfer will expire |  [optional]
**nonce** | **kotlin.Int** | Nonce of the transfer |  [optional]
**payloadHash** | **kotlin.String** | Hash of the payload to be signed for transfer |  [optional]
**receiverStarkKey** | **kotlin.String** | Receiver of the transfer |  [optional]
**receiverVaultId** | **kotlin.Int** | ID of the vault being transferred to |  [optional]
**senderVaultId** | **kotlin.Int** | ID of the vault being transferred from |  [optional]
**token** | [**SignableToken**](SignableToken.md) |  |  [optional]



