package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.TransfersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.model.AssetModel
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_TRANSFER = "Get signable transfer"
private const val GET_TRANSFER_REQUEST = "Get transfer request"

internal fun transfer(
    token: AssetModel,
    recipientAddress: String,
    signer: Signer,
    starkSigner: StarkSigner,
    api: TransfersApi = TransfersApi()
): CompletableFuture<CreateTransferResponse> {
    val future = CompletableFuture<CreateTransferResponse>()

    signer.getAddress().thenApply { address -> WorkflowSignatures(address) }
        .thenCompose { signatures ->
            getSignableTransfer(
                signatures.ethAddress,
                token,
                recipientAddress,
                api
            ).thenApply { response -> signatures to response }
        }
        .thenCompose { (signatures, response) ->
            getStarkSignature(response, starkSigner)
                .thenApply { signature -> signatures.apply { starkSignature = signature } to response }
        }
        .thenCompose { (signatures, response) ->
            signer.signPrefixedMessage(response.signableMessage)
                .thenApply { signature -> signatures.apply { ethSignature = signature } to response }
        }
        .thenCompose { (signatures, response) -> createTransfer(response, signatures, api) }
        .whenComplete { response, error ->
            // Forward any exceptions from the compose chain
            if (error != null)
                future.completeExceptionally(error)
            else
                future.complete(response)
        }

    return future
}

@Suppress("TooGenericExceptionCaught")
private fun getSignableTransfer(
    address: String,
    token: AssetModel,
    recipientAddress: String,
    api: TransfersApi
): CompletableFuture<GetSignableTransferResponse> = call(GET_SIGNABLE_TRANSFER) {
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
    api.getSignableTransfer(request)
}

@Suppress("TooGenericExceptionCaught")
private fun getStarkSignature(
    response: GetSignableTransferResponse,
    signer: StarkSigner
): CompletableFuture<String> = call(GET_TRANSFER_REQUEST) {
    // Force unwrapping that the NPE gets handled by `call`
    response.signableResponses.firstOrNull()?.payloadHash!!
}
    .thenCompose { payload -> signer.signMessage(payload) }

@Suppress("TooGenericExceptionCaught")
private fun createTransfer(
    response: GetSignableTransferResponse,
    signatures: WorkflowSignatures,
    api: TransfersApi
): CompletableFuture<CreateTransferResponse> {
    val future = CompletableFuture<CreateTransferResponse>()

    CompletableFuture.runAsync {
        try {
            val signableResponse = response.signableResponses.first()
            future.complete(
                api.createTransfer(
                    CreateTransferRequest(
                        senderStarkKey = response.senderStarkKey,
                        requests = arrayListOf(
                            TransferRequest(
                                amount = signableResponse.amount,
                                assetId = signableResponse.assetId,
                                expirationTimestamp = signableResponse.expirationTimestamp,
                                nonce = signableResponse.nonce,
                                receiverStarkKey = signableResponse.receiverStarkKey,
                                receiverVaultId = signableResponse.receiverVaultId,
                                senderVaultId = signableResponse.senderVaultId,
                                starkSignature = signatures.starkSignature,
                            )
                        )
                    ),
                    xImxEthAddress = signatures.ethAddress,
                    xImxEthSignature = signatures.serialisedEthSignature
                )
            )
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }

    return future
}
