package com.immutable.sdkdemo

import com.immutable.sdk.*
import com.immutable.sdk.crypto.StarkKey.generate
import org.web3j.crypto.Credentials
import org.web3j.crypto.Sign
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

fun main() {
    try {
        println("starting...")

        val base = ImmutableXBase.Sandbox
        val core = ImmutableX(base)
        core.setHttpLoggingLevel(ImmutableXHttpLoggingLevel.Body)

//        val pubKey = "0x3d22885544eB026AA73310871c3DDdfb7588BFf4"
//        val pk = "eb0c7355c7825b583263a1912ccb6b30fac52def230c2cb3312b816597e3068f"
        val pubKey = "0x065E2F8Ab2e18fd1850c63326A9afE1b599F67bc"
        val pk = "7b6fae1b20928c30b5a7dd7327bfb0f066bec84f71045748d91a36af4cf1d03d"
        val credentials: Credentials = Credentials.create(pk)
        println("pubKey = ${credentials.address}")

        val l1Signer = L1Signer(credentials)
        val l2Signer = StandardStarkSigner(generate(l1Signer).get())

        core.deposit(l1Signer).get()
    } catch (e: Exception) {
        println("exception: " + e.message)
    }

//    println("List of collections: ${CollectionsApi().listCollections().result.joinToString { it.name }}")
}

class L1Signer(private val credentials: Credentials) : Signer {

    override fun getAddress(): CompletableFuture<String> {
        return CompletableFuture.completedFuture(credentials.address)
    }

    override fun signPrefixedMessage(message: String): CompletableFuture<String> {
        val signatureData = Sign.signPrefixedMessage(message.toByteArray(), credentials.ecKeyPair)
        val retval = ByteArray(65)
        System.arraycopy(signatureData.r, 0, retval, 0, 32)
        System.arraycopy(signatureData.s, 0, retval, 32, 32)
        System.arraycopy(signatureData.v, 0, retval, 64, 1)
        val signed = Numeric.toHexString(retval)
        return CompletableFuture.completedFuture(signed)
    }

    override fun signMessage(message: String): CompletableFuture<Sign.SignatureData> {
        val signatureData =
            Sign.signMessage(Numeric.hexStringToByteArray(message), credentials.ecKeyPair)
        return CompletableFuture.completedFuture(signatureData)
    }
}
