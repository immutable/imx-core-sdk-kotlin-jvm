package com.immutable.sdk.extensions

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

private const val KEY1 = "transactionHash"
private const val KEY2 = "status"
private const val VALUE1 = "0x1234"
private const val VALUE2 = "0xabcd"
private const val VALUE3 = "minted_to_l1"
private const val MESSAGE = "done"
private const val URL = "https://immutable.com/api"

class ResponseExtensionsTest {
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createResponseBuilder() = Response.Builder()
        .code(HttpURLConnection.HTTP_OK)
        .message(MESSAGE)
        .protocol(Protocol.HTTP_2)
        .request(Request.Builder().url(URL).build())

    @Test
    fun testGetJson() {
        val json = JSONObject()
        json.put(KEY1, VALUE1)
        json.put(KEY2, VALUE3)

        val responseString = json.toString()
        val responseBody = responseString.toResponseBody()
        val response = createResponseBuilder()
            .body(responseBody)
            .build()

        val actual = response.getJson()
        assertEquals(VALUE1, actual?.get(KEY1))
        assertEquals(VALUE3, actual?.get(KEY2))
    }

    @Test
    fun testGetJson_noBody() {
        val response = createResponseBuilder().build()
        assertNull(response.getJson())
    }

    @Test
    fun testGetJsonArray() {
        val json = JSONArray()
        json.put(VALUE1)
        json.put(VALUE2)

        val responseString = json.toString()
        val responseBody = responseString.toResponseBody()
        val response = createResponseBuilder()
            .body(responseBody)
            .build()

        val actual = response.getJsonArray()
        assertEquals(VALUE1, actual?.get(0))
        assertEquals(VALUE2, actual?.get(1))
    }

    @Test
    fun testGetJsonArray_noBody() {
        val response = createResponseBuilder().build()
        assertNull(response.getJsonArray())
    }
}
