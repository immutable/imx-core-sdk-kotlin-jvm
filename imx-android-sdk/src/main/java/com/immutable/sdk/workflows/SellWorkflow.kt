package com.immutable.sdk.workflows

import androidx.annotation.VisibleForTesting
import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.model.*
import com.immutable.sdk.utils.TokenType
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList")
internal fun sell(
    tokenAddress: String,
    tokenId: String,
    sellTokenAmount: String,
    sellTokenAddress: String?, // If this is null, the default sell token address will be for ETH
    sellTokenDecimals: Int?, // If this is null, the default value will be the number of decimals for ETH
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address ->
            getSignableOrder(
                tokenAddress,
                tokenId,
                sellTokenAmount,
                sellTokenAddress,
                sellTokenDecimals,
                address,
                ordersApi
            )
        }
        .thenCompose { response -> getStarkSignature(response, starkSigner) }
        .thenCompose { responseToSignature ->
            createOrder(
                responseToSignature.first,
                responseToSignature.second,
                ordersApi
            )
        }.whenComplete { tradeId, error ->
            // Forward any exceptions from the compose chain to the login future
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
    "InstanceOfCheckForException",
    "LongParameterList"
)
private fun getSignableOrder(
    tokenAddress: String,
    tokenId: String,
    sellTokenAmount: String,
    sellTokenAddress: String?,
    sellTokenDecimals: Int?,
    address: String,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> {
    val future = CompletableFuture<GetSignableOrderResponse>()
    CompletableFuture.runAsync {
        try {
            future.complete(
                api.getSignableOrder(
                    GetSignableOrderRequest(
                        amountBuy = convertAmount(sellTokenAmount, sellTokenDecimals),
                        amountSell = "1",
                        tokenBuy = if (sellTokenAddress != null)
                            Token(
                                data = TokenData(
                                    tokenAddress = sellTokenAddress,
                                    decimals = sellTokenDecimals
                                ),
                                type = TokenType.ERC20.name
                            )
                        else Token(
                            data = TokenData(decimals = 18),
                            type = TokenType.ETH.name
                        ),
                        tokenSell = Token(
                            data = TokenData(
                                tokenId = tokenId,
                                tokenAddress = tokenAddress
                            ),
                            type = TokenType.ERC721.name
                        ),
                        user = address,
                        fees = listOf(), // add support for maker/taker fees
                        includeFees = true
                    )
                )
            )
        } catch (e: Exception) {
            future.completeExceptionally(
                ImmutableException("Unable to get signable order: ${e.message}")
            )
        }
    }
    return future
}

@Suppress("MagicNumber")
@VisibleForTesting
internal fun convertAmount(value: String, decimals: Int?): String =
    (BigDecimal(10).pow(decimals ?: 18) * BigDecimal(value)).toBigInteger().toString()

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
