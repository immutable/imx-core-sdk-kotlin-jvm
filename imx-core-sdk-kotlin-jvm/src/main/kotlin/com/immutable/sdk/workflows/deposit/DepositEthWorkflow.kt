package com.immutable.sdk.workflows.deposit

import com.immutable.sdk.Constants.HEX_RADIX
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationResponse
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.EthAsset
import com.immutable.sdk.workflows.executeDeposit
import com.immutable.sdk.workflows.prepareDeposit
import org.web3j.tx.gas.StaticGasProvider
import java.util.concurrent.CompletableFuture

@Suppress("LongParameterList", "LongMethod")
internal fun depositEth(
    base: ImmutableXBase,
    nodeUrl: String,
    token: EthAsset,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    gasProvider: StaticGasProvider
): CompletableFuture<String> {
    val future = CompletableFuture<String>()

    prepareDeposit(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi, gasProvider)
        .thenCompose { params ->
            executeDeposit(
                base,
                nodeUrl,
                signer,
                params,
                registerAndDepositFunction = Core_sol_Core.FUNC_REGISTERANDDEPOSITETH,
                registerAndDepositData = { contract: Core_sol_Core, response: GetSignableRegistrationResponse ->
                    contract.registerAndDepositEth(
                        params.address,
                        params.starkKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                        response.operatorSignature.hexToByteArray(),
                        params.assetType,
                        params.vaultId
                    ).encodeFunctionCall()
                },
                depositFunction = Core_sol_Core.FUNC_DEPOSITETH,
                depositData = { contract: Core_sol_Core ->
                    contract.depositEth(
                        params.starkKey.hexRemovePrefix().toBigInteger(HEX_RADIX),
                        params.assetType,
                        params.vaultId
                    ).encodeFunctionCall()
                },
                usersApi,
                gasProvider
            )
        }
        .whenComplete { response, throwable ->
            if (throwable != null) future.completeExceptionally(throwable)
            else future.complete(response.transactionHash)
        }

    return future
}
