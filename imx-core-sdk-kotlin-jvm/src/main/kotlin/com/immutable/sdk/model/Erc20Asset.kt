package com.immutable.sdk.model

import com.immutable.sdk.api.model.SignableToken
import com.immutable.sdk.api.model.Token

/**
 * This is an ERC20 wrapper for the [Token] API model
 *
 * @property tokenAddress The address of this ERC20 contract
 * @property decimals The quantization factor of the ERC20 token. Refer
 * [here](https://docs.starkware.co/starkex-v4/starkex-deep-dive/starkex-specific-concepts#quantization)
 * for more information.
 */
class Erc20Asset(
    val tokenAddress: String,
    val decimals: Int,
    quantity: String,
) : AssetModel(quantity) {
    override fun toSignableToken(): SignableToken = SignableToken(
        type = TokenType.ERC20.name,
        data = SignableTokenData(
            tokenAddress = tokenAddress,
            decimals = decimals,
        )
    )
}
