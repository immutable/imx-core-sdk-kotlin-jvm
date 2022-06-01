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

import com.immutable.sdk.api.model.FeeEntry

import com.squareup.moshi.Json

/**
 * 
 *
 * @param orderId The ID of the maker order involved
 * @param user Ethereum address of the submitting user
 * @param expirationTimestamp ExpirationTimestamp in Unix time. Note: will be rounded down to the nearest hour
 * @param fees Inclusion of either maker or taker fees
 */

data class GetSignableTradeRequest (

    /* The ID of the maker order involved */
    @Json(name = "order_id")
    val orderId: kotlin.Int,

    /* Ethereum address of the submitting user */
    @Json(name = "user")
    val user: kotlin.String,

    /* ExpirationTimestamp in Unix time. Note: will be rounded down to the nearest hour */
    @Json(name = "expiration_timestamp")
    val expirationTimestamp: kotlin.Int? = null,

    /* Inclusion of either maker or taker fees */
    @Json(name = "fees")
    val fees: kotlin.collections.List<FeeEntry>? = null

)

