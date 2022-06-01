package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.CreateOrderRequest
import com.immutable.sdk.api.model.FeeEntry
import com.immutable.sdk.api.model.GetSignableOrderRequest
import com.immutable.sdk.api.model.GetSignableOrderResponse
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.formatQuantity
import java.util.concurrent.CompletableFuture

private const val CREATE_ORDER = "Create order"
private const val SIGNABLE_ORDER = "Signable order"

@Suppress("LongParameterList")
internal fun sell(
    asset: Erc721Asset,
    sellToken: AssetModel,
    fees: List<FeeEntry>,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address ->
            getSignableOrder(asset, sellToken, address, fees, ordersApi)
        }
        .thenCompose { (payloadHash, response) ->
            starkSigner.signMessage(payloadHash).thenApply { response to it }
        }
        .thenCompose { (response, signature) -> createOrder(response, signature, fees, ordersApi) }
        .whenComplete { tradeId, error ->
            // Forward any exceptions from the compose chain
            if (error != null) future.completeExceptionally(error)
            else future.complete(tradeId)
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
): CompletableFuture<Pair<String, GetSignableOrderResponse>> = call(SIGNABLE_ORDER) {
    val request = GetSignableOrderRequest(
        amountBuy = sellToken.formatQuantity(),
        amountSell = asset.quantity,
        tokenBuy = sellToken.toSignableToken(),
        tokenSell = asset.toSignableToken(),
        user = address,
        fees = fees
    )
    val response = api.getSignableOrder(request)
    // Unwrapping payload hash here so if it's null, the correct exception is thrown
    response.payloadHash to response
}

@Suppress(
    "TooGenericExceptionCaught",
    "SwallowedException",
    "InstanceOfCheckForException",
    "LongParameterList"
)
private fun createOrder(
    response: GetSignableOrderResponse,
    starkSignature: String,
    fees: List<FeeEntry>,
    api: OrdersApi
): CompletableFuture<Int> = call(CREATE_ORDER) {
    api.createOrder(
        CreateOrderRequest(
            amountBuy = response.amountBuy,
            amountSell = response.amountSell,
            assetIdBuy = response.assetIdBuy,
            assetIdSell = response.assetIdSell,
            expirationTimestamp = response.expirationTimestamp,
            nonce = response.nonce,
            starkKey = response.starkKey,
            starkSignature = starkSignature,
            vaultIdBuy = response.vaultIdBuy,
            vaultIdSell = response.vaultIdSell,
            fees = fees,
            // Always include fees in order, the 'fees' field will either have fees details or nothing at all
            includeFees = true
        ),
        xImxEthAddress = null,
        xImxEthSignature = null
    ).orderId
}
