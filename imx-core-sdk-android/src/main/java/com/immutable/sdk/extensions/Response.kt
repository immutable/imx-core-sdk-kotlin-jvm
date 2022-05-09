package com.immutable.sdk.extensions

import okhttp3.Response
import org.json.JSONObject
import org.json.JSONArray

/**
 * Converts [this] [Response] into [JSONObject]
 */
internal fun Response.getJson(): JSONObject? = body?.string()?.run {
    JSONObject(this)
}

/**
 * Converts [this] [Response] into [JSONArray]
 */
internal fun Response.getJsonArray(): JSONArray? = body?.string()?.run {
    JSONArray(this)
}
