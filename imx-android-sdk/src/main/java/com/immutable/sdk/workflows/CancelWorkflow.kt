package com.immutable.sdk.workflows

import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.CancelOrderRequest
import com.immutable.sdk.api.model.GetSignableCancelOrderRequest
import com.immutable.sdk.stark.StarkCurve
import java.util.concurrent.CompletableFuture

private const val SIGNABLE_CANCEL_ORDER = "Signable cancel order"
private const val CANCEL_ORDER = "Cancel order"

internal fun cancel(
    orderId: String,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    starkSigner.getStarkKeys()
        .thenCompose { starkKeys ->
            getSignableCancelOrder(orderId, ordersApi).thenApply { starkKeys to it }
        }
        .thenCompose { (starkKeys, payloadHash) ->
            val signature = StarkCurve.sign(starkKeys, payloadHash)
            CompletableFuture.completedFuture(signature)
        }
        .thenCompose { signature ->
            cancelOrder(orderId, signature, ordersApi)
        }
        .whenComplete { cancelledOrderId, error ->
            // Forward any exceptions from the compose chain
            if (error != null)
                future.completeExceptionally(error)
            else
                future.complete(cancelledOrderId)
        }

    return future
}

private fun getSignableCancelOrder(orderId: String, api: OrdersApi) = call(SIGNABLE_CANCEL_ORDER) {
    api.getSignableCancelOrder(GetSignableCancelOrderRequest(orderId = orderId.toInt())).payloadHash!!
}

private fun cancelOrder(
    orderId: String,
    signature: String,
    api: OrdersApi
): CompletableFuture<Int> = call(CANCEL_ORDER) {
    api.cancelOrder(
        orderId,
        CancelOrderRequest(orderId.toInt(), signature),
        xImxEthAddress = null,
        xImxEthSignature = null
    ).orderId!!
}
