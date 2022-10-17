package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.formatQuantity
import java.util.concurrent.CompletableFuture

private const val CREATE_ORDER = "Create order"
private const val SIGNABLE_ORDER = "Signable order"

@Suppress("LongParameterList")
internal fun createOrder(
    asset: Erc721Asset,
    sellToken: AssetModel,
    fees: List<FeeEntry>,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<CreateOrderResponse> {
    val future = CompletableFuture<CreateOrderResponse>()

    signer.getAddress().thenApply { address -> WorkflowSignatures(address) }
        .thenCompose { signatures ->
            getSignableOrder(asset, sellToken, signatures.ethAddress, fees, ordersApi)
                .thenApply { response -> signatures to response }
        }
        .thenCompose { (signatures, response) ->
            starkSigner.signMessage(response.payloadHash)
                .thenApply { signature -> signatures.apply { starkSignature = signature } to response }
        }
        .thenCompose { (signatures, response) ->
            signer.signMessage(response.signableMessage)
                .thenApply { signature -> signatures.apply { ethSignature = signature } to response }
        }
        .thenCompose { (signatures, response) -> createOrder(response, signatures, fees, ordersApi) }
        .whenComplete { response, error ->
            // Forward any exceptions from the compose chain
            if (error != null) future.completeExceptionally(error)
            else future.complete(response)
        }

    return future
}

@Suppress("LongParameterList")
private fun getSignableOrder(
    asset: Erc721Asset,
    sellToken: AssetModel,
    address: String,
    fees: List<FeeEntry>,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> = call(SIGNABLE_ORDER) {
    val request = GetSignableOrderRequest(
        amountBuy = sellToken.formatQuantity(),
        amountSell = asset.quantity,
        tokenBuy = sellToken.toSignableToken(),
        tokenSell = asset.toSignableToken(),
        user = address,
        fees = fees
    )
    api.getSignableOrder(request)
}

@Suppress(
    "TooGenericExceptionCaught",
    "SwallowedException",
    "InstanceOfCheckForException",
    "LongParameterList"
)
private fun createOrder(
    response: GetSignableOrderResponse,
    signatures: WorkflowSignatures,
    fees: List<FeeEntry>,
    api: OrdersApi
): CompletableFuture<CreateOrderResponse> = call(CREATE_ORDER) {
    api.createOrder(
        CreateOrderRequest(
            amountBuy = response.amountBuy,
            amountSell = response.amountSell,
            assetIdBuy = response.assetIdBuy,
            assetIdSell = response.assetIdSell,
            expirationTimestamp = response.expirationTimestamp,
            nonce = response.nonce,
            starkKey = response.starkKey,
            starkSignature = signatures.starkSignature,
            vaultIdBuy = response.vaultIdBuy,
            vaultIdSell = response.vaultIdSell,
            fees = fees,
            // Always include fees in order, the 'fees' field will either have fees details or nothing at all
            includeFees = true
        ),
        xImxEthAddress = signatures.ethAddress,
        xImxEthSignature = signatures.serialisedEthSignature
    )
}
