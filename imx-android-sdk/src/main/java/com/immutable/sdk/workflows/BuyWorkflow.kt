package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.TradesApi
import com.immutable.sdk.api.model.CreateTradeRequest
import com.immutable.sdk.api.model.GetSignableOrderRequest
import com.immutable.sdk.api.model.GetSignableOrderResponse
import com.immutable.sdk.api.model.Order
import com.immutable.sdk.extensions.clean
import com.immutable.sdk.extensions.combine
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
        .combine(getOrderDetails(orderId, ordersApi)) { address, order ->
            getSignableTrade(order, address, ordersApi)
        }
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

private fun getOrderDetails(
    orderId: String,
    api: OrdersApi
): CompletableFuture<Order> =
    call(ORDER_DETAILS) {
        api.getOrder(
            id = orderId,
            includeFees = true,
            auxiliaryFeePercentages = null,
            auxiliaryFeeRecipients = null
        )
    }

private fun getSignableTrade(
    order: Order,
    address: String,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> = when {
    order.user == address ->
        completeExceptionally(ImmutableException.invalidRequest("Cannot purchase own order"))
    order.status != OrderStatus.Active.value ->
        completeExceptionally(ImmutableException.invalidRequest("Order not available for purchase"))
    else -> call(SIGNABLE_ORDER) {
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
    }
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun createTrade(
    orderId: Int,
    response: GetSignableOrderResponse,
    starkSignature: String,
    api: TradesApi
): CompletableFuture<Int> = call(CREATE_TRADE) {
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
    ).tradeId!!
}
