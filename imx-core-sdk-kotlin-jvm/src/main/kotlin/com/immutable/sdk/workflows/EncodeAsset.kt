package com.immutable.sdk.workflows

import com.google.common.annotations.VisibleForTesting
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.model.EncodeAssetRequest
import com.immutable.sdk.api.model.EncodeAssetRequestToken
import com.immutable.sdk.api.model.EncodeAssetTokenData
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.withdrawal.MintableErc721Asset

private const val ENCODE_ASSET = "Encode asset"

@VisibleForTesting
internal enum class EncodeAssetType(val value: String) {
    Asset("asset"),
    MintableAsset("mintable-asset")
}

internal fun encodeAsset(
    asset: AssetModel,
    api: EncodingApi
) = call(ENCODE_ASSET) {
    val tokenType = when (asset) {
        is EthAsset -> EncodeAssetRequestToken.Type.eTH
        is Erc20Asset -> EncodeAssetRequestToken.Type.eRC20
        else -> EncodeAssetRequestToken.Type.eRC721
    }
    val encodeAssetTokenData = when (asset) {
        is EthAsset -> null
        is Erc20Asset -> EncodeAssetTokenData(tokenAddress = asset.tokenAddress)
        is MintableErc721Asset -> EncodeAssetTokenData(
            blueprint = asset.blueprint,
            id = asset.tokenId,
            tokenAddress = asset.tokenAddress,
        )
        is Erc721Asset -> EncodeAssetTokenData(
            tokenAddress = asset.tokenAddress,
            tokenId = asset.tokenId
        )
    }
    val assetType =
        if (asset is MintableErc721Asset) EncodeAssetType.MintableAsset else EncodeAssetType.Asset
    val request = EncodeAssetRequest(
        token = EncodeAssetRequestToken(
            type = tokenType,
            data = encodeAssetTokenData
        )
    )
    api.encodeAsset(assetType.value, request)
}
