package com.immutable.sdk.api.model

import com.squareup.moshi.Json

/**
 *
 *
 * @param orderId The order ID
 * @param user Ethereum address of the user who submitted the order
 * @param status Order status
 * @param buyQuantity Quantity of this asset
 * @param buyDecimals Number of decimals supported by this asset
 */
data class SellOrders(

    /* The order ID */
    @Json(name = "order_id")
    val orderId: kotlin.Int? = null,

    /* Ethereum address of the user who submitted the order */
    @Json(name = "user")
    val user: kotlin.String? = null,

    /* Order status */
    @Json(name = "status")
    val status: kotlin.String? = null,

    /* Quantity of this asset */
    @Json(name = "buy_quantity")
    val buyQuantity: kotlin.String? = null,

    /* Number of decimals supported by this asset */
    @Json(name = "buy_decimals")
    val buyDecimals: kotlin.Int? = null

)
