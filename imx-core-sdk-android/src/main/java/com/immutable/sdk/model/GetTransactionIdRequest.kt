package com.immutable.sdk.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class GetTransactionIdRequest(
    @Json(name = "wallet_address")
    val walletAddress: String,
    val provider: String
)
