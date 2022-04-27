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
import com.immutable.sdk.api.model.Fee
import com.immutable.sdk.api.model.OrderDetails

import com.squareup.moshi.Json

/**
 * 
 *
 * @param collection 
 * @param createdAt Timestamp of when the asset was created
 * @param description Description of this asset
 * @param fees Royalties to pay on this asset operations
 * @param id [DEPRECATED] Internal Immutable X Token ID
 * @param imageUrl URL of the image which should be used for this asset
 * @param metadata Metadata of this asset
 * @param name Name of this asset
 * @param orders 
 * @param status Status of this asset (where it is in the system)
 * @param tokenAddress Address of the ERC721 contract
 * @param tokenId ERC721 Token ID of this asset
 * @param updatedAt Timestamp of when the asset was updated
 * @param uri URI to access this asset externally to Immutable X
 * @param user Ethereum address of the user who owns this asset
 */

data class Asset (

    @Json(name = "collection")
    val collection: CollectionDetails? = null,

    /* Timestamp of when the asset was created */
    @Json(name = "created_at")
    val createdAt: kotlin.String? = null,

    /* Description of this asset */
    @Json(name = "description")
    val description: kotlin.String? = null,

    /* Royalties to pay on this asset operations */
    @Json(name = "fees")
    val fees: kotlin.collections.List<Fee>? = null,

    /* [DEPRECATED] Internal Immutable X Token ID */
    @Json(name = "id")
    val id: kotlin.String? = null,

    /* URL of the image which should be used for this asset */
    @Json(name = "image_url")
    val imageUrl: kotlin.String? = null,

    /* Metadata of this asset */
    @Json(name = "metadata")
    val metadata: kotlin.Any? = null,

    /* Name of this asset */
    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "orders")
    val orders: OrderDetails? = null,

    /* Status of this asset (where it is in the system) */
    @Json(name = "status")
    val status: kotlin.String? = null,

    /* Address of the ERC721 contract */
    @Json(name = "token_address")
    val tokenAddress: kotlin.String? = null,

    /* ERC721 Token ID of this asset */
    @Json(name = "token_id")
    val tokenId: kotlin.String? = null,

    /* Timestamp of when the asset was updated */
    @Json(name = "updated_at")
    val updatedAt: kotlin.String? = null,

    /* URI to access this asset externally to Immutable X */
    @Json(name = "uri")
    val uri: kotlin.String? = null,

    /* Ethereum address of the user who owns this asset */
    @Json(name = "user")
    val user: kotlin.String? = null

)

