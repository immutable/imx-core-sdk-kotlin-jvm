package com.immutable.sdk.model

import com.immutable.sdk.api.model.SignableToken

/**
 * This is a wrapper for the [Token] API model that gives structured classes for the 3 types of
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
