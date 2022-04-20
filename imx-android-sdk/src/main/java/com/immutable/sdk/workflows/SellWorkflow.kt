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
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.TokenType
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture

private const val CREATE_ORDER = "Create order"
private const val SIGNABLE_ORDER = "Signable order"
private const val MAKER_ORDER_FEE_CALCULATION_DECIMALS = 2

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
        .thenCompose { address -> getSignableOrder(asset, sellToken, address, fees, ordersApi) }
        .thenCompose { response -> starkSigner.getStarkKeys().thenApply { response to it } }
        .thenApply { (response, starkKeys) -> response to StarkCurve.sign(starkKeys, response.payloadHash!!) }
        .thenCompose { (response, signature) -> createOrder(response, signature, fees, ordersApi) }
        .whenComplete { tradeId, error ->
            // Forward any exceptions from the compose chain
            if (error != null) future.completeExceptionally(error)
            else future.complete(tradeId)
        }

    return future
}

private fun calculateMakerOrderFee(
    amount: BigDecimal,
    fees: List<FeeEntry>
): BigDecimal {
    var totalFee = BigDecimal.ZERO
    // TODO royalties
    fees.forEach { fee ->
        val percentage = BigDecimal(fee.feePercentage?.div(100) ?: 0.0)
        totalFee += amount.multiply(percentage)
    }
    return totalFee.setScale(MAKER_ORDER_FEE_CALCULATION_DECIMALS, BigDecimal.ROUND_HALF_EVEN)
}

@Suppress("LongParameterList")
private fun getSignableOrder(
    asset: Erc721Asset,
    sellToken: AssetModel,
    address: String,
    fees: List<FeeEntry>,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> = call(SIGNABLE_ORDER) {
    val totalFees = calculateMakerOrderFee(BigDecimal(sellToken.quantity), fees)
    val request = GetSignableOrderRequest(
        amountBuy = formatAmount(sellToken, totalFees),
        amountSell = asset.quantity,
        tokenBuy = sellToken.toSignableToken(),
        tokenSell = createTokenSell(asset),
        user = address,
        fees = fees
    )
    api.getSignableOrder(request)
}

private fun createTokenSell(asset: Erc721Asset) = SignableToken(
    data = TokenData(tokenId = asset.tokenId, tokenAddress = asset.tokenAddress),
    type = TokenType.ERC721.name
)

@VisibleForTesting
internal fun formatAmount(sellToken: AssetModel, totalFees: BigDecimal): String {
    val decimals = when (sellToken) {
        is Erc20Asset -> sellToken.decimals
        is EthAsset -> Constants.ETH_DECIMALS
        is Erc721Asset -> return sellToken.quantity
    }
    val sellTokenAmount = BigDecimal(sellToken.quantity) + totalFees
    return (BigDecimal.TEN.pow(decimals) * sellTokenAmount).toBigInteger()
        .toString()
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
            fees = fees.map { FeeEntry(address = it.address, feePercentage = it.feePercentage) },
            // Always include fees in order, the 'fees' field will either have fees details or nothing at all
            includeFees = true
        ),
        xImxEthAddress = null,
        xImxEthSignature = null
    ).orderId!!
}
