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

import com.immutable.sdk.api.model.Fee
import com.immutable.sdk.api.model.Token

import com.squareup.moshi.Json

/**
 * 
 *
 * @param fees Fee details
 * @param status Status of this mint
 * @param timestamp Timestamp this mint was initiated
 * @param token 
 * @param transactionId Sequential ID of transaction in Immutable X
 * @param user Ethereum address of the user to whom the asset has been minted
 */

data class Mint (

    /* Fee details */
    @Json(name = "fees")
    val fees: kotlin.collections.List<Fee>? = null,

    /* Status of this mint */
    @Json(name = "status")
    val status: kotlin.String? = null,

    /* Timestamp this mint was initiated */
    @Json(name = "timestamp")
    val timestamp: kotlin.String? = null,

    @Json(name = "token")
    val token: Token? = null,

    /* Sequential ID of transaction in Immutable X */
    @Json(name = "transaction_id")
    val transactionId: kotlin.Int? = null,

    /* Ethereum address of the user to whom the asset has been minted */
    @Json(name = "user")
    val user: kotlin.String? = null

)
