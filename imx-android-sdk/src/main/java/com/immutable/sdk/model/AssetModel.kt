package com.immutable.sdk.model

sealed class AssetModel(val quantity: String) {
    abstract fun toToken(): Token
}
