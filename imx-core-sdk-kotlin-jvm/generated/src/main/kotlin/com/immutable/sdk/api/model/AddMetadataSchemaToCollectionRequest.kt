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

import com.immutable.sdk.api.model.MetadataSchemaRequest

import com.squareup.moshi.Json

/**
 * 
 *
 * @param metadata The metadata container
 * @param contractAddress Not required from API user
 */

data class AddMetadataSchemaToCollectionRequest (

    /* The metadata container */
    @Json(name = "metadata")
    val metadata: kotlin.collections.List<MetadataSchemaRequest>,

    /* Not required from API user */
    @Json(name = "contract_address")
    val contractAddress: kotlin.String? = null

)

