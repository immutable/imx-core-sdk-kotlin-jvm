package com.immutable.sdk.extensions

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonObjectExtensionsTest {
    @Test
    fun testToURLEncodedString() {
        val json = JSONObject()
        json.put("id", 1)
        json.put("name", "ImmutableX")
        json.put("tokens", arrayListOf("imx", "eth"))

        assertEquals(
            "%7B%22name%22%3A%22ImmutableX%22%2C%22tokens%22%3A%5B%22imx%22%2C%22eth%22%5D%2C%22id%22%3A1%7D",
            json.toURLEncodedString()
        )
    }
}
