package com.immutable.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.immutable.sdk.api.CollectionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Change Base to Ropsten
        ImmutableXSdk.setBase(ImmutableXBase.Ropsten)

        GlobalScope.launch(Dispatchers.Default) {
            val response = CollectionsApi().getCollections(
                20,
                null,
                null,
                null,
                null
            )
            println("COLLECTIONS: " + response.result?.joinToString { it.name ?: "no name" })
        }
    }
}