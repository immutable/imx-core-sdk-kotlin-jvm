package com.immutable.sdk.api.model

import com.squareup.moshi.Json

/**
 *
 *
 * @param buyOrders Buy orders for this asset
 * @param sellOrders Sell orders for this asset
 */
data class AssetOrderDetails(

    /* Buy orders for this asset */
    @Json(name = "buy_orders")
    val buyOrders: List<Any>? = null,

    /* Sell orders for this asset */
    @Json(name = "sell_orders")
    val sellOrders: List<SellOrders>? = null
)
