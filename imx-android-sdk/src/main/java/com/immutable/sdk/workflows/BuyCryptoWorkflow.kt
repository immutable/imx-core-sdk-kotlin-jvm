package com.immutable.sdk.workflows

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.VisibleForTesting
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.Signer
import com.immutable.sdk.extensions.getJson
import com.immutable.sdk.extensions.getJsonArray
import com.immutable.sdk.extensions.toURLEncodedString
import com.immutable.sdk.model.GetSignedMoonpayRequest
import com.immutable.sdk.model.GetTransactionIdRequest
import com.immutable.sdk.utils.Utils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@VisibleForTesting
internal const val ID = "id"

@VisibleForTesting
internal const val SIGNATURE = "signature"

@VisibleForTesting
internal const val CURRENCY_CODE = "currency_code"

private const val ENCODED_EQUAL_SIGN = "%3D"
private const val HASH_SIGN = "#"

private const val MOONPAY = "moonpay"

@VisibleForTesting
internal const val MOONPAY_BUY_URL =
    "https://buy-staging.moonpay.io/?%s&signature=%s"
private const val GET_TRANSACTION_ID_URL_PATH = "v2/exchanges"
private const val MOONPAY_GET_SIGNATURE_URL_PATH = "v2/moonpay/sign-url"
private const val GET_CURRENCIES = "v2/exchanges/currencies/fiat-to-crypto"

private const val APPLICATION_JSON = "application/json"

private const val HEADER_ACCEPT = "Accept"
private const val HEADER_CONTENT_TYPE = "Content-Type"

/**
 * Launches a Chrome Custom Tab to buy cryptocurrencies via Moonpay
 *
 * @throws Exception if anything error occurs
 */
@Suppress("TooGenericExceptionCaught", "LongParameterList")
fun buyCrypto(
    base: ImmutableXBase,
    context: Context,
    signer: Signer,
    client: OkHttpClient = OkHttpClient.Builder().build(),
    @ColorInt colourInt: Int,
    colourCodeHex: String
) {
    try {
        // Get connected wallet address
        val address = signer.getAddress().get()
        // Get transaction ID
        val transactionId = getTransactionId(walletAddress = address, base = base, client = client)
        // Get supported fiat to crypto currencies
        val currencies = getSupportedCurrencies(walletAddress = address, base = base, client = client)
        // Get Moonpay buy crypto URL
        val url = getBuyCryptoUrl(
            walletAddress = address,
            transactionId = transactionId,
            currencies = currencies,
            base = base,
            client = client,
            colourCodeHex = colourCodeHex
        )
        // Launch Chrome Custom Tab with Moonpay buy crypto URL
        Utils.launchCustomTabs(
            context = context,
            url = url,
            toolbarColourInt = colourInt
        )
    } catch (e: Exception) {
        e.cause?.let { throw it } ?: throw e
    }
}

private fun getTransactionId(
    walletAddress: String,
    base: ImmutableXBase,
    client: OkHttpClient
): Long {
    val response = post(
        urlPath = GET_TRANSACTION_ID_URL_PATH,
        jsonBody = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter(GetTransactionIdRequest::class.java)
            .toJson(GetTransactionIdRequest(walletAddress = walletAddress, provider = MOONPAY)),
        base = base,
        client = client
    )
    return response.getLong(ID)
}

private fun getSupportedCurrencies(
    walletAddress: String,
    base: ImmutableXBase,
    client: OkHttpClient
): Map<String, String> {
    val request: Request = Request.Builder()
        .url("${base.url}/$GET_CURRENCIES")
        .get()
        .addHeader(HEADER_ACCEPT, APPLICATION_JSON)
        .addHeader(HEADER_CONTENT_TYPE, APPLICATION_JSON)
        .build()
    val response = client.newCall(request).execute()
    val currencies = hashMapOf<String, String>()
    response.getJsonArray()?.let { array ->
        for (i in 0 until array.length()) {
            val code = array.getJSONObject(i).getString(CURRENCY_CODE)
            currencies[code] = walletAddress
        }
    }
    return currencies
}

@Suppress("LongParameterList")
private fun getBuyCryptoUrl(
    walletAddress: String,
    transactionId: Long,
    currencies: Map<String, String>,
    base: ImmutableXBase,
    client: OkHttpClient,
    colourCodeHex: String
): String {
    val currenciesJson = JSONObject(currencies).toURLEncodedString()
    println(currenciesJson)
    val requestParams = "apiKey=${base.moonpayApiKey}" +
        "&baseCurrencyCode=usd" +
        "&colorCode=%23${colourCodeHex.replace(HASH_SIGN, "")}" +
        "&externalTransactionId=$transactionId" +
        "&walletAddress=$walletAddress" +
        "&walletAddresses=" +
        currenciesJson
    val response = post(
        urlPath = MOONPAY_GET_SIGNATURE_URL_PATH,
        jsonBody = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(GetSignedMoonpayRequest::class.java)
            .toJson(GetSignedMoonpayRequest(request = requestParams)),
        base = base,
        client = client
    )
    val signature = response.getString(SIGNATURE)
        // Remove equal sign at the end
        .replace(ENCODED_EQUAL_SIGN, "")
    return String.format(MOONPAY_BUY_URL, requestParams, signature)
}

@Suppress("TooGenericExceptionCaught")
private fun post(
    urlPath: String,
    jsonBody: String,
    base: ImmutableXBase,
    client: OkHttpClient
): JSONObject {
    val request: Request = Request.Builder()
        .url("${base.url}/$urlPath")
        .post(jsonBody.toRequestBody())
        .addHeader(HEADER_ACCEPT, APPLICATION_JSON)
        .addHeader(HEADER_CONTENT_TYPE, APPLICATION_JSON)
        .build()
    val response = client.newCall(request).execute()
    return response.getJson() ?: JSONObject()
}
