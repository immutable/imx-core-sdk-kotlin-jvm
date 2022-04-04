package com.immutable.sdk.extensions

import com.immutable.sdk.model.Token
import com.immutable.sdk.model.TokenData
import com.immutable.sdk.utils.TokenType

/**
 * We need to strip the Token back to the required only fields otherwise the api validation will fail.
 */
internal fun Token.clean(): Token? = data?.let {
    when (type) {
        TokenType.ETH.name -> {
            if (it.decimals != null)
                Token(data = TokenData(decimals = it.decimals), type = TokenType.ETH.name)
            else
                null
        }
        TokenType.ERC20.name -> {
            if (it.tokenAddress != null && it.decimals != null)
                Token(
                    data = TokenData(decimals = it.decimals, tokenAddress = it.tokenAddress),
                    type = TokenType.ERC20.name
                )
            else
                null
        }
        TokenType.ERC721.name -> {
            if (it.tokenAddress != null && it.tokenId != null)
                Token(
                    data = TokenData(tokenId = it.tokenId, tokenAddress = it.tokenAddress),
                    type = TokenType.ERC721.name
                )
            else
                null
        }
        else -> null
    }
}
