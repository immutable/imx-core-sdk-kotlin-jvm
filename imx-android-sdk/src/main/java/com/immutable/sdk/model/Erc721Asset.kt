package com.immutable.sdk.model

import com.immutable.sdk.utils.TokenType

class Erc721Asset(
    /**
     * The address of this ERC721 contract
     */
    val tokenAddress: String,
    /**
     * The token id of this ERC721 asset
     */
    val tokenId: String
) : AssetModel("1") {
    override fun toToken(): Token = Token(
        type = TokenType.ERC721.name,
        data = TokenData(tokenAddress = tokenAddress, tokenId = tokenId)
    )
}
