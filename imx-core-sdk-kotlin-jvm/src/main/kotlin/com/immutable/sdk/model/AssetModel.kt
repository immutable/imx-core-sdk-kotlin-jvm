package com.immutable.sdk.model

import com.immutable.sdk.Constants
import com.immutable.sdk.api.model.SignableToken
import java.math.BigDecimal

/**
 * This is a wrapper for the [SignableToken] API model that gives structured classes for the 3 types of
 * assets (ERC20, ERC721, ETH) used in the workflow functions.
 *
 * @property quantity the amount of the asset to be transferred/sold/bought
 */
sealed class AssetModel(val quantity: String) {
    /**
     * This converts this convenience model into the [SignableToken] API model which will be used for the
     * client calls.
     */
    abstract fun toSignableToken(): SignableToken
}

/**
 * Helper function to calculate the quantity field
 */
internal fun AssetModel.formatQuantity(): String {
    val decimals = when (this) {
        is Erc20Asset -> decimals
        is Erc721Asset -> 0
        else -> Constants.ETH_DECIMALS
    }
    val totalAmount = BigDecimal(quantity)
    return (BigDecimal.TEN.pow(decimals) * totalAmount).toBigInteger()
        .toString()
}
