package com.immutable.sdk.extensions

/**
 * Calls the given [block] if both [first] and [second] are not null.
 */
internal fun <T, U, V> ifNotNull(first: T?, second: U?, block: (T, U) -> V): V? {
    return if (first != null && second != null) block(first, second) else null
}
