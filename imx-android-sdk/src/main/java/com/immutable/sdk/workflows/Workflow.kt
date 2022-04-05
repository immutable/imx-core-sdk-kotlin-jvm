package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableException
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.crypto.CryptoUtil
import com.immutable.sdk.model.GetSignableOrderResponse
import java.util.concurrent.CompletableFuture

@Suppress("TooGenericExceptionCaught", "SwallowedException", "InstanceOfCheckForException")
internal fun getStarkSignature(
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
                    vaultSell = response.vaultIdSell!!.toString(),
                    vaultBuy = response.vaultIdBuy!!.toString(),
                    amountSell = response.amountSell!!,
                    amountBuy = response.amountBuy!!,
                    nonce = response.nonce!!.toString(),
                    expirationTimestamp = response.expirationTimestamp!!.toString(),
                    feeToken = feeInfo.assetId,
                    feeLimit = feeInfo.feeLimit,
                    feeVault = feeInfo.sourceVaultId!!.toString()
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

            signer.starkSign(msg).whenComplete { signature, error ->
                if (error != null)
                    future.completeExceptionally(
                        ImmutableException("Unable to generate stark signature")
                    )
                else
                    future.complete(response to signature)
            }
        } catch (e: Exception) {
            if (e is NullPointerException)
                future.completeExceptionally(
                    ImmutableException(
                        "Unable to generate stark signature: Signable order response contains unexpected null values"
                    )
                )
            else
                future.completeExceptionally(e)
        }
    }

    return future
}
