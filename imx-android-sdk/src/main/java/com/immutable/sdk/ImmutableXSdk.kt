package com.immutable.sdk

import android.os.NetworkOnMainThreadException
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.utils.login
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ServerException
import org.web3j.crypto.ECKeyPair
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

enum class ImmutableXBase(val url: String) {
    Production("https://api.x.immutable.com"),
    Ropsten("https://api.ropsten.x.immutable.com")
}

object ImmutableXSdk {
    /**
     * Sets the base property used by all Immutable X API classes.
     */
    fun setBase(base: ImmutableXBase) {
        System.getProperties().setProperty("org.openapitools.client.baseUrl", base.url)
    }

    /**
     * This is a utility function that will register a user to Immutable X if they aren't already
     * and then return their Stark key pair. This must be called from a background thread.
     *
     * @throws [InterruptedException]
     * @throws [ExecutionException]
     * @throws [NetworkOnMainThreadException] if this method isn't run on a background thread
     * @throws [ClientException] if the api requests fail due to a client error
     * @throws [ServerException] if the api requests fail due to a server error
     * @throws [UnsupportedOperationException] if the api response is informational or redirect
     */
    fun login(signer: Signer): CompletableFuture<ECKeyPair> = login(signer, UsersApi())
}
