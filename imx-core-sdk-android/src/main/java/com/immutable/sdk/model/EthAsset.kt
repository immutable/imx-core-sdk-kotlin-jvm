package com.immutable.sdk.model

import com.immutable.sdk.Constants
import com.immutable.sdk.api.model.SignableToken
import com.immutable.sdk.api.model.Token
import java.math.BigDecimal

/**
 * This is an ETH wrapper for the [Token] API model
 */
class EthAsset(quantity: String) :
    AssetModel(quantity) {
    override fun toSignableToken(fees: BigDecimal): SignableToken = SignableToken(
        type = TokenType.ETH.name,
        data = SignableTokenData(
            decimals = Constants.ETH_DECIMALS
        )
    )
}
