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

import com.immutable.sdk.api.model.FeeToken

import com.squareup.moshi.Json

/**
 * 
 *
 * @param address 
 * @param amount 
 * @param token 
 * @param type Fee type
 */

data class OrderFeeInfo (

    @Json(name = "address")
    val address: kotlin.String? = null,

    @Json(name = "amount")
    val amount: kotlin.String? = null,

    @Json(name = "token")
    val token: FeeToken? = null,

    /* Fee type */
    @Json(name = "type")
    val type: kotlin.String? = null

)

