package com.immutable.sdk.extensions

import com.immutable.sdk.api.model.SignableToken
import com.immutable.sdk.api.model.Token
import com.immutable.sdk.api.model.TokenData
import com.immutable.sdk.model.TokenType

/**
 * We need to strip the Token back to the required only fields otherwise the api validation will fail.
 */
internal fun Token.clean(): SignableToken? = data?.let {
    when (type) {
        TokenType.ETH.name -> it.decimals?.let { decimals ->
            SignableToken(data = TokenData(decimals = decimals), type = TokenType.ETH.name)
        }
        TokenType.ERC20.name -> ifNotNull(it.tokenAddress, it.decimals) { tokenAddress, decimals ->
            SignableToken(
                data = TokenData(decimals = decimals, tokenAddress = tokenAddress),
                type = TokenType.ERC20.name
            )
        }
        TokenType.ERC721.name -> ifNotNull(it.tokenAddress, it.tokenId) { tokenAddress, tokenId ->
            SignableToken(
                data = TokenData(tokenId = tokenId, tokenAddress = tokenAddress),
                type = TokenType.ERC721.name
            )
        }
        else -> null
    }
}
