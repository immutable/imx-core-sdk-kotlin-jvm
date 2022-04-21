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

import com.immutable.sdk.api.model.TokenDetails

import com.squareup.moshi.Json

/**
 * 
 *
 * @param cursor Generated cursor returned by previous query
 * @param result Tokens matching query parameters
 */

data class ListTokensResponse (

    /* Generated cursor returned by previous query */
    @Json(name = "cursor")
    val cursor: kotlin.String? = null,

    /* Tokens matching query parameters */
    @Json(name = "result")
    val result: kotlin.collections.List<TokenDetails>? = null

)

