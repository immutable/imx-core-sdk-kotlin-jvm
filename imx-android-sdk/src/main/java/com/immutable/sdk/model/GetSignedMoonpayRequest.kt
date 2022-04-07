package com.immutable.sdk.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class GetSignedMoonpayRequest(val request: String)
