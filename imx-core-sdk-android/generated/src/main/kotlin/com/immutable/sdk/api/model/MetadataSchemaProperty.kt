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
 * @param filterable Sets the metadata as filterable
 * @param name Name of the metadata key
 * @param type Type of the metadata. Values: \"enum\", \"text\", \"boolean\", \"continuous\", \"discrete\" | Default: \"text\". Src: https://docs.x.immutable.com/docs/asset-metadata#property-type-mapping
 */

data class MetadataSchemaProperty (

    /* Sets the metadata as filterable */
    @Json(name = "filterable")
    val filterable: kotlin.Boolean,

    /* Name of the metadata key */
    @Json(name = "name")
    val name: kotlin.String,

    /* Type of the metadata. Values: \"enum\", \"text\", \"boolean\", \"continuous\", \"discrete\" | Default: \"text\". Src: https://docs.x.immutable.com/docs/asset-metadata#property-type-mapping */
    @Json(name = "type")
    val type: kotlin.String

)

