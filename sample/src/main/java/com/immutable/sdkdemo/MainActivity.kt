import com.immutable.sdk.api.CollectionsApi

fun main() {
    println("List of collections: ${CollectionsApi().listCollections().result.joinToString { it.name }}")
}
