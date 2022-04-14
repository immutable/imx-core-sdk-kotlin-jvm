package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.CancelOrderRequest
import com.immutable.sdk.crypto.CryptoUtil
import com.immutable.sdk.stark.StarkCurve
import java.util.concurrent.CompletableFuture

private const val CANCEL_ORDER = "Cancel order"

internal fun cancel(
    orderId: String,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    starkSigner.getStarkKeys()
        .thenCompose { starkKeys ->
            val message = CryptoUtil.getCancelOrderMsg(orderId)
            val signature = StarkCurve.sign(starkKeys, message)
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

@Suppress("TooGenericExceptionCaught")
private fun cancelOrder(
    orderId: String,
    signature: String,
    api: OrdersApi
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()
    CompletableFuture.runAsync {
        try {
            future.complete(
                api.cancelOrder(
                    orderId,
                    CancelOrderRequest(signature)
                ).orderId
            )
        } catch (e: Exception) {
            future.completeExceptionally(ImmutableException.apiError(CANCEL_ORDER, e))
        }
    }
    return future
}
