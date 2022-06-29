package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.CancelOrderRequest
import com.immutable.sdk.api.model.CancelOrderResponse
import com.immutable.sdk.api.model.GetSignableCancelOrderRequest
import java.util.concurrent.CompletableFuture

private const val SIGNABLE_CANCEL_ORDER = "Signable cancel order"
private const val CANCEL_ORDER = "Cancel order"

internal fun cancel(
    orderId: String,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<CancelOrderResponse> {
    val future = CompletableFuture<CancelOrderResponse>()

    signer.getAddress().thenApply { address -> WorkflowSignatures(address) }
        .thenCompose { signatures ->
            getSignableCancelOrder(orderId, ordersApi).thenApply { response -> signatures to response }
        }
        .thenCompose { (signatures, response) ->
            starkSigner.signMessage(response.payloadHash)
                .thenApply { signature -> signatures.apply { starkSignature = signature } to response }
        }
        .thenCompose { (signatures, response) ->
            signer.signMessage(response.signableMessage)
                .thenApply { signature -> signatures.apply { ethSignature = signature } }
        }
        .thenCompose { signatures -> cancelOrder(orderId, signatures, ordersApi) }
        .whenComplete { response, error ->
            // Forward any exceptions from the compose chain
            if (error != null)
                future.completeExceptionally(error)
            else
                future.complete(response)
        }

    return future
}

private fun getSignableCancelOrder(orderId: String, api: OrdersApi) = call(SIGNABLE_CANCEL_ORDER) {
    api.getSignableCancelOrder(GetSignableCancelOrderRequest(orderId = orderId.toInt()))
}

private fun cancelOrder(
    orderId: String,
    signatures: WorkflowSignatures,
    api: OrdersApi
): CompletableFuture<CancelOrderResponse> = call(CANCEL_ORDER) {
    api.cancelOrder(
        orderId,
        CancelOrderRequest(orderId.toInt(), signatures.starkSignature),
        xImxEthAddress = signatures.ethAddress,
        xImxEthSignature = signatures.serialisedEthSignature
    )
}
