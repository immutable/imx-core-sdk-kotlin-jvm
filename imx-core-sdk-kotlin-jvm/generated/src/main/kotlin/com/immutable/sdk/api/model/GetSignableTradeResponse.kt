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

import com.immutable.sdk.api.model.FeeInfo

import com.squareup.moshi.Json

/**
 * 
 *
 * @param amountBuy Amount to buy
 * @param amountSell Amount to sell
 * @param assetIdBuy ID of the asset to buy
 * @param assetIdSell ID of the asset to sell
 * @param expirationTimestamp Expiration timestamp for this order
 * @param nonce Nonce of the order
 * @param payloadHash Payload Hash
 * @param signableMessage Message to sign with L1 wallet to confirm trade request
 * @param starkKey Public stark key of the created user
 * @param vaultIdBuy ID of the vault into which the bought asset will be placed
 * @param vaultIdSell ID of the vault to sell from
 * @param feeInfo 
 */

data class GetSignableTradeResponse (

    /* Amount to buy */
    @Json(name = "amount_buy")
    val amountBuy: kotlin.String,

    /* Amount to sell */
    @Json(name = "amount_sell")
    val amountSell: kotlin.String,

    /* ID of the asset to buy */
    @Json(name = "asset_id_buy")
    val assetIdBuy: kotlin.String,

    /* ID of the asset to sell */
    @Json(name = "asset_id_sell")
    val assetIdSell: kotlin.String,

    /* Expiration timestamp for this order */
    @Json(name = "expiration_timestamp")
    val expirationTimestamp: kotlin.Int,

    /* Nonce of the order */
    @Json(name = "nonce")
    val nonce: kotlin.Int,

    /* Payload Hash */
    @Json(name = "payload_hash")
    val payloadHash: kotlin.String,

    /* Message to sign with L1 wallet to confirm trade request */
    @Json(name = "signable_message")
    val signableMessage: kotlin.String,

    /* Public stark key of the created user */
    @Json(name = "stark_key")
    val starkKey: kotlin.String,

    /* ID of the vault into which the bought asset will be placed */
    @Json(name = "vault_id_buy")
    val vaultIdBuy: kotlin.Int,

    /* ID of the vault to sell from */
    @Json(name = "vault_id_sell")
    val vaultIdSell: kotlin.Int,

    @Json(name = "fee_info")
    val feeInfo: FeeInfo? = null

)

