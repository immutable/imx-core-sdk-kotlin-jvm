package com.immutable.sdk.extensions

import okhttp3.Response
import org.json.JSONObject

/**
 * Converts [this] [Response] into [JSONObject]
 */
internal fun Response.getJson(): JSONObject? = body?.string()?.run {
    JSONObject(this)
}
