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
 * @param blueprint Blueprint of this token
 * @param clientTokenId ID provided by the client for this token
 * @param tokenId IMX Id of this token
 */

data class MintableTokenDetails (

    /* Blueprint of this token */
    @Json(name = "blueprint")
    val blueprint: kotlin.String,

    /* ID provided by the client for this token */
    @Json(name = "client_token_id")
    val clientTokenId: kotlin.String,

    /* IMX Id of this token */
    @Json(name = "token_id")
    val tokenId: kotlin.String

)

