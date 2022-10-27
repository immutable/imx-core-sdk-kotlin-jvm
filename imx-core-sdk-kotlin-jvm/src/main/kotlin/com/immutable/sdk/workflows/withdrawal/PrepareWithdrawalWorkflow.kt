package com.immutable.sdk.workflows.withdrawal

import com.immutable.sdk.Signer
import com.immutable.sdk.StarkSigner
import com.immutable.sdk.api.WithdrawalsApi
import com.immutable.sdk.api.model.CreateWithdrawalRequest
import com.immutable.sdk.api.model.CreateWithdrawalResponse
import com.immutable.sdk.api.model.GetSignableWithdrawalRequest
import com.immutable.sdk.api.model.GetSignableWithdrawalResponse
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.formatQuantity
import com.immutable.sdk.workflows.WorkflowSignatures
import com.immutable.sdk.workflows.call
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_WITHDRAWAL = "Get signable withdrawal"
private const val CREATE_WITHDRAWAL = "Create withdrawal"

internal fun prepareWithdrawal(
    token: AssetModel,
    signer: Signer,
    starkSigner: StarkSigner,
    withdrawalsApi: WithdrawalsApi
): CompletableFuture<CreateWithdrawalResponse> {
    val amount = token.formatQuantity()

    return signer.getAddress()
        .thenApply { address -> WorkflowSignatures(address) }
        .thenCompose { signatures ->
            getSignableWithdrawal(signatures.ethAddress, token, amount, withdrawalsApi)
                .thenApply { response -> signatures to response }
        }
        .thenCompose { (signatures, response) ->
            starkSigner.signMessage(response.payloadHash)
                .thenApply { signature -> signatures.apply { starkSignature = signature } to response }
        }
        .thenCompose { (signatures, response) ->
            signer.signMessage(response.signableMessage)
                .thenApply { signature -> signatures.apply { ethSignature = signature } to response }
        }
        .thenCompose { (signatures, response) ->
            createWithdrawal(response, signatures, amount, withdrawalsApi)
        }
}

private fun getSignableWithdrawal(
    address: String,
    token: AssetModel,
    amount: String,
    api: WithdrawalsApi
): CompletableFuture<GetSignableWithdrawalResponse> = call(GET_SIGNABLE_WITHDRAWAL) {
    api.getSignableWithdrawal(
        GetSignableWithdrawalRequest(
            amount = amount,
            token = token.toSignableToken(),
            user = address
        )
    )
}

private fun createWithdrawal(
    response: GetSignableWithdrawalResponse,
    signatures: WorkflowSignatures,
    amount: String,
    api: WithdrawalsApi
): CompletableFuture<CreateWithdrawalResponse> = call(CREATE_WITHDRAWAL) {
    api.createWithdrawal(
        CreateWithdrawalRequest(
            amount = amount,
            assetId = response.assetId,
            nonce = response.nonce,
            starkKey = response.starkKey,
            starkSignature = signatures.starkSignature,
            vaultId = response.vaultId
        ),
        xImxEthAddress = signatures.ethAddress,
        xImxEthSignature = signatures.serialisedEthSignature
    )
}
