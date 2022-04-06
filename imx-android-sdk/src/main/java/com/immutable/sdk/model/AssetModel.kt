package com.immutable.sdk.model

/**
 * @param quantity the amount of the asset to be transferred/sold/bought
 */
sealed class AssetModel(val quantity: String) {
    abstract fun toToken(): Token
}
