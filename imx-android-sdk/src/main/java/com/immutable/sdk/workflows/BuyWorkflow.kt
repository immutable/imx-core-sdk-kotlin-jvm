package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.TradesApi
import com.immutable.sdk.extensions.clean
import com.immutable.sdk.api.model.CreateTradeRequest
import com.immutable.sdk.api.model.GetSignableOrderRequest
import com.immutable.sdk.api.model.GetSignableOrderResponse
import com.immutable.sdk.model.OrderStatus
import java.util.concurrent.CompletableFuture

private const val SIGNABLE_ORDER = "Signable order"
private const val ORDER_DETAILS = "Order details"
private const val CREATE_TRADE = "Create trade"

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
            // Forward any exceptions from the compose chain
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
                    future.completeExceptionally(
                        ImmutableException.invalidRequest("Cannot purchase own order")
                    )
                order.status != OrderStatus.Active.value ->
                    future.completeExceptionally(
                        ImmutableException.invalidRequest("Order not available for purchase")
                    )
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
                                ImmutableException.invalidResponse(SIGNABLE_ORDER, e)
                            )
                        else
                            future.completeExceptionally(
                                ImmutableException.apiError(SIGNABLE_ORDER, e)
                            )
                    }
                }
            }
        } catch (e: Exception) {
            future.completeExceptionally(ImmutableException.apiError(ORDER_DETAILS, e))
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
                future.completeExceptionally(ImmutableException.invalidResponse(CREATE_TRADE, e))
            else
                future.completeExceptionally(ImmutableException.apiError(CREATE_TRADE, e))
        }
    }
    return future
}
