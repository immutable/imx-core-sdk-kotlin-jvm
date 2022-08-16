package com.immutable.sdk.model

import com.immutable.sdk.api.model.SignableToken
import com.squareup.moshi.Json

/**
 * An object representing the `data` field of [SignableToken].
 */
data class SignableTokenData(
    @Json(name = "token_address")
    val tokenAddress: String? = null,
    @Json(name = "token_id")
    val tokenId: String? = null,
    val decimals: Int? = null
)
