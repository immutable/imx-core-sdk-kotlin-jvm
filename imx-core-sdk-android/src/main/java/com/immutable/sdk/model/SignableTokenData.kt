package com.immutable.sdk.model

import com.squareup.moshi.Json

data class SignableTokenData(
    @Json(name = "token_address")
    val tokenAddress: String? = null,
    @Json(name = "token_id")
    val tokenId: String? = null,
    val decimals: Int? = null
)
