package com.immutable.sdkdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.immutable.sdk.ImmutableXBase
import com.immutable.sdk.ImmutableXHttpLoggingLevel
import com.immutable.sdk.ImmutableXSdk
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

        // Set API logging level
        ImmutableXSdk.setHttpLoggingLevel(ImmutableXHttpLoggingLevel.Body)

        GlobalScope.launch(Dispatchers.Default) {
            val response = CollectionsApi().listCollections(pageSize = 20)
            println("COLLECTIONS: " + response.result?.joinToString { it.name ?: "no name" })
        }
    }
}