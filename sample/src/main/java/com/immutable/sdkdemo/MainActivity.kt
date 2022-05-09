package com.immutable.sdkdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.immutable.sdk.*
import com.immutable.sdk.api.CollectionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import link.magic.android.Magic
import link.magic.android.core.relayer.urlBuilder.CustomNodeConfiguration
import link.magic.android.modules.auth.requestConfiguration.LoginWithMagicLinkConfiguration
import org.web3j.crypto.ECKeyPair
import org.web3j.protocol.admin.methods.response.PersonalSign
import org.web3j.protocol.core.Request
import java.util.*
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity() {
    private var magic: Magic = Magic(
        "pk_live_A7D9211D7547A338",
        CustomNodeConfiguration(
            "https://ropsten.infura.io/v3/35927ba871b64bd4b03238a3479b360b",
            "3"
        )
    )
    private val magicSigner = MagicSigner(magic)

    class MagicSigner(val magic: Magic) : Signer, StarkSigner {
        var keyPair: ECKeyPair? = null

        override fun getAddress(): CompletableFuture<String> {
            val future = CompletableFuture<String>()
            magic.user.getMetadata()
                .thenApply { it.result.publicAddress }
                .whenComplete { address, error ->
                    when {
                        error != null -> future.completeExceptionally(error)
                        address.isNullOrEmpty() -> future.completeExceptionally(Exception("Could not get signer address"))
                        else -> future.complete(address.lowercase())
                    }
                }
            return future
        }

        override fun signMessage(message: String): CompletableFuture<String> {
            val future = CompletableFuture<String>()
            getAddress().whenComplete { address, throwable ->
                if (throwable != null)
                    future.completeExceptionally(throwable)
                else
                    magic.rpcProvider.sendAsync(
                        Request(
                            "personal_sign",
                            Arrays.asList<String>(message, address),
                            magic.rpcProvider,
                            PersonalSign::class.java
                        ),
                        PersonalSign::class.java
                    ).whenComplete { result, error ->
                        if (error != null)
                            future.completeExceptionally(error)
                        else
                            future.complete(result.signedMessage)
                    }
            }
            return future
        }

        override fun getStarkKeys(): CompletableFuture<ECKeyPair> =
            CompletableFuture.supplyAsync { keyPair }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        magic.startRelayer(this)

        // Change Base to Ropsten
        ImmutableXSdk.setBase(ImmutableXBase.Ropsten)

        findViewById<View>(R.id.text).postDelayed({
            magic.auth.loginWithMagicLink(LoginWithMagicLinkConfiguration("dominic.murray+22@immutable.com"))
                .whenComplete { _, error ->
                    if (error == null) {
                        ImmutableXSdk.login(magicSigner).whenComplete { keyPair, error ->
                            println("Main activity complete $keyPair $error")
                            magicSigner.keyPair = keyPair
                        }
                    }
                }
        }, 1000L)

        GlobalScope.launch(Dispatchers.Default) {
            val response = CollectionsApi().listCollections(pageSize = 20)
            println("COLLECTIONS: " + response.result?.joinToString { it.name ?: "no name" })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        magic.destroyRelayer()
    }
}