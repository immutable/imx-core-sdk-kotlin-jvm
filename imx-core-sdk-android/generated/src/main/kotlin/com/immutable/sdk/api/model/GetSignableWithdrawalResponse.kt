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
 * @param amount Amount of the token we are withdrawing
 * @param assetId ID of the asset to be withdrawn
 * @param nonce Nonce of this transaction
 * @param payloadHash Encoded payload hash
 * @param signableMessage Message to sign with L1 wallet to verity withdrawal request
 * @param starkKey Public stark key of this user
 * @param vaultId ID of the vault we are withdrawing from
 */

data class GetSignableWithdrawalResponse (

    /* Amount of the token we are withdrawing */
    @Json(name = "amount")
    val amount: kotlin.String,

    /* ID of the asset to be withdrawn */
    @Json(name = "asset_id")
    val assetId: kotlin.String,

    /* Nonce of this transaction */
    @Json(name = "nonce")
    val nonce: kotlin.Int,

    /* Encoded payload hash */
    @Json(name = "payload_hash")
    val payloadHash: kotlin.String,

    /* Message to sign with L1 wallet to verity withdrawal request */
    @Json(name = "signable_message")
    val signableMessage: kotlin.String,

    /* Public stark key of this user */
    @Json(name = "stark_key")
    val starkKey: kotlin.String,

    /* ID of the vault we are withdrawing from */
    @Json(name = "vault_id")
    val vaultId: kotlin.Int

)

