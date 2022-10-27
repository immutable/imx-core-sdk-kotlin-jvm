package com.immutable.sdk.model

import com.immutable.sdk.Constants.ERC721_AMOUNT
import com.immutable.sdk.api.model.SignableToken
import com.immutable.sdk.api.model.Token

/**
 * This is an ERC721 wrapper for the [Token] API model
 *
 * @property tokenAddress The address of this ERC721 contract
 * @property tokenId The token id of this ERC721 asset
 */
open class Erc721Asset(
    open val tokenAddress: String,
    val tokenId: String
) : AssetModel(ERC721_AMOUNT) {
    override fun toSignableToken(): SignableToken = SignableToken(
        type = TokenType.ERC721.name,
        data = SignableTokenData(
            tokenAddress = tokenAddress,
            tokenId = tokenId
        )
    )
}
