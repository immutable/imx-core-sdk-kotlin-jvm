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
 * @param assetId 
 * @param assetType 
 */

data class EncodeAssetResponse (

    @Json(name = "asset_id")
    val assetId: kotlin.String? = null,

    @Json(name = "asset_type")
    val assetType: kotlin.String? = null

)
