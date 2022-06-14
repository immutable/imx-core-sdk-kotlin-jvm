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
 * @param sentSignature [deprecated] Sent signature
 * @param status [deprecated] The status of transfer
 * @param time [deprecated] Time of the transfer
 * @param transferId ID of the transfer
 */

data class CreateTransferResponseV1 (

    /* [deprecated] Sent signature */
    @Json(name = "sent_signature")
    val sentSignature: kotlin.String,

    /* [deprecated] The status of transfer */
    @Json(name = "status")
    val status: kotlin.String,

    /* [deprecated] Time of the transfer */
    @Json(name = "time")
    val time: kotlin.Int,

    /* ID of the transfer */
    @Json(name = "transfer_id")
    val transferId: kotlin.Int

)

