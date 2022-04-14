package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.TransfersApi
import com.immutable.sdk.api.model.CreateTransferRequest
import com.immutable.sdk.api.model.CreateTransferResponse
import com.immutable.sdk.api.model.GetSignableTransferRequest
import com.immutable.sdk.api.model.GetSignableTransferResponse
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.stark.StarkCurve
import java.util.concurrent.CompletableFuture

internal fun transfer(
    token: AssetModel,
    recipientAddress: String,
    signer: Signer,
    starkSigner: StarkSigner,
    api: TransfersApi = TransfersApi()
): CompletableFuture<Int> {
    val future = CompletableFuture<Int>()

    signer.getAddress()
        .thenCompose { address -> getSignableTransfer(address, token, recipientAddress, api) }
        .thenCompose { signableResponse -> getTransferRequest(signableResponse, starkSigner) }
        .thenCompose { request -> createTransfer(request, api) }
        .whenComplete { response, error ->
            // Forward any exceptions from the compose chain
            if (error != null)
                future.completeExceptionally(error)
            else
                future.complete(response.transferId)
        }

    return future
}

@Suppress("TooGenericExceptionCaught")
private fun getSignableTransfer(
    address: String,
    token: AssetModel,
    recipientAddress: String,
    api: TransfersApi
): CompletableFuture<GetSignableTransferResponse> {
    val future = CompletableFuture<GetSignableTransferResponse>()

    CompletableFuture.runAsync {
        try {
            future.complete(
                api.getSignableTransfer(
                    GetSignableTransferRequest(
                        amount = token.quantity,
                        receiver = recipientAddress,
                        sender = address,
                        token = token.toToken()
                    )
                )
            )
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }
    return future
}

@Suppress("TooGenericExceptionCaught")
private fun getTransferRequest(
    response: GetSignableTransferResponse,
    signer: StarkSigner
): CompletableFuture<CreateTransferRequest> {
    val future = CompletableFuture<CreateTransferRequest>()

    CompletableFuture.runAsync {
        try {
            signer.getStarkKeys()
                .thenCompose { starkKeys ->
                    val signature = StarkCurve.sign(starkKeys, response.payloadHash!!)
                    CompletableFuture.completedFuture(signature)
                }
                .whenComplete { signature, error ->
                    if (error != null)
                        future.completeExceptionally(error)
                    else {
                        future.complete(
                            CreateTransferRequest(
                                amount = response.amount!!,
                                assetId = response.assetId!!,
                                expirationTimestamp = response.expirationTimestamp!!,
                                nonce = response.nonce!!,
                                receiverStarkKey = response.receiverStarkKey!!,
                                receiverVaultId = response.receiverVaultId!!,
                                senderStarkKey = response.senderStarkKey!!,
                                senderVaultId = response.senderVaultId!!,
                                starkSignature = signature!!
                            )
                        )
                    }
                }
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }

    return future
}

@Suppress("TooGenericExceptionCaught")
private fun createTransfer(
    request: CreateTransferRequest,
    api: TransfersApi
): CompletableFuture<CreateTransferResponse> {
    val future = CompletableFuture<CreateTransferResponse>()

    CompletableFuture.runAsync {
        try {
            future.complete(api.createTransfer(request))
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }

    return future
}
