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

import com.immutable.sdk.api.model.Token

import com.squareup.moshi.Json

/**
 * 
 *
 * @param status Status of this deposit in Immutable X
 * @param timestamp Timestamp of the deposit
 * @param token 
 * @param transactionId Sequential ID of this transaction within Immutable X
 * @param user Ethereum address of the user making this deposit
 */

data class Deposit (

    /* Status of this deposit in Immutable X */
    @Json(name = "status")
    val status: kotlin.String? = null,

    /* Timestamp of the deposit */
    @Json(name = "timestamp")
    val timestamp: kotlin.String? = null,

    @Json(name = "token")
    val token: Token? = null,

    /* Sequential ID of this transaction within Immutable X */
    @Json(name = "transaction_id")
    val transactionId: kotlin.Int? = null,

    /* Ethereum address of the user making this deposit */
    @Json(name = "user")
    val user: kotlin.String? = null

)
