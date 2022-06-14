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

import com.immutable.sdk.api.model.EncodeAssetTokenData

import com.squareup.moshi.Json

/**
 * 
 *
 * @param `data` 
 * @param type The type of the token to be encoded
 */

data class EncodeAssetRequestToken (

    @Json(name = "data")
    val `data`: EncodeAssetTokenData? = null,

    /* The type of the token to be encoded */
    @Json(name = "type")
    val type: EncodeAssetRequestToken.Type? = null

) {

    /**
     * The type of the token to be encoded
     *
     * Values: eTH,eRC20,eRC721
     */
    enum class Type(val value: kotlin.String) {
        @Json(name = "ETH") eTH("ETH"),
        @Json(name = "ERC20") eRC20("ERC20"),
        @Json(name = "ERC721") eRC721("ERC721");
    }
}

