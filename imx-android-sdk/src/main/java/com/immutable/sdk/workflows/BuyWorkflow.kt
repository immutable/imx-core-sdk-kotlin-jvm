package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.TradesApi
import com.immutable.sdk.crypto.CryptoUtil
import com.immutable.sdk.model.*
import com.immutable.sdk.utils.TokenType
import java.util.concurrent.CompletableFuture

/**
 * We need to strip the Token back to the required only fields otherwise the api validation will fail.
 */
internal fun Token.clean(): Token? = data?.let {
    when (type) {
        TokenType.ETH.name -> {
            if (it.decimals != null)
                Token(TokenData(decimals = it.decimals), type = TokenType.ETH.name)
            else
                null
        }
        TokenType.ERC20.name -> {
            if (it.tokenAddress != null && it.decimals != null)
                Token(
                    TokenData(decimals = it.decimals, tokenAddress = it.tokenAddress),
                    type = TokenType.ERC20.name
                )
            else
                null
        }
        TokenType.ERC721.name -> {
            if (it.tokenAddress != null && it.tokenId != null)
                Token(
                    TokenData(tokenId = it.tokenId, tokenAddress = it.tokenAddress),
                    type = TokenType.ERC721.name
                )
            else
                null
        }
        else -> null
    }
}

internal fun buy(
    orderId: String,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi(),
    tradesApi: TradesApi = TradesApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address -> getSignableTrade(orderId, address, ordersApi) }
        .thenCompose { response -> getStarkSignature(response, starkSigner) }
        .thenCompose { responseToSignature ->
            createTrade(
                orderId.toInt(),
                responseToSignature.first,
                responseToSignature.second,
                tradesApi
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

@Suppress("TooGenericExceptionCaught", "SwallowedException")
private fun getSignableTrade(
    orderId: String,
    address: String,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> {
    val future = CompletableFuture<GetSignableOrderResponse>()
    CompletableFuture.runAsync {
        try {
            val order = api.getOrder(
                id = orderId,
                includeFees = true,
                auxiliaryFeePercentages = null,
                auxiliaryFeeRecipients = null
            )
            when {
                order.user == address ->
                    future.completeExceptionally(ImmutableException("Cannot purchase own order"))
                order.status != "active" ->
                    future.completeExceptionally(ImmutableException("Order not available for purchase"))
                else -> {
                    val buyAmount = order.sell?.data?.quantity
                    val sellAmount = order.buy?.data?.quantity
                    when {
                        buyAmount == null ->
                            future.completeExceptionally(ImmutableException("Unable to get buy amount"))
                        sellAmount == null ->
                            future.completeExceptionally(ImmutableException("Unable to get sell amount"))
                        else -> {
                            try {
                                future.complete(
                                    api.getSignableOrder(
                                        GetSignableOrderRequest(
                                            amountBuy = buyAmount,
                                            amountSell = sellAmount,
                                            tokenBuy = order.sell.clean()!!,
                                            tokenSell = order.buy.clean()!!,
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
                    }
                }
            }
        } catch (e: Exception) {
            future.completeExceptionally(ImmutableException("Unable to get order details: ${e.message}"))
        }
    }
    return future
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun getStarkSignature(
    response: GetSignableOrderResponse,
    signer: StarkSigner
): CompletableFuture<Pair<GetSignableOrderResponse, String>> {
    val future = CompletableFuture<Pair<GetSignableOrderResponse, String>>()

    // Temporary until API returns message to sign
    CompletableFuture.runAsync {
        try {
            val msg = response.feeInfo?.let { feeInfo ->
                CryptoUtil.getLimitOrderMsgWithFee(
                    tokenSell = response.assetIdSell!!,
                    tokenBuy = response.assetIdBuy!!,
                    vaultSell = response.vaultIdSell.toString(),
                    vaultBuy = response.vaultIdBuy.toString(),
                    amountSell = response.amountSell!!,
                    amountBuy = response.amountBuy!!,
                    nonce = response.nonce.toString(),
                    expirationTimestamp = response.expirationTimestamp.toString(),
                    feeToken = feeInfo.assetId,
                    feeLimit = feeInfo.feeLimit,
                    feeVault = feeInfo.sourceVaultId.toString()
                )
            } ?: CryptoUtil.getLimitOrderMsg(
                tokenSell = response.assetIdSell!!,
                tokenBuy = response.assetIdBuy!!,
                vaultSell = response.vaultIdSell.toString(),
                vaultBuy = response.vaultIdBuy.toString(),
                amountSell = response.amountSell!!,
                amountBuy = response.amountBuy!!,
                nonce = response.nonce.toString(),
                expirationTimestamp = response.expirationTimestamp.toString()
            )

            signer.starkSign(msg).whenComplete { signature, error ->
                if (error != null)
                    future.completeExceptionally(
                        ImmutableException("Unable to generate stark signature")
                    )
                else
                    future.complete(response to signature)
            }
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }

    return future
}

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
private fun createTrade(
    orderId: Int,
    response: GetSignableOrderResponse,
    starkSignature: String,
    api: TradesApi
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()
    CompletableFuture.runAsync {
        try {
            future.complete(
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
                ).tradeId
            )
        } catch (e: Exception) {
            if (e is NullPointerException)
                future.completeExceptionally(
                    ImmutableException(
                        "Unable to complete buy: Signable order response contains unexpected null values"
                    )
                )
            else
                future.completeExceptionally(ImmutableException("Unable to complete buy: ${e.message}"))
        }
    }
    return future
}
