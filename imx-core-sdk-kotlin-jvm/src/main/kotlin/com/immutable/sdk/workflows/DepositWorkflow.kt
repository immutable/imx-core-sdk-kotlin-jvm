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
    asset: AssetModel,
    signer: Signer,
    depositsApi: DepositsApi,
    userApi: UsersApi,
    encodingApi: EncodingApi,
): CompletableFuture<String> {
    return when (asset) {
        is EthAsset -> depositEth(base, nodeUrl, asset, signer, depositsApi, userApi, encodingApi)
        is Erc20Asset -> CompletableFuture.failedFuture(UnsupportedOperationException())
        is Erc721Asset -> CompletableFuture.failedFuture(UnsupportedOperationException())
    }
}
