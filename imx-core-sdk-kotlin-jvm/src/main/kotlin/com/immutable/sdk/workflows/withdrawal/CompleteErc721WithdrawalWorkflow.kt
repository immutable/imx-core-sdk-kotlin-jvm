package com.immutable.sdk.workflows.withdrawal

import com.immutable.sdk.Constants
import com.immutable.sdk.ImmutableException
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.MintsApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetSignableRegistrationResponse
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.extensions.hexRemovePrefix
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.Erc721Asset
import com.immutable.sdk.workflows.executeCompleteWithdrawal
import com.immutable.sdk.workflows.prepareCompleteWithdrawal
import org.openapitools.client.infrastructure.ClientException
import org.web3j.tx.gas.StaticGasProvider
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

private const val GET_MINTABLE_TOKEN_DETAILS = "Get mintable token details"

@Suppress("LongParameterList", "LongMethod")
internal fun completeErc721Withdrawal(
    base: ImmutableXBase,
    nodeUrl: String,
    token: Erc721Asset,
    signer: Signer,
    starkPublicKey: String,
    usersApi: UsersApi,
    encodingApi: EncodingApi,
    mintsApi: MintsApi,
    gasProvider: StaticGasProvider
): CompletableFuture<String> = getMintableTokenDetails(token, mintsApi)
    .thenCompose { assetToken ->
        prepareCompleteWithdrawal(
            base,
            nodeUrl,
            assetToken,
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
            registerAndWithdrawFunction = if (params.token is MintableErc721Asset)
                Registration_sol_Registration.FUNC_REGSITERANDWITHDRAWANDMINT
            else Registration_sol_Registration.FUNC_REGISTERANDWITHDRAWNFT,
            registerAndWithdrawData = { contract, response: GetSignableRegistrationResponse ->
                if (params.token is MintableErc721Asset) contract.regsiterAndWithdrawAndMint(
                    params.address,
                    params.starkKey.hexRemovePrefix().toBigInteger(Constants.HEX_RADIX),
                    response.operatorSignature.hexToByteArray(),
                    params.assetType,
                    getMintingBlob(params.token)
                ).encodeFunctionCall()
                else contract.registerAndWithdrawNft(
                    params.address,
                    params.starkKey.hexRemovePrefix().toBigInteger(Constants.HEX_RADIX),
                    response.operatorSignature.hexToByteArray(),
                    params.assetType,
                    token.tokenId.toBigInteger()
                ).encodeFunctionCall()
            },
            withdrawFunction = if (params.token is MintableErc721Asset) Core_sol_Core.FUNC_WITHDRAWANDMINT
            else Core_sol_Core.FUNC_WITHDRAW,
            withdrawData = { contract ->
                if (params.token is MintableErc721Asset) contract.withdrawAndMint(
                    params.starkKey.hexRemovePrefix().toBigInteger(Constants.HEX_RADIX),
                    params.assetType,
                    getMintingBlob(params.token)
                ).encodeFunctionCall()
                else contract.withdrawNft(
                    params.starkKey.hexRemovePrefix().toBigInteger(Constants.HEX_RADIX),
                    params.assetType,
                    token.tokenId.toBigInteger()
                ).encodeFunctionCall()
            },
            usersApi,
            gasProvider
        )
    }
    .thenApply { it.transactionHash }

private fun getMintableTokenDetails(
    token: Erc721Asset,
    api: MintsApi
): CompletableFuture<AssetModel> {
    val future = CompletableFuture<AssetModel>()
    CompletableFuture.runAsync {
        try {
            val details = api.getMintableTokenDetailsByClientTokenId(
                tokenAddress = token.tokenAddress,
                tokenId = token.tokenId
            )
            val mintableErc721Asset = MintableErc721Asset(
                blueprint = details.blueprint,
                tokenAddress = token.tokenAddress,
                id = token.tokenId
            )
            future.complete(mintableErc721Asset)
        } catch (e: ClientException) {
            // Endpoint returns 404 if token is already minted on L1
            if (e.statusCode == HttpURLConnection.HTTP_NOT_FOUND) future.complete(token)
            else future.completeExceptionally(ImmutableException.apiError(GET_MINTABLE_TOKEN_DETAILS, e))
        }
    }
    return future
}

private fun getMintingBlob(token: MintableErc721Asset): ByteArray {
    val id = token.id
    val blueprint = token.blueprint
    val blob = "{$id}:{$blueprint}"
    return blob.toByteArray()
}

internal data class MintableErc721Asset(
    val blueprint: String,
    val id: String,
    override val tokenAddress: String,
) : Erc721Asset(tokenAddress, id)
