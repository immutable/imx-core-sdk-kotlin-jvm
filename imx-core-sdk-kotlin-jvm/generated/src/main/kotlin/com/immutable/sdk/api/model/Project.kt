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
 * @param collectionLimitExpiresAt The current period expiry date for collection limit
 * @param collectionMonthlyLimit The total monthly collection limit
 * @param collectionRemaining The number of collection remaining in the current period
 * @param companyName The company name
 * @param contactEmail The project contact email
 * @param id The project ID
 * @param mintLimitExpiresAt The current period expiry date for mint operation limit
 * @param mintMonthlyLimit The total monthly mint operation limit
 * @param mintRemaining The number of mint operation remaining in the current period
 * @param name The project name
 */

data class Project (

    /* The current period expiry date for collection limit */
    @Json(name = "collection_limit_expires_at")
    val collectionLimitExpiresAt: kotlin.String,

    /* The total monthly collection limit */
    @Json(name = "collection_monthly_limit")
    val collectionMonthlyLimit: kotlin.Int,

    /* The number of collection remaining in the current period */
    @Json(name = "collection_remaining")
    val collectionRemaining: kotlin.Int,

    /* The company name */
    @Json(name = "company_name")
    val companyName: kotlin.String,

    /* The project contact email */
    @Json(name = "contact_email")
    val contactEmail: kotlin.String,

    /* The project ID */
    @Json(name = "id")
    val id: kotlin.Int,

    /* The current period expiry date for mint operation limit */
    @Json(name = "mint_limit_expires_at")
    val mintLimitExpiresAt: kotlin.String,

    /* The total monthly mint operation limit */
    @Json(name = "mint_monthly_limit")
    val mintMonthlyLimit: kotlin.Int,

    /* The number of mint operation remaining in the current period */
    @Json(name = "mint_remaining")
    val mintRemaining: kotlin.Int,

    /* The project name */
    @Json(name = "name")
    val name: kotlin.String

)
