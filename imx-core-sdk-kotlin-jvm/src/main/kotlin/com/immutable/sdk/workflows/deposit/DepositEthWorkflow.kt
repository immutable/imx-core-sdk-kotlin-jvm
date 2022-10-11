package com.immutable.sdk.workflows.deposit

import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.EncodeAssetRequest
import com.immutable.sdk.api.model.EncodeAssetRequestToken
import com.immutable.sdk.api.model.GetSignableDepositRequest
import com.immutable.sdk.api.model.GetSignableDepositResponse
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.WorkflowSignatures
import com.immutable.sdk.workflows.call
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_DEPOSIT = "Get signable deposit"
private const val ENCODE_ASSET = "Encode asset"
private const val ASSET_TYPE = "asset"

internal fun depositEth(
    token: EthAsset,
    signer: Signer,
    depositsApi: DepositsApi,
    userApi: UsersApi,
    encodingApi: EncodingApi,
) {
    signer.getAddress()
        .thenCompose { ethAddress ->
            getSignableDeposit(
                ethAddress,
                token,
                depositsApi
            )
        }
        .thenCompose { signableDepositResponse ->
            encodeAsset(token, encodingApi)
                .thenApply { response -> signableDepositResponse to response }
        }
}

private fun getSignableDeposit(
    address: String,
    token: EthAsset,
    api: DepositsApi
): CompletableFuture<GetSignableDepositResponse> = call(GET_SIGNABLE_DEPOSIT) {
    val request = GetSignableDepositRequest(
        amount = token.quantity,
        token = token.toSignableToken(),
        user = address
    )
    api.getSignableDeposit(request)
}

private fun encodeAsset(token: EthAsset, api: EncodingApi) = call(ENCODE_ASSET) {
    val request =
        EncodeAssetRequest(token = EncodeAssetRequestToken(type = EncodeAssetRequestToken.Type.eTH))
    api.encodeAsset(ASSET_TYPE, request)
}