package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.TradesApi
import com.immutable.sdk.extensions.clean
import com.immutable.sdk.model.CreateTradeRequest
import com.immutable.sdk.model.GetSignableOrderRequest
import com.immutable.sdk.model.GetSignableOrderResponse
import java.util.concurrent.CompletableFuture

internal fun buy(
    orderId: String,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi(),
    tradesApi: TradesApi = TradesApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address -> getSignableTrade(orderId, address, ordersApi) }
        .thenCompose { response -> getOrderStarkSignature(response, starkSigner) }
        .thenCompose { responseToSignature ->
            createTrade(
                orderId.toInt(),
                responseToSignature.first,
                responseToSignature.second,
                tradesApi
            )
        }.whenComplete { tradeId, error ->
            // Forward any exceptions from the compose chain to the login future
            if (error != null)
                future.completeExceptionally(error)
            else
                future.complete(tradeId)
        }

    return future
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun getSignableTrade(
    orderId: String,
    address: String,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> {
    val future = CompletableFuture<GetSignableOrderResponse>()
    CompletableFuture.runAsync {
        try {
            val order = api.getOrder(
                id = orderId,
                includeFees = true,
                auxiliaryFeePercentages = null,
                auxiliaryFeeRecipients = null
            )
            when {
                order.user == address ->
                    future.completeExceptionally(ImmutableException("Cannot purchase own order"))
                order.status != "active" ->
                    future.completeExceptionally(ImmutableException("Order not available for purchase"))
                else -> {
                    try {
                        future.complete(
                            api.getSignableOrder(
                                GetSignableOrderRequest(
                                    amountBuy = order.sell!!.data!!.quantity!!,
                                    amountSell = order.buy!!.data!!.quantity!!,
                                    tokenBuy = order.sell.clean()!!,
                                    tokenSell = order.buy.clean()!!,
                                    user = address,
                                    fees = listOf(), // add support for maker/taker fees
                                    includeFees = true
                                )
                            )
                        )
                    } catch (e: Exception) {
                        if (e is NullPointerException)
                            future.completeExceptionally(
                                ImmutableException("Order is missing buy and/or sell data: ${e.message}")
                            )
                        else
                            future.completeExceptionally(
                                ImmutableException("Unable to get signable order: ${e.message}")
                            )
                    }
                }
            }
        } catch (e: Exception) {
            future.completeExceptionally(ImmutableException("Unable to get order details: ${e.message}"))
        }
    }
    return future
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun createTrade(
    orderId: Int,
    response: GetSignableOrderResponse,
    starkSignature: String,
    api: TradesApi
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()
    CompletableFuture.runAsync {
        try {
            future.complete(
                api.createTrade(
                    // If the forced unwrapping below fails it will be forwarded on as an error
                    CreateTradeRequest(
                        amountBuy = response.amountBuy!!,
                        amountSell = response.amountSell!!,
                        assetIdBuy = response.assetIdBuy!!,
                        assetIdSell = response.assetIdSell!!,
                        expirationTimestamp = response.expirationTimestamp!!,
                        nonce = response.nonce!!,
                        orderId = orderId,
                        starkKey = response.starkKey!!,
                        starkSignature = starkSignature,
                        vaultIdBuy = response.vaultIdBuy!!,
                        vaultIdSell = response.vaultIdSell!!,
                        feeInfo = response.feeInfo,
                        fees = listOf(),
                        includeFees = true
                    )
                ).tradeId
            )
        } catch (e: Exception) {
            if (e is NullPointerException)
                future.completeExceptionally(
                    ImmutableException(
                        "Unable to complete buy: Signable order response contains unexpected null values"
                    )
                )
            else
                future.completeExceptionally(ImmutableException("Unable to complete buy: ${e.message}"))
        }
    }
    return future
}
