package com.immutable.sdk.extensions

import com.immutable.sdk.api.model.Token
import com.immutable.sdk.api.model.TokenData
import com.immutable.sdk.utils.TokenType

/**
 * We need to strip the Token back to the required only fields otherwise the api validation will fail.
 */
internal fun Token.clean(): Token? = data?.let {
    when (type) {
        TokenType.ETH.name -> it.decimals?.let { decimals ->
            Token(data = TokenData(decimals = decimals), type = TokenType.ETH.name)
        }
        TokenType.ERC20.name -> ifNotNull(it.tokenAddress, it.decimals) { tokenAddress, decimals ->
            Token(
                data = TokenData(decimals = decimals, tokenAddress = tokenAddress),
                type = TokenType.ERC20.name
            )
        }
        TokenType.ERC721.name -> ifNotNull(it.tokenAddress, it.tokenId) { tokenAddress, tokenId ->
            Token(
                data = TokenData(tokenId = tokenId, tokenAddress = tokenAddress),
                type = TokenType.ERC721.name
            )
        }
        else -> null
    }
}
