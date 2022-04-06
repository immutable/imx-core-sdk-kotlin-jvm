package com.immutable.sdk.workflows

import androidx.annotation.VisibleForTesting
import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.model.*
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.Constants.ERC721_AMOUNT
import com.immutable.sdk.utils.TokenType
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList")
internal fun sell(
    asset: Erc721Asset,
    sellAmount: String,
    sellToken: SellToken,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address ->
            getSignableOrder(
                asset,
                sellAmount,
                sellToken,
                address,
                ordersApi
            )
        }
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

@Suppress(
    "TooGenericExceptionCaught",
    "SwallowedException",
    "InstanceOfCheckForException"
)
private fun getSignableOrder(
    asset: Erc721Asset,
    sellAmount: String,
    sellToken: SellToken,
    address: String,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> {
    val future = CompletableFuture<GetSignableOrderResponse>()
    CompletableFuture.runAsync {
        try {
            val request = GetSignableOrderRequest(
                amountBuy = convertAmount(sellAmount, sellToken),
                amountSell = ERC721_AMOUNT,
                tokenBuy = createTokenBuy(sellToken),
                tokenSell = createTokenSell(asset),
                user = address,
                fees = listOf(), // add support for maker/taker fees
                includeFees = true
            )
            future.complete(api.getSignableOrder(request))
        } catch (e: Exception) {
            future.completeExceptionally(
                ImmutableException("Unable to get signable order: ${e.message}")
            )
        }
    }
    return future
}

private fun createTokenBuy(sellToken: SellToken) = when (sellToken) {
    SellToken.ETH -> Token(
        data = TokenData(decimals = Constants.ETH_DECIMALS),
        type = TokenType.ETH.name
    )
    is SellToken.ERC20 -> Token(
        data = TokenData(tokenAddress = sellToken.tokenAddress, decimals = sellToken.decimals),
        type = TokenType.ERC20.name
    )
}

private fun createTokenSell(asset: Erc721Asset) = Token(
    data = TokenData(tokenId = asset.tokenId, tokenAddress = asset.tokenAddress),
    type = TokenType.ERC721.name
)

@VisibleForTesting
internal fun convertAmount(value: String, sellToken: SellToken): String {
    val decimals = when (sellToken) {
        is SellToken.ERC20 -> sellToken.decimals
        SellToken.ETH -> Constants.ETH_DECIMALS
    }
    return (BigDecimal.TEN.pow(decimals) * BigDecimal(value)).toBigInteger()
        .toString()
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun createOrder(
    response: GetSignableOrderResponse,
    starkSignature: String,
    api: OrdersApi
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()
    CompletableFuture.runAsync {
        try {
            future.complete(
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
                ).orderId
            )
        } catch (e: Exception) {
            if (e is NullPointerException)
                future.completeExceptionally(
                    ImmutableException(
                        "Unable to complete sell: Signable order response contains unexpected null values"
                    )
                )
            else
                future.completeExceptionally(ImmutableException("Unable to complete sell: ${e.message}"))
        }
    }
    return future
}
