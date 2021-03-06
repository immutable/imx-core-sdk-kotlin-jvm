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

import com.immutable.sdk.api.model.MintTokenDataV2

import com.squareup.moshi.Json

/**
 * 
 *
 * @param tokens List of Mint tokens
 * @param user User wallet address
 */

data class MintUser (

    /* List of Mint tokens */
    @Json(name = "tokens")
    val tokens: kotlin.collections.List<MintTokenDataV2>,

    /* User wallet address */
    @Json(name = "user")
    val user: kotlin.String

)

