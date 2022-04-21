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
 * @param amount Amount to transfer
 * @param assetId ID of the asset to transfer
 * @param expirationTimestamp Expiration timestamp for this transfer
 * @param nonce Nonce of the transfer
 * @param receiverStarkKey Public stark key of the user receiving the transfer
 * @param receiverVaultId ID of the vault into which the asset will be transferred to
 * @param senderVaultId ID of the vault into which the asset is from
 * @param starkSignature Transfer payload signature
 */

data class TransferRequest (

    /* Amount to transfer */
    @Json(name = "amount")
    val amount: kotlin.String,

    /* ID of the asset to transfer */
    @Json(name = "asset_id")
    val assetId: kotlin.String,

    /* Expiration timestamp for this transfer */
    @Json(name = "expiration_timestamp")
    val expirationTimestamp: kotlin.Int,

    /* Nonce of the transfer */
    @Json(name = "nonce")
    val nonce: kotlin.Int,

    /* Public stark key of the user receiving the transfer */
    @Json(name = "receiver_stark_key")
    val receiverStarkKey: kotlin.String,

    /* ID of the vault into which the asset will be transferred to */
    @Json(name = "receiver_vault_id")
    val receiverVaultId: kotlin.Int,

    /* ID of the vault into which the asset is from */
    @Json(name = "sender_vault_id")
    val senderVaultId: kotlin.Int,

    /* Transfer payload signature */
    @Json(name = "stark_signature")
    val starkSignature: kotlin.String

)

