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
 * @param balance Amount which is currently inside the exchange
 * @param preparingWithdrawal Amount which is currently preparing withdrawal from the exchange
 * @param symbol Symbol of the token (e.g. ETH, IMX)
 * @param withdrawable Amount which is currently withdrawable from the exchange
 */

data class Balance (

    /* Amount which is currently inside the exchange */
    @Json(name = "balance")
    val balance: kotlin.String? = null,

    /* Amount which is currently preparing withdrawal from the exchange */
    @Json(name = "preparing_withdrawal")
    val preparingWithdrawal: kotlin.String? = null,

    /* Symbol of the token (e.g. ETH, IMX) */
    @Json(name = "symbol")
    val symbol: kotlin.String? = null,

    /* Amount which is currently withdrawable from the exchange */
    @Json(name = "withdrawable")
    val withdrawable: kotlin.String? = null

)

