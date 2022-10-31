package com.immutable.sdk.workflows

import com.immutable.sdk.Signer
import com.immutable.sdk.api.MintsApi
import com.immutable.sdk.api.model.MintFee
import com.immutable.sdk.api.model.MintRequest
import com.immutable.sdk.api.model.MintResultDetails
import com.immutable.sdk.api.model.UnsignedMintRequest
import com.immutable.sdk.crypto.Crypto
import com.immutable.sdk.extensions.toJsonString
import org.web3j.crypto.Hash
import java.util.concurrent.CompletableFuture

private const val KEY_ETHER_KEY = "ether_key"
private const val KEY_ID = "id"
private const val KEY_BLUEPRINT = "blueprint"
private const val KEY_ROYALTIES = "royalties"
private const val KEY_RECIPIENT = "recipient"
private const val KEY_PERCENTAGE = "percentage"
private const val KEY_TOKENS = "tokens"
private const val KEY_CONTRACT_ADDRESS = "contract_address"
private const val KEY_USERS = "users"
private const val KEY_AUTH_SIGNATURE = "auth_signature"
private const val MINT_TOKEN = "Mint token"

internal fun mint(
    request: UnsignedMintRequest,
    signer: Signer,
    mintsApi: MintsApi = MintsApi()
): CompletableFuture<List<MintResultDetails>> {
    // Manually creating the JSON object using LinkedHashMap as the order of the keys is important
    val users = request.users.map { user ->
        val json = LinkedHashMap<String, Any>()
        json[KEY_ETHER_KEY] = user.user

        val tokens = user.tokens.map { token ->
            LinkedHashMap<String, Any>().apply {
                put(KEY_ID, token.id)
                token.blueprint?.let { put(KEY_BLUEPRINT, it) }
                token.royalties?.toLinkedHashMap()?.let { put(KEY_ROYALTIES, it) }
            }
        }
        json[KEY_TOKENS] = tokens

        json
    }

    val signablePayload = LinkedHashMap<String, Any>().apply {
        put(KEY_CONTRACT_ADDRESS, request.contractAddress)
        request.royalties?.toLinkedHashMap()?.let { put(KEY_ROYALTIES, it) }
        put(KEY_USERS, users)
        put(KEY_AUTH_SIGNATURE, "")
    }.toJsonString()

    val hash = Hash.sha3String(signablePayload)

    return signer.signMessage(hash)
        .thenApply { authSignature ->
            MintRequest(
                authSignature = Crypto.serialiseEthSignature(authSignature),
                contractAddress = request.contractAddress,
                users = request.users,
                royalties = request.royalties
            )
        }
        .thenCompose { apiPayload -> call(MINT_TOKEN) { mintsApi.mintTokens(arrayListOf(apiPayload)) } }
        .thenApply { it.results }
}

private fun List<MintFee>?.toLinkedHashMap() = this?.map { fee ->
    LinkedHashMap<String, Any>().apply {
        put(KEY_RECIPIENT, fee.recipient)
        put(KEY_PERCENTAGE, fee.percentage)
    }
}
