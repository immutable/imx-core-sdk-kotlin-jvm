package com.immutable.sdk.utils

sealed class ConvertedToken(val amount: String) {
    data class ETH(val decimals: Int, val quantity: String) : ConvertedToken(quantity)
    data class ERC20(val tokenAddress: String, val decimals: Int, val quantity: String) :
        ConvertedToken(quantity)

    data class ERC721(val tokenAddress: String, val tokenId: String, val quantity: String) :
        ConvertedToken(quantity)
}
