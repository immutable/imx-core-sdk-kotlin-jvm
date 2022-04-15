package com.immutable.sdk.workflows

import androidx.annotation.VisibleForTesting
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.TokenType
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture

private const val CREATE_ORDER = "Create order"
private const val SIGNABLE_ORDER = "Signable order"

@Suppress("LongParameterList")
internal fun sell(
    asset: Erc721Asset,
    sellToken: AssetModel,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address -> getSignableOrder(asset, sellToken, address, ordersApi) }
        .thenCompose { response -> getOrderStarkSignature(response, starkSigner) }
        .thenCompose { responseToSignature ->
            createOrder(
                responseToSignature.first,
                responseToSignature.second,
                ordersApi
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

private fun getSignableOrder(
    asset: Erc721Asset,
    sellToken: AssetModel,
    address: String,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> = call(SIGNABLE_ORDER) {
    val request = GetSignableOrderRequest(
        amountBuy = convertAmount(sellToken),
        amountSell = asset.quantity,
        tokenBuy = sellToken.toToken(),
        tokenSell = createTokenSell(asset),
        user = address,
        fees = listOf(), // add support for maker/taker fees
        includeFees = true
    )
    api.getSignableOrder(request)
}

private fun createTokenSell(asset: Erc721Asset) = Token(
    data = TokenData(tokenId = asset.tokenId, tokenAddress = asset.tokenAddress),
    type = TokenType.ERC721.name
)

@VisibleForTesting
internal fun convertAmount(sellToken: AssetModel): String {
    val decimals = when (sellToken) {
        is Erc20Asset -> sellToken.decimals
        is EthAsset -> Constants.ETH_DECIMALS
        is Erc721Asset -> return sellToken.quantity
    }
    return (BigDecimal.TEN.pow(decimals) * BigDecimal(sellToken.quantity)).toBigInteger()
        .toString()
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun createOrder(
    response: GetSignableOrderResponse,
    starkSignature: String,
    api: OrdersApi
): CompletableFuture<Int> = call(CREATE_ORDER) {
    api.createOrder(
        // If the forced unwrapping below fails it will be forwarded on as an error
        CreateOrderRequest(
            amountBuy = response.amountBuy!!,
            amountSell = response.amountSell!!,
            assetIdBuy = response.assetIdBuy!!,
            assetIdSell = response.assetIdSell!!,
            expirationTimestamp = response.expirationTimestamp!!,
            nonce = response.nonce!!,
            starkKey = response.starkKey!!,
            starkSignature = starkSignature,
            vaultIdBuy = response.vaultIdBuy!!,
            vaultIdSell = response.vaultIdSell!!,
            fees = listOf(),
            includeFees = true
        )
    ).orderId!!
}
