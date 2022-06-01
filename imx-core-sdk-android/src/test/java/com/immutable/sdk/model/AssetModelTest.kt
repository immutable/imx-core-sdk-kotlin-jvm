package com.immutable.sdk.model

import org.junit.Assert.assertEquals
import org.junit.Test

private const val SELL_TOKEN_ADDRESS = "0x07865c6e87b9f70255377e024ace6630c1eaa37f"
private const val SELL_TOKEN_DECIMALS = 6
private const val TOKEN_ADDRESS = "0x0ee4c0826ba5523c9f0eee40da69befa30b3d9dd"
private const val TOKEN_ID = "5"

class AssetModelTest {
    @Test
    fun testFormatQuantity() {
        assertEquals("81000000000000000", EthAsset("0.081").formatQuantity())
        assertEquals(
            "55000000",
            Erc20Asset(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS, "55").formatQuantity()
        )
        assertEquals(
            "1000000",
            Erc20Asset(SELL_TOKEN_ADDRESS, SELL_TOKEN_DECIMALS, "1").formatQuantity()
        )
        assertEquals("1", Erc721Asset(TOKEN_ADDRESS, TOKEN_ID).formatQuantity())
    }
}
