package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.model.GetSignableOrderResponse
import com.immutable.sdk.crypto.CryptoUtil
import com.immutable.sdk.stark.StarkCurve
import java.util.concurrent.CompletableFuture

private const val SIGNABLE_ORDER = "Signable order"

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
internal fun getOrderStarkSignature(
    response: GetSignableOrderResponse,
    signer: StarkSigner
): CompletableFuture<Pair<GetSignableOrderResponse, String>> {
    val future = CompletableFuture<Pair<GetSignableOrderResponse, String>>()

    // Temporary until API returns message to sign
    CompletableFuture.runAsync {
        try {
            val message = response.feeInfo?.let { feeInfo ->
                CryptoUtil.getLimitOrderMsgWithFee(
                    tokenSell = response.assetIdSell!!,
                    tokenBuy = response.assetIdBuy!!,
                    vaultSell = response.vaultIdSell!!.toString(),
                    vaultBuy = response.vaultIdBuy!!.toString(),
                    amountSell = response.amountSell!!,
                    amountBuy = response.amountBuy!!,
                    nonce = response.nonce!!.toString(),
                    expirationTimestamp = response.expirationTimestamp!!.toString(),
                    feeToken = feeInfo.assetId,
                    feeLimit = feeInfo.feeLimit,
                    feeVault = feeInfo.sourceVaultId.toString()
                )
            } ?: CryptoUtil.getLimitOrderMsg(
                tokenSell = response.assetIdSell!!,
                tokenBuy = response.assetIdBuy!!,
                vaultSell = response.vaultIdSell!!.toString(),
                vaultBuy = response.vaultIdBuy!!.toString(),
                amountSell = response.amountSell!!,
                amountBuy = response.amountBuy!!,
                nonce = response.nonce!!.toString(),
                expirationTimestamp = response.expirationTimestamp!!.toString()
            )

            signer.getStarkKeys()
                .thenCompose { starkKeys ->
                    val signature = StarkCurve.sign(starkKeys, message)
                    CompletableFuture.completedFuture(signature)
                }
                .whenComplete { signature, error ->
                    if (error != null)
                        future.completeExceptionally(error)
                    else
                        future.complete(response to signature)
                }
        } catch (e: Exception) {
            if (e is NullPointerException)
                future.completeExceptionally(ImmutableException.invalidResponse(SIGNABLE_ORDER, e))
            else
                future.completeExceptionally(e)
        }
    }

    return future
}
