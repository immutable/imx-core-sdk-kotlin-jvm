package com.immutable.sdk.model

data class Erc721Asset(
    /**
     * The address of this ERC721 contract
     */
    val tokenAddress: String,
    /**
     * The token id of this ERC721 asset
     */
    val tokenId: String
)
