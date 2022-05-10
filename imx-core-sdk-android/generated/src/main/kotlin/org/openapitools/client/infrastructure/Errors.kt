@file:Suppress("unused")
package org.openapitools.client.infrastructure

import java.lang.RuntimeException

 data class ApiErrorModel(val code: String? = null, val message: String? = null)

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