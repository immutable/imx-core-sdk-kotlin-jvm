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
 * @param amount Amount to withdraw
 * @param assetId The ID of asset the user is withdrawing
 * @param nonce Nonce of the withdrawal
 * @param starkKey Public stark key of the withdrawing user
 * @param starkSignature Payload signature
 * @param vaultId The ID of the vault the asset belong to
 */

data class CreateWithdrawalRequest (

    /* Amount to withdraw */
    @Json(name = "amount")
    val amount: kotlin.String,

    /* The ID of asset the user is withdrawing */
    @Json(name = "asset_id")
    val assetId: kotlin.String,

    /* Nonce of the withdrawal */
    @Json(name = "nonce")
    val nonce: kotlin.Int,

    /* Public stark key of the withdrawing user */
    @Json(name = "stark_key")
    val starkKey: kotlin.String,

    /* Payload signature */
    @Json(name = "stark_signature")
    val starkSignature: kotlin.String,

    /* The ID of the vault the asset belong to */
    @Json(name = "vault_id")
    val vaultId: kotlin.Int

)

