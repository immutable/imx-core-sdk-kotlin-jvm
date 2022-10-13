package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.deposit.depositEth
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList")
internal fun deposit(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    depositsApi: DepositsApi,
    userApi: UsersApi,
    encodingApi: EncodingApi,
): CompletableFuture<Unit> {
    println("deposit workflow $token")
    when (token) {
        is EthAsset -> return depositEth(base, nodeUrl, token, signer, depositsApi, userApi, encodingApi)
        is Erc20Asset -> {
        }
        is Erc721Asset -> {
        }
        else -> {}
    }
    return CompletableFuture.completedFuture(Unit)
}
