package com.immutable.sdk.workflows

import androidx.annotation.VisibleForTesting
import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.AssetsApi
import com.immutable.sdk.api.OrdersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.utils.Constants
import com.immutable.sdk.utils.TokenType
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture

private const val CREATE_ORDER = "Create order"
private const val SIGNABLE_ORDER = "Signable order"
private const val GET_ASSETS = "Get assets"

@VisibleForTesting
internal const val FEE_TYPE_ROYALTY = "royalty"

@Suppress("LongParameterList")
internal fun sell(
    asset: Erc721Asset,
    sellToken: AssetModel,
    fees: List<FeeEntry>,
    signer: Signer,
    starkSigner: StarkSigner,
    ordersApi: OrdersApi = OrdersApi(),
    assetsApi: AssetsApi = AssetsApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address ->
            getFees(asset, sellToken, fees, assetsApi).thenApply { address to it }
        }
        .thenCompose { (address, totalFees) ->
            getSignableOrder(asset, sellToken, address, fees, totalFees, ordersApi)
        }
        .thenCompose { response -> starkSigner.getStarkKeys().thenApply { response to it } }
        .thenApply { (response, starkKeys) ->
            response to StarkCurve.sign(starkKeys, response.payloadHash!!)
        }
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
    fees.forEach { fee ->
        val percentage = BigDecimal(fee.feePercentage?.div(100) ?: 0.0)
        totalFee += amount.multiply(percentage)
    }
    return totalFee
}

private fun getFees(
    asset: Erc721Asset,
    sellToken: AssetModel,
    fees: List<FeeEntry>,
    api: AssetsApi
): CompletableFuture<BigDecimal> = call(GET_ASSETS) {
    val feesList = arrayListOf<FeeEntry>()
    feesList += fees

    feesList += api.getAsset(
        tokenAddress = asset.tokenAddress,
        tokenId = asset.tokenId,
        includeFees = true // to return fees related to the asset e.g. royalty
    ).fees
        // Look for royalty fees only
        ?.filter { it.type == FEE_TYPE_ROYALTY }
        ?.map { fee -> FeeEntry(address = fee.address, feePercentage = fee.percentage) }
        ?: emptyList()

    calculateMakerOrderFee(BigDecimal(sellToken.quantity), feesList)
}

@Suppress("LongParameterList")
private fun getSignableOrder(
    asset: Erc721Asset,
    sellToken: AssetModel,
    address: String,
    fees: List<FeeEntry>,
    totalFees: BigDecimal,
    api: OrdersApi
): CompletableFuture<GetSignableOrderResponse> = call(SIGNABLE_ORDER) {
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
        is Erc721Asset -> return sellToken.quantity
        else -> Constants.ETH_DECIMALS
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
            fees = fees,
            // Always include fees in order, the 'fees' field will either have fees details or nothing at all
            includeFees = true
        ),
        xImxEthAddress = null,
        xImxEthSignature = null
    ).orderId!!
}
