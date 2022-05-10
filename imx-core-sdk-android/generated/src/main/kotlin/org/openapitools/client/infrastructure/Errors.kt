@file:Suppress("unused")
package org.openapitools.client.infrastructure

import java.lang.RuntimeException
import org.json.JSONException
import org.json.JSONObject

 class ApiErrorModel(val body: Any? = null) {
    val code: String?
    val message: String?

    init {
        val (code, message) = if (body is String) {
            try {
                val json = JSONObject(body)
                json.optString("code") to json.optString("message")
            } catch (e: JSONException) {
                null to null
            }
        } else
            null to null

        this.code = code
        this.message = message
    }
}

open class ClientException(message: kotlin.String? = null, val statusCode: Int = -1, val response: Response? = null, val errorModel: ApiErrorModel? = null) : RuntimeException(message) {

    companion object {
        private const val serialVersionUID: Long = 123L
    }
}

open class ServerException(message: kotlin.String? = null, val statusCode: Int = -1, val response: Response? = null, val errorModel: ApiErrorModel? = null) : RuntimeException(message) {

    companion object {
        private const val serialVersionUID: Long = 456L
    }
}