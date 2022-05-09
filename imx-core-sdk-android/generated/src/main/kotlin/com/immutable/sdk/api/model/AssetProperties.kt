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

import com.immutable.sdk.api.model.CollectionDetails

import com.squareup.moshi.Json

/**
 * 
 *
 * @param collection 
 * @param imageUrl Image URL of this asset
 * @param name Name of this asset
 */

data class AssetProperties (

    @Json(name = "collection")
    val collection: CollectionDetails? = null,

    /* Image URL of this asset */
    @Json(name = "image_url")
    val imageUrl: kotlin.String? = null,

    /* Name of this asset */
    @Json(name = "name")
    val name: kotlin.String? = null

)
