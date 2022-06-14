/**
 * Immutable X API
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1
 * Contact: support@immutable.com
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.immutable.sdk.api.model


import com.squareup.moshi.Json

/**
 * 
 *
 * @param orderId ID of the cancelled order
 * @param status New status of the order
 */

data class CancelOrderResponse (

    /* ID of the cancelled order */
    @Json(name = "order_id")
    val orderId: kotlin.Int,

    /* New status of the order */
    @Json(name = "status")
    val status: kotlin.String

)
