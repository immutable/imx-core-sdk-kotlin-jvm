
# Asset

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**collection** | [**CollectionDetails**](CollectionDetails.md) |  | 
**createdAt** | **kotlin.String** | Timestamp of when the asset was created | 
**description** | **kotlin.String** | Description of this asset | 
**imageUrl** | **kotlin.String** | URL of the image which should be used for this asset | 
**metadata** | [**kotlin.Any**](.md) | Metadata of this asset | 
**name** | **kotlin.String** | Name of this asset | 
**status** | **kotlin.String** | Status of this asset (where it is in the system) | 
**tokenAddress** | **kotlin.String** | Address of the ERC721 contract | 
**tokenId** | **kotlin.String** | ERC721 Token ID of this asset | 
**updatedAt** | **kotlin.String** | Timestamp of when the asset was updated | 
**uri** | **kotlin.String** | URI to access this asset externally to Immutable X | 
**user** | **kotlin.String** | Ethereum address of the user who owns this asset | 
**fees** | [**kotlin.collections.List&lt;Fee&gt;**](Fee.md) | Royalties to pay on this asset operations |  [optional]
**id** | **kotlin.String** | [DEPRECATED] Internal Immutable X Token ID |  [optional]
**orders** | [**com.immutable.sdk.api.model.AssetOrderDetails**](com.immutable.sdk.api.model.AssetOrderDetails.md) |  |  [optional]



