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

import com.immutable.sdk.api.model.SignableToken

import com.squareup.moshi.Json

/**
 * 
 *
 * @param amount Amount of the asset being transferred
 * @param assetId ID of the asset being transferred
 * @param expirationTimestamp Timestamp when this transfer will expire
 * @param nonce Nonce of the transfer
 * @param payloadHash Hash of the payload to be signed for transfer
 * @param receiverStarkKey Receiver of the transfer
 * @param receiverVaultId ID of the vault being transferred to
 * @param senderVaultId ID of the vault being transferred from
 * @param token 
 */

data class SignableTransferResponseDetails (

    /* Amount of the asset being transferred */
    @Json(name = "amount")
    val amount: kotlin.String? = null,

    /* ID of the asset being transferred */
    @Json(name = "asset_id")
    val assetId: kotlin.String? = null,

    /* Timestamp when this transfer will expire */
    @Json(name = "expiration_timestamp")
    val expirationTimestamp: kotlin.Int? = null,

    /* Nonce of the transfer */
    @Json(name = "nonce")
    val nonce: kotlin.Int? = null,

    /* Hash of the payload to be signed for transfer */
    @Json(name = "payload_hash")
    val payloadHash: kotlin.String? = null,

    /* Receiver of the transfer */
    @Json(name = "receiver_stark_key")
    val receiverStarkKey: kotlin.String? = null,

    /* ID of the vault being transferred to */
    @Json(name = "receiver_vault_id")
    val receiverVaultId: kotlin.Int? = null,

    /* ID of the vault being transferred from */
    @Json(name = "sender_vault_id")
    val senderVaultId: kotlin.Int? = null,

    @Json(name = "token")
    val token: SignableToken? = null

)
