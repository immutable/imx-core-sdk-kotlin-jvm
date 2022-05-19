package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.TransfersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.crypto.StarkKey
import com.immutable.sdk.model.AssetModel
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
                future.complete(response.transferIds?.first())
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
            val transferDetails = arrayListOf(
                SignableTransferDetails(
                    amount = token.quantity,
                    receiver = recipientAddress,
                    token = token.toSignableToken()
                )
            )
            val request = GetSignableTransferRequest(
                senderEtherKey = address,
                signableRequests = transferDetails
            )
            future.complete(api.getSignableTransfer(request))
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
                    val signature = StarkKey.sign(
                        keyPair = starkKeys,
                        msg = response.signableResponses?.first()?.payloadHash!!
                    )
                    CompletableFuture.completedFuture(signature)
                }
                .whenComplete { signature, error ->
                    if (error != null) future.completeExceptionally(error)
                    else future.complete(getCreateTransferRequest(response, signature))
                }
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }

    return future
}

private fun getCreateTransferRequest(
    response: GetSignableTransferResponse,
    signature: String?
): CreateTransferRequest {
    val signableResponse = response.signableResponses?.first()!!
    val transferRequest = TransferRequest(
        amount = signableResponse.amount!!,
        assetId = signableResponse.assetId!!,
        expirationTimestamp = signableResponse.expirationTimestamp!!,
        nonce = signableResponse.nonce!!,
        receiverStarkKey = signableResponse.receiverStarkKey!!,
        receiverVaultId = signableResponse.receiverVaultId!!,
        senderVaultId = signableResponse.senderVaultId!!,
        starkSignature = signature!!,
    )
    return CreateTransferRequest(
        senderStarkKey = response.senderStarkKey,
        requests = arrayListOf(transferRequest)
    )
}

@Suppress("TooGenericExceptionCaught")
private fun createTransfer(
    request: CreateTransferRequest,
    api: TransfersApi
): CompletableFuture<CreateTransferResponse> {
    val future = CompletableFuture<CreateTransferResponse>()

    CompletableFuture.runAsync {
        try {
            val response = api.createTransfer(
                request,
                xImxEthAddress = null,
                xImxEthSignature = null
            )
            future.complete(response)
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }

    return future
}
