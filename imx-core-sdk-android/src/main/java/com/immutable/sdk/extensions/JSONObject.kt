package com.immutable.sdk.extensions

import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Converts [this] [JSONObject] to a URL encoded JSON string
 */
internal fun JSONObject.toURLEncodedString() =
    URLEncoder.encode(toString(), StandardCharsets.UTF_8.toString())
