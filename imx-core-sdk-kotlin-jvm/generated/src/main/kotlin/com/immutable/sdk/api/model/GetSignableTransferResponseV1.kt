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
 * @param amount Amount of the asset being transferred
 * @param assetId ID of the asset being transferred
 * @param expirationTimestamp Token in request to match in SDK implementation
 * @param nonce Nonce of the transfer
 * @param payloadHash Hash of the payload
 * @param receiverStarkKey Receiver of the transfer
 * @param receiverVaultId ID of the vault being transferred to
 * @param senderVaultId ID of the vault being transferred from
 * @param signableMessage Message to sign with L1 wallet to confirm transfer request
 * @param senderStarkKey Sender of the transfer
 */

data class GetSignableTransferResponseV1 (

    /* Amount of the asset being transferred */
    @Json(name = "amount")
    val amount: kotlin.String,

    /* ID of the asset being transferred */
    @Json(name = "asset_id")
    val assetId: kotlin.String,

    /* Token in request to match in SDK implementation */
    @Json(name = "expiration_timestamp")
    val expirationTimestamp: kotlin.Int,

    /* Nonce of the transfer */
    @Json(name = "nonce")
    val nonce: kotlin.Int,

    /* Hash of the payload */
    @Json(name = "payload_hash")
    val payloadHash: kotlin.String,

    /* Receiver of the transfer */
    @Json(name = "receiver_stark_key")
    val receiverStarkKey: kotlin.String,

    /* ID of the vault being transferred to */
    @Json(name = "receiver_vault_id")
    val receiverVaultId: kotlin.Int,

    /* ID of the vault being transferred from */
    @Json(name = "sender_vault_id")
    val senderVaultId: kotlin.Int,

    /* Message to sign with L1 wallet to confirm transfer request */
    @Json(name = "signable_message")
    val signableMessage: kotlin.String,

    /* Sender of the transfer */
    @Json(name = "sender_stark_key")
    val senderStarkKey: kotlin.String? = null

)

