package com.immutable.sdk.extensions

import com.immutable.sdk.api.model.SignableToken
import com.immutable.sdk.api.model.Token
import com.immutable.sdk.api.model.TokenData
import com.immutable.sdk.utils.TokenType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

private const val DECIMAL = 18
private const val TOKEN_ID = "1"
private const val TOKEN_ADDRESS = "0x1"
private const val ID = "ID"
private const val QUANTITY = "5"

class TokenExtensionTest {
    private val tokenData = TokenData(
        decimals = DECIMAL,
        tokenId = TOKEN_ID,
        tokenAddress = TOKEN_ADDRESS,
        quantity = QUANTITY,
        id = ID
    )

    @Test
    fun testCleanEth() {
        assertEquals(
            SignableToken(
                data = TokenData(decimals = DECIMAL),
                type = TokenType.ETH.name
            ),
            Token(data = tokenData, type = TokenType.ETH.name).clean()
        )
    }

    @Test
    fun testCleanEth_noDecimals() {
        assertNull(Token(data = TokenData(), type = TokenType.ETH.name).clean())
        assertNull(Token(data = TokenData(id = ID), type = TokenType.ETH.name).clean())
    }

    @Test
    fun testCleanErc20() {
        assertEquals(
            SignableToken(
                data = TokenData(decimals = DECIMAL, tokenAddress = TOKEN_ADDRESS),
                type = TokenType.ERC20.name
            ),
            Token(data = tokenData, type = TokenType.ERC20.name).clean()
        )
    }

    @Test
    fun testCleanErc20_noTokenAddressOrDecimals() {
        assertNull(Token(data = TokenData(), type = TokenType.ERC20.name).clean())
        assertNull(
            Token(
                data = TokenData(tokenAddress = TOKEN_ADDRESS),
                type = TokenType.ERC20.name
            ).clean()
        )
        assertNull(Token(data = TokenData(decimals = DECIMAL), type = TokenType.ERC20.name).clean())
    }

    @Test
    fun testCleanErc721() {
        assertEquals(
            SignableToken(
                data = TokenData(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID),
                type = TokenType.ERC721.name
            ),
            Token(data = tokenData, type = TokenType.ERC721.name).clean()
        )
    }

    @Test
    fun testCleanErc721_noTokenAddressOrTokenId() {
        assertNull(Token(data = TokenData(), type = TokenType.ERC721.name).clean())
        assertNull(
            Token(
                data = TokenData(tokenAddress = TOKEN_ADDRESS),
                type = TokenType.ERC721.name
            ).clean()
        )
        assertNull(
            Token(
                data = TokenData(tokenId = TOKEN_ID),
                type = TokenType.ERC721.name
            ).clean()
        )
    }
}
