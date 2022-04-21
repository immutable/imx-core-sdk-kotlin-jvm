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

import com.immutable.sdk.api.model.FeeData

import com.squareup.moshi.Json

/**
 * 
 *
 * @param `data` 
 * @param type Fee token type. One of ETH/ERC20
 */

data class FeeToken (

    @Json(name = "data")
    val `data`: FeeData? = null,

    /* Fee token type. One of ETH/ERC20 */
    @Json(name = "type")
    val type: FeeToken.Type? = null

) {

    /**
     * Fee token type. One of ETH/ERC20
     *
     * Values: eTH,eRC20
     */
    enum class Type(val value: kotlin.String) {
        @Json(name = "ETH") eTH("ETH"),
        @Json(name = "ERC20") eRC20("ERC20");
    }
}

