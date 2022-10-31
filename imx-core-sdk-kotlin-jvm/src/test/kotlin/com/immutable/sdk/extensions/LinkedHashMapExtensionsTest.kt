package com.immutable.sdk.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

class LinkedHashMapExtensionsTest {
    @Test
    fun testLinkedHashMapToJsonString() {
        val map = LinkedHashMap<String, Any>()
        map["key1"] = "value"
        map["key2"] = true
        map["key3"] = 1
        map["key4"] = LinkedHashMap<String, Any>().apply {
            put("1", "valueA")
            put("2", "valueB")
        }
        map["key5"] = arrayListOf(
            LinkedHashMap<String, Any>().apply {
                put("a", "value1")
            },
            LinkedHashMap<String, Any>().apply {
                put("b", "value2")
            },
        )

        assertEquals(
            "{\"key1\":\"value\",\"key2\":true,\"key3\":1," +
                "\"key4\":{\"1\":\"valueA\",\"2\":\"valueB\"}," +
                "\"key5\":[{\"a\":\"value1\"},{\"b\":\"value2\"}]}",
            map.toJsonString()
        )
    }

    @Test
    fun testLinkedHashMapToJsonString_emptyMap() {
        val map = LinkedHashMap<String, Any>()

        assertEquals("{}", map.toJsonString())
    }
}
