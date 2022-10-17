package com.immutable.sdkdemo

import com.immutable.sdk.ImmutableX
import com.immutable.sdk.ImmutableXBase

fun main() {
    val immutableX = ImmutableX(ImmutableXBase.Sandbox)
    println(immutableX.listCollections().result.joinToString { it.name })
}