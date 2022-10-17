package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableConfig.getCoreContractAddress
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.model.*
import com.immutable.sdk.workflows.deposit.depositErc20
import com.immutable.sdk.workflows.deposit.depositEth
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

private const val GET_SIGNABLE_DEPOSIT = "Get signable deposit"
private const val ENCODE_ASSET = "Encode asset"
private const val ASSET_TYPE = "asset"

@Suppress("LongParameterList")
internal fun deposit(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
): CompletableFuture<String> {
    return when (token) {
        is EthAsset -> depositEth(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi)
        is Erc20Asset -> depositErc20(base, nodeUrl, token, signer, depositsApi, usersApi, encodingApi)
        is Erc721Asset -> CompletableFuture.failedFuture(UnsupportedOperationException())
    }
}

/**
 * 1. Gets signable deposit
 * 2. Encodes the [token]
 * 3. Checks if user is registered on chain
 *
 * @return [DepositWorkflowParams]
 */
@Suppress("LongParameterList")
internal fun prepareDeposit(
    base: ImmutableXBase,
    nodeUrl: String,
    token: AssetModel,
    signer: Signer,
    depositsApi: DepositsApi,
    usersApi: UsersApi,
    encodingApi: EncodingApi
): CompletableFuture<DepositWorkflowParams> {
    val amount = when (token) {
        is EthAsset -> Convert.toWei(token.quantity, Convert.Unit.ETHER).toBigInteger()
        is Erc20Asset -> token.formatQuantity().toBigInteger()
        is Erc721Asset -> BigInteger.ONE
    }

    return signer.getAddress()
        .thenCompose { address ->
            getSignableDeposit(address, token, amount, depositsApi)
                .thenApply { response -> address to response }
        }
        .thenCompose { (address, signableDepositResponse) ->
            encodeAsset(token, encodingApi).thenApply { encodeAssetResponse ->
                DepositWorkflowParams(
                    tokenType = token.toSignableToken().type,
                    address = address,
                    starkKey = signableDepositResponse.starkKey,
                    assetType = encodeAssetResponse.assetType.toBigInteger(),
                    vaultId = signableDepositResponse.vaultId.toBigInteger(),
                    amount = amount
                )
            }
        }
        .thenCompose { params ->
            isRegisteredOnChain(base, nodeUrl, signer, usersApi)
                .thenApply { isRegistered -> params.copy(isRegistered = isRegistered) }
        }
}

private fun getSignableDeposit(
    address: String,
    token: AssetModel,
    amount: BigInteger,
    api: DepositsApi
): CompletableFuture<GetSignableDepositResponse> = call(GET_SIGNABLE_DEPOSIT) {
    api.getSignableDeposit(
        GetSignableDepositRequest(
            amount = amount.toString(),
            token = token.toSignableToken(),
            user = address
        )
    )
}

private fun encodeAsset(
    asset: AssetModel,
    api: EncodingApi,
) = call(ENCODE_ASSET) {
    val tokenType = when (asset) {
        is EthAsset -> EncodeAssetRequestToken.Type.eTH
        is Erc20Asset -> EncodeAssetRequestToken.Type.eRC20
        is Erc721Asset -> EncodeAssetRequestToken.Type.eRC721
    }
    val encodeAssetTokenData = when (asset) {
        is EthAsset -> null
        is Erc20Asset -> EncodeAssetTokenData(tokenAddress = asset.tokenAddress)
        is Erc721Asset -> EncodeAssetTokenData(
            tokenAddress = asset.tokenAddress,
            tokenId = asset.tokenId
        )
    }
    val request = EncodeAssetRequest(
        token = EncodeAssetRequestToken(
            type = tokenType,
            data = encodeAssetTokenData
        )
    )
    api.encodeAsset(ASSET_TYPE, request)
}

@Suppress("LongParameterList")
internal fun executeDeposit(
    base: ImmutableXBase,
    nodeUrl: String,
    signer: Signer,
    params: DepositWorkflowParams,
    registerAndDepositFunction: String,
    registerAndDepositData: (Core_sol_Core, GetSignableRegistrationResponse) -> String,
    depositFunction: String,
    depositData: (Core_sol_Core) -> String,
    usersApi: UsersApi
): CompletableFuture<EthSendTransaction> {
    val web3j = Web3j.build(HttpService(nodeUrl))
    val contract = Core_sol_Core.load(
        getCoreContractAddress(base),
        web3j,
        ClientTransactionManager(web3j, params.address),
        DefaultGasProvider()
    )
    // This is amount is only used for Eth asset
    val transactionAmount =
        if (params.tokenType == TokenType.ETH.name) params.amount else BigInteger.ZERO

    return if (!params.isRegistered) { // User is not registered, do both registration and deposit
        signer.getAddress()
            .thenCompose { address ->
                getSignableRegistrationOnChain(
                    address,
                    params.starkKey,
                    usersApi
                )
            }
            .thenCompose { response ->
                sendTransaction(
                    contract = contract,
                    contractFunction = registerAndDepositFunction,
                    amount = transactionAmount,
                    data = registerAndDepositData(contract, response),
                    signer = signer,
                    web3j = web3j
                )
            }
    } else { // User is already registered, continue to deposit
        sendTransaction(
            contract = contract,
            contractFunction = depositFunction,
            amount = transactionAmount,
            data = depositData(contract),
            signer = signer,
            web3j = web3j
        )
    }
}

internal data class DepositWorkflowParams(
    val tokenType: String?,
    val address: String,
    val starkKey: String,
    val assetType: BigInteger,
    val vaultId: BigInteger,
    val amount: BigInteger,
    val isRegistered: Boolean = false,
)
