package com.immutable.sdk.extensions

/**
 * Converts the linked hash map to a JSON string
 */
internal fun <K, V> LinkedHashMap<K, V>.toJsonString(): String {
    val i = entries.iterator()
    if (!i.hasNext()) return "{}"
    else {
        val sb = StringBuilder()
        sb.append('{')
        while (true) {
            val (key, value) = i.next()
            sb.append("\"$key\"")
            sb.append(':')
            sb.append(
                when (value) {
                    is String -> "\"$value\""
                    is LinkedHashMap<*, *> -> value.toJsonString()
                    is List<*> -> value.map { (it as? LinkedHashMap<*, *>)?.toJsonString() }
                        .joinToString(
                            separator = ",",
                            prefix = "[",
                            postfix = "]"
                        )
                    else -> value
                }
            )
            if (!i.hasNext()) {
                return sb.append('}').toString()
            }
            sb.append(',')
        }
    }
}
