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

import com.immutable.sdk.api.model.OrderFeeInfo
import com.immutable.sdk.api.model.Token

import com.squareup.moshi.Json

/**
 * 
 *
 * @param amountSold Amount of the asset already sold by this order
 * @param buy 
 * @param expirationTimestamp Expiration timestamp of this order
 * @param fees Fee information for the order
 * @param orderId ID of the order
 * @param sell 
 * @param status Status of the order
 * @param timestamp Timestamp this order was created
 * @param updatedTimestamp Updated timestamp of this order
 * @param user Ethereum address of the user who submitted the order
 */

data class Order (

    /* Amount of the asset already sold by this order */
    @Json(name = "amount_sold")
    val amountSold: kotlin.String? = null,

    @Json(name = "buy")
    val buy: Token? = null,

    /* Expiration timestamp of this order */
    @Json(name = "expiration_timestamp")
    val expirationTimestamp: kotlin.String? = null,

    /* Fee information for the order */
    @Json(name = "fees")
    val fees: kotlin.collections.List<OrderFeeInfo>? = null,

    /* ID of the order */
    @Json(name = "order_id")
    val orderId: kotlin.Int? = null,

    @Json(name = "sell")
    val sell: Token? = null,

    /* Status of the order */
    @Json(name = "status")
    val status: kotlin.String? = null,

    /* Timestamp this order was created */
    @Json(name = "timestamp")
    val timestamp: kotlin.String? = null,

    /* Updated timestamp of this order */
    @Json(name = "updated_timestamp")
    val updatedTimestamp: kotlin.String? = null,

    /* Ethereum address of the user who submitted the order */
    @Json(name = "user")
    val user: kotlin.String? = null

)

