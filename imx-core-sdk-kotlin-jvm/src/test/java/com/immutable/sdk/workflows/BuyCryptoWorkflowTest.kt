package com.immutable.sdk.workflows

import com.immutable.sdk.ImmutableConfig
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.GetUsersApiResponse
import com.immutable.sdk.extensions.getJson
import com.immutable.sdk.extensions.getJsonArray
import com.immutable.sdk.extensions.toURLEncodedString
import com.immutable.sdk.testFuture
import io.mockk.*
import io.mockk.impl.annotations.MockK
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.util.concurrent.CompletableFuture

private const val COLOUR_HEX = "81d8d0"
private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6fac7f"
private const val STARK_KEY = "0x01236456cb87e2c77dd6b43075c20888069a306aeff7ba36f654543aae80f678"
private const val BUY_CRYPTO_URL = "https://buy.crypto.com"
private const val MOONPAY_SIGNED_REQUEST = "1234567890abcdef="
private const val TRANSACTION_ID = 8L
private const val BASE_URL = "https://ropsten.immutable.com"
private const val API_KEY = "pk_test_api"
private const val CURRENCY_CODE_VALUE = "usdc"
private const val ENCODED_CURRENCIES = "%7B%22eth_immutable"
private const val URL_ENCODED_JSON_STRING = "urlEncodedJsonString"

class BuyCryptoWorkflowTest {
    @MockK
    private lateinit var client: OkHttpClient

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var usersApi: UsersApi

    @MockK
    private lateinit var getUsersApiResponse: GetUsersApiResponse

    @MockK
    private lateinit var call: Call

    @MockK
    private lateinit var response: Response

    @MockK
    private lateinit var jsonObject: JSONObject

    @MockK
    private lateinit var currencyJsonObject: JSONObject

    @MockK
    private lateinit var jsonArray: JSONArray

    @MockK
    private lateinit var base: ImmutableXBase

    private lateinit var addressFuture: CompletableFuture<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        addressFuture.complete(ADDRESS)

        every { usersApi.getUsers(any()) } returns getUsersApiResponse
        every { getUsersApiResponse.accounts } returns arrayListOf(STARK_KEY)

        every { client.newCall(any()) } returns call
        every { call.execute() } returns response

        mockkStatic("com.immutable.sdk.extensions.ResponseKt")
        every { any<Response>().getJson() } returns jsonObject
        every { any<Response>().getJsonArray() } returns jsonArray
        every { jsonArray.length() } returns 1
        every { jsonArray.getJSONObject(0) } returns currencyJsonObject
        every { currencyJsonObject.getString(CURRENCY_CODE) } returns CURRENCY_CODE_VALUE

        mockkStatic("com.immutable.sdk.extensions.JSONObjectKt")
        every { any<JSONObject>().toURLEncodedString() } returns URL_ENCODED_JSON_STRING

        mockkStatic(URLEncoder::class)
        every { URLEncoder.encode(any(), any()) } returns ENCODED_CURRENCIES

        every { jsonObject.getLong(ID) } returns TRANSACTION_ID
        every { jsonObject.getString(SIGNATURE) } returns MOONPAY_SIGNED_REQUEST

        mockkObject(ImmutableConfig)
        every { ImmutableConfig.getPublicApiUrl(base) } returns BASE_URL
        every { ImmutableConfig.getMoonpayApiKey(base) } returns API_KEY
        every { ImmutableConfig.getBuyCryptoUrl(base) } returns BUY_CRYPTO_URL
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun buyCrypto(): CompletableFuture<String> =
        buyCrypto(
            base = base,
            signer = signer,
            client = client,
            colourCodeHex = "#$COLOUR_HEX",
            usersApi = usersApi
        )

    @Test
    fun testBuyCryptoSuccess() {
        val expectedRequestParams = "apiKey=$API_KEY" +
            "&baseCurrencyCode=usd" +
            "&colorCode=%23$COLOUR_HEX" +
            "&externalTransactionId=$TRANSACTION_ID" +
            "&walletAddress=$ADDRESS" +
            "&walletAddresses=" +
            URL_ENCODED_JSON_STRING

        testFuture(
            future = buyCrypto(),
            expectedResult = String.format(
                MOONPAY_BUY_URL,
                BUY_CRYPTO_URL,
                expectedRequestParams,
                MOONPAY_SIGNED_REQUEST
            ),
            expectedError = null
        )
    }

    fun testBuyCryptoFailedOnAddress() {
        every { addressFuture.get() } throws RuntimeException()

        testFuture(
            future = buyCrypto(),
            expectedResult = null,
            expectedError = RuntimeException()
        )
    }

    fun testBuyCryptoFailedOnApiCall() {
        every { call.execute() } throws IOException()

        testFuture(
            future = buyCrypto(),
            expectedResult = null,
            expectedError = IOException()
        )
    }

    fun testBuyCryptoFailedOnGetSupportedCurrencies() {
        every { currencyJsonObject.getString(CURRENCY_CODE) } throws JSONException("")

        testFuture(
            future = buyCrypto(),
            expectedResult = null,
            expectedError = JSONException("")
        )
    }

    fun testBuyCryptoFailedOnGetTransactionId() {
        every { jsonObject.getLong(ID) } throws JSONException("")

        testFuture(
            future = buyCrypto(),
            expectedResult = null,
            expectedError = JSONException("")
        )
    }

    fun testBuyCryptoFailedOnGetBuyCryptoUrl() {
        every { jsonObject.getString(SIGNATURE) } throws JSONException("")

        testFuture(
            future = buyCrypto(),
            expectedResult = null,
            expectedError = JSONException("")
        )
    }

    fun testBuyCryptoWalletNotRegistered_notFound() {
        every { getUsersApiResponse.accounts } throws
            ClientException(statusCode = HttpURLConnection.HTTP_NOT_FOUND)

        testFuture(
            future = buyCrypto(),
            expectedResult = null,
            expectedError = ClientException()
        )
    }

    fun testBuyCryptoWalletNotRegistered_emptyList() {
        every { getUsersApiResponse.accounts } returns emptyList()

        testFuture(
            future = buyCrypto(),
            expectedResult = null,
            expectedError = IllegalStateException()
        )
    }
}
