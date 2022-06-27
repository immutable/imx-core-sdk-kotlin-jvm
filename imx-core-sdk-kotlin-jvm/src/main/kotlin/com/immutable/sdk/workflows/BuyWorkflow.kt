package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.TradesApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.OrderStatus
import java.util.concurrent.CompletableFuture

private const val SIGNABLE_TRADE = "Signable trade"
private const val ORDER_DETAILS = "Order details"
private const val CREATE_TRADE = "Create trade"
private const val COMMA = ","

@Suppress("LongParameterList")
internal fun buy(
    orderId: String,
    fees: List<FeeEntry>,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi(),
    tradesApi: TradesApi = TradesApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address ->
            getOrderDetails(orderId, fees, ordersApi).thenApply { address to it }
        }
        .thenCompose { (address, order) -> getSignableTrade(order, address, fees, tradesApi) }
        .thenCompose { (payloadHash, response) ->
            starkSigner.signMessage(payloadHash).thenApply { response to it }
        }
        .thenCompose { (response, signature) ->
            createTrade(orderId.toInt(), response, fees, signature, tradesApi)
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
    fees: List<FeeEntry>,
    api: OrdersApi
): CompletableFuture<Order> =
    call(ORDER_DETAILS) {
        api.getOrder(
            id = orderId,
            includeFees = true,
            auxiliaryFeePercentages = fees.joinToString(separator = COMMA) {
                it.feePercentage?.toString()!!
            },
            auxiliaryFeeRecipients = fees.joinToString(separator = COMMA) { it.address!! },
        )
    }

private fun getSignableTrade(
    order: Order,
    address: String,
    fees: List<FeeEntry>,
    api: TradesApi
): CompletableFuture<Pair<String, GetSignableTradeResponse>> = when {
    order.user == address ->
        completeExceptionally(ImmutableException.invalidRequest("Cannot purchase own order"))
    order.status != OrderStatus.Active.value ->
        completeExceptionally(ImmutableException.invalidRequest("Order not available for purchase"))
    else -> call(SIGNABLE_TRADE) {
        val response = api.getSignableTrade(
            GetSignableTradeRequest(
                orderId = order.orderId,
                user = address,
                fees = fees
            )
        )
        // Unwrapping payload hash here so if it's null, the correct exception is thrown
        response.payloadHash to response
    }
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun createTrade(
    orderId: Int,
    response: GetSignableTradeResponse,
    fees: List<FeeEntry>,
    starkSignature: String,
    api: TradesApi
): CompletableFuture<Int> = call(CREATE_TRADE) {
    api.createTrade(
        CreateTradeRequestV1(
            amountBuy = response.amountBuy,
            amountSell = response.amountSell,
            assetIdBuy = response.assetIdBuy,
            assetIdSell = response.assetIdSell,
            expirationTimestamp = response.expirationTimestamp,
            nonce = response.nonce,
            orderId = orderId,
            starkKey = response.starkKey,
            starkSignature = starkSignature,
            vaultIdBuy = response.vaultIdBuy,
            vaultIdSell = response.vaultIdSell,
            feeInfo = response.feeInfo,
            fees = fees,
            // Always include fees, the 'fees' field will contain fees details or nothing at all
            includeFees = true
        ),
        xImxEthAddress = null,
        xImxEthSignature = null
    ).tradeId
}