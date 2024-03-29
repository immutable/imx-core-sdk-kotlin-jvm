package com.immutable.sdk.workflows.withdrawal

import com.immutable.sdk.Constants
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationResponse
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.workflows.executeCompleteWithdrawal
import com.immutable.sdk.workflows.prepareCompleteWithdrawal
import org.web3j.tx.gas.StaticGasProvider
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList", "LongMethod")
internal fun completeFungibleTokenWithdrawal(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    starkPublicKey: String,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    gasProvider: StaticGasProvider,
): CompletableFuture<String> = signer.getAddress()
    .thenCompose {
        prepareCompleteWithdrawal(
            base,
            nodeUrl,
            token,
            signer,
            starkPublicKey,
            usersApi,
            encodingApi,
            gasProvider
        )
    }
    .thenCompose { params ->
        executeCompleteWithdrawal(
            base,
            nodeUrl,
            signer,
            params,
            registerAndWithdrawFunction = Registration_sol_Registration.FUNC_REGISTERANDWITHDRAW,
            registerAndWithdrawData = { contract, response: GetSignableRegistrationResponse ->
                contract.registerAndWithdraw(
                    params.address,
                    params.starkKey.hexRemovePrefix().toBigInteger(Constants.HEX_RADIX),
                    response.operatorSignature.hexToByteArray(),
                    params.assetType,
                ).encodeFunctionCall()
            },
            withdrawFunction = Core_sol_Core.FUNC_WITHDRAW,
            withdrawData = { contract ->
                contract.withdraw(
                    params.starkKey.hexRemovePrefix().toBigInteger(Constants.HEX_RADIX),
                    params.assetType,
                ).encodeFunctionCall()
            },
            usersApi,
            gasProvider
        )
    }
    .thenApply { it.transactionHash }
