package com.immutable.sdk.model

import com.immutable.sdk.api.model.SignableToken
import com.immutable.sdk.api.model.Token
import com.immutable.sdk.api.model.TokenData
import com.immutable.sdk.utils.Constants

/**
 * This is an ETH wrapper for the [Token] API model
 */
class EthAsset(quantity: String) : AssetModel(quantity) {
    override fun toSignableToken(): SignableToken = SignableToken(
        type = TokenType.ETH.name,
        data = TokenData(decimals = Constants.ETH_DECIMALS)
    )
}