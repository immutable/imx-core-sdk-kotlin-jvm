package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.TransfersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.model.AssetModel
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_TRANSFER = "Get signable transfer"

data class TransferData(
    val token: AssetModel,
    val recipientAddress: String
)

internal class TransferWorkflow(val ethAddress: String) {
    lateinit var ethSignature: String
    lateinit var starkSignatures: List<String>
}

internal fun transfer(
    transfers: List<TransferData>,
    signer: Signer,
    starkSigner: StarkSigner,
    api: TransfersApi = TransfersApi()
): CompletableFuture<CreateTransferResponse> {
    val future = CompletableFuture<CreateTransferResponse>()

    signer.getAddress().thenApply { address -> TransferWorkflow(address) }
        .thenCompose { signatures ->
            getSignableTransfer(
                signatures.ethAddress,
                transfers,
                api
            ).thenApply { response -> signatures to response }
        }
        .thenCompose { (signatures, response) ->
            getStarkSignatures(response, starkSigner)
                .thenApply { result -> signatures.apply { starkSignatures = result } to response }
        }
        .thenCompose { (signatures, response) ->
            signer.signMessage(response.signableMessage)
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
    transfers: List<TransferData>,
    api: TransfersApi
): CompletableFuture<GetSignableTransferResponse> = call(GET_SIGNABLE_TRANSFER) {
    val request = GetSignableTransferRequest(
        senderEtherKey = address,
        signableRequests = transfers.map { data ->
            SignableTransferDetails(
                amount = data.token.quantity,
                receiver = data.recipientAddress,
                token = data.token.toSignableToken()
            )
        }
    )
    val result = api.getSignableTransfer(request)
    if (result.signableResponses.isEmpty())
        throw NullPointerException("signableResponses is empty.")
    result
}

@Suppress("TooGenericExceptionCaught")
private fun getStarkSignatures(
    response: GetSignableTransferResponse,
    signer: StarkSigner
): CompletableFuture<List<String>> {
    val future = CompletableFuture<List<String>>()
    val signatures = mutableListOf<String>()

    CompletableFuture.runAsync {
        response.signableResponses.forEach {
            try {
                signatures.add(signer.signMessage(it.payloadHash).get())
            } catch (e: Exception) {
                future.completeExceptionally(e.cause)
            }
        }

        future.complete(signatures)
    }

    return future
}

@Suppress("TooGenericExceptionCaught")
private fun createTransfer(
    response: GetSignableTransferResponse,
    signatures: TransferWorkflow,
    api: TransfersApi
): CompletableFuture<CreateTransferResponse> {
    val future = CompletableFuture<CreateTransferResponse>()

    CompletableFuture.runAsync {
        try {
            val transfers = response.signableResponses.mapIndexed { index, signable ->
                TransferRequest(
                    amount = signable.amount,
                    assetId = signable.assetId,
                    expirationTimestamp = signable.expirationTimestamp,
                    nonce = signable.nonce,
                    receiverStarkKey = signable.receiverStarkKey,
                    receiverVaultId = signable.receiverVaultId,
                    senderVaultId = signable.senderVaultId,
                    starkSignature = signatures.starkSignatures[index],
                )
            }

            future.complete(
                api.createTransfer(
                    CreateTransferRequest(
                        senderStarkKey = response.senderStarkKey,
                        requests = transfers
                    ),
                    xImxEthAddress = signatures.ethAddress,
                    xImxEthSignature = Crypto.serialiseEthSignature(signatures.ethSignature)
                )
            )
        } catch (e: Exception) {
            future.completeExceptionally(e)
        }
    }

    return future
}
