package com.immutable.sdk.model

import com.immutable.sdk.api.model.Token
import com.immutable.sdk.api.model.TokenData
import com.immutable.sdk.utils.TokenType

class Erc20Asset(
    /**
     * The address of this ERC20 contract
     */
    val tokenAddress: String,
    /**
     * The quantization factor of the ERC20 token. Refer to here for more information:
     * https://docs.starkware.co/starkex-v4/starkex-deep-dive/starkex-specific-concepts#quantization
     */
    val decimals: Int,
    quantity: String
) : AssetModel(quantity) {
    override fun toToken(): Token = Token(
        type = TokenType.ERC20.name,
        data = TokenData(tokenAddress = tokenAddress, decimals = decimals)
    )
}
