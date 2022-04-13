package com.immutable.sdk.model

import com.immutable.sdk.api.model.Token
import com.immutable.sdk.api.model.TokenData
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.TokenType

class EthAsset(quantity: String) : AssetModel(quantity) {
    override fun toToken(): Token = Token(
        type = TokenType.ETH.name,
        data = TokenData(decimals = Constants.ETH_DECIMALS)
    )
}
