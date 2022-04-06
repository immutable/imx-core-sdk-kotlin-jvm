package com.immutable.sdk.model

sealed class SellToken {
    object ETH : SellToken()

    /**
     * @param tokenAddress the address of the ERC20 contract.
     * @param decimals the number of decimals for the token.
     */
    data class ERC20(val tokenAddress: String, val decimals: Int) : SellToken()
}
