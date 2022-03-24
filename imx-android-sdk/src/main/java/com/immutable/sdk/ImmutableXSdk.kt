package com.immutable.sdk

import android.os.NetworkOnMainThreadException
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.crypto.CryptoUtil
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.extensions.sanitizeBytes
import com.immutable.sdk.extensions.toHexString
import com.immutable.sdk.model.RegisterUserRequestVerifyEth
import com.immutable.sdk.stark.StarkCurve
import com.immutable.sdk.stark.StarkKey
import com.immutable.sdk.utils.Constants
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ServerException
import org.web3j.crypto.ECKeyPair
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
    fun login(signer: Signer): ECKeyPair {
        val api = UsersApi()
        val address = signer.getAddress().get()
        val starkSeed = signer.signMessage(Constants.STARK_MESSAGE).get()

        val keyPair = StarkKey.getKeyFromRawSignature(starkSeed, address)
            ?: throw ImmutableException("Failed to generate Stark key pair")

        val isRegistered = api.getUser(address).accounts?.isNotEmpty() == true
        if (!isRegistered) {
            val ethSignature = signer.signMessage(Constants.REGISTER_SIGN_MESSAGE).get()
            val starkPublicKey = keyPair.getStarkPublicKey()
            api.registerUser(
                RegisterUserRequestVerifyEth(
                    ethSignature = ethSignature,
                    etherKey = address,
                    starkKey = starkPublicKey,
                    starkSignature = StarkCurve.sign(
                        keyPair,
                        CryptoUtil.getRegisterUserMsgVerifyEth(address, starkPublicKey)
                    )
                )
            )
        }

        return keyPair
    }

    private fun ECKeyPair.getStarkPublicKey() =
        publicKey.toString(Constants.HEX_RADIX)
            .sanitizeBytes()
            .hexToByteArray()
            .toHexString(byteLength = Constants.STARK_KEY_PUBLIC_BYTE_LENGTH)
}
