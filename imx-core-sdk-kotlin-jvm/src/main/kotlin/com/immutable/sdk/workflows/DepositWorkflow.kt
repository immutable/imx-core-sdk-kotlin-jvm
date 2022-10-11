package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc20Asset
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.deposit.depositEth

internal fun deposit(
    token: AssetModel,
    signer: Signer,
    depositsApi: DepositsApi,
    userApi: UsersApi,
    encodingApi: EncodingApi,
) {
    when (token) {
        is EthAsset -> depositEth(token, signer, depositsApi, userApi, encodingApi)
        is Erc20Asset -> {
        }
        is Erc721Asset -> {
        }
        else -> {}
    }
}