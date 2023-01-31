<div align="center">
  <p align="center">
    <a  href="https://docs.x.immutable.com/docs">
      <img src="https://cdn.dribbble.com/users/1299339/screenshots/7133657/media/837237d447d36581ebd59ec36d30daea.gif" width="280"/>
    </a>
  </p>
</div>

---

# Immutable X Core SDK Kotlin/JVM

The Immutable X Core SDK Kotlin/JVM provides convenient access to the Immutable API's for applications written on the Immutable X platform.

## Documentation

See the [developer guides](https://docs.x.immutable.com) for information on building on Immutable X.

See the [API reference documentation](https://docs.x.immutable.com/reference) for more information on our API's.

### Examples
* **Sample code** - see the [sample](./sample/) folder for examples of key SDK functionality.

## Installation

1. Add Maven Central to your repositories
```gradle
repositories {
    mavenCentral()
}
```
2. Add dependency to your app `build.gradle` file
```gradle
dependencies {
    implementation 'com.immutable.sdk:imx-core-sdk-kotlin-jvm:$version'
}
```
3. Set the correct environment (defaults to Production)
```kt
val immutableX = ImmutableX(ImmutableXBase.Sandbox)
```

### Environments

| Environment | Description |
| -- | -- |
| `ImmutableXBase.Sandbox` | The default test network (currently, it is Goërli) |
| `ImmutableXBase.Ropsten` | Ropsten test network (to be deprecated soon) |
| `ImmutableXBase.Production` | Ethereum network |

## Quickstart

### Standard API Requests

The Core SDK includes functions that interact with the Immutable X APIs.

e.g. Get a list of collections ordered by name in ascending order
```kt
val response = immutableX.listCollections(
    pageSize = 20,
    orderBy = "name",
    direction = "asc"
)
```
OR
```kt
val response = immutableX.collectionsApi.listCollections(
    pageSize = 20,
    orderBy = "name",
    direction = "asc"
)
```
View the [OpenAPI spec](openapi.json) for a full list of API requests available in the Core SDK.

## Workflow Functions

Utility functions that will chain necessary API calls to complete a process or perform a transaction.

* Register a user with Immutable X
* Buy cryptocurrency via Moonpay
* Buy ERC721
* Sell ERC721
* Cancel order
* Transfer ERC20/ERC721/ETH
* Deposit ERC20/ERC721/ETH
* Withdraw ERC20/ERC721/ETH
* Check if user is registered on chain

### Wallet Connection

In order to call authorised API and use workflow functions, you will need to pass in the connected wallet provider. This means you will need to implement your own Wallet L1 [Signer](https://github.com/immutable/imx-core-sdk-kotlin-jvm/blob/main/imx-core-sdk-kotlin-jvm/src/main/kotlin/com/immutable/sdk/Signer.kt) and L2 [StarkSigner](https://github.com/immutable/imx-core-sdk-kotlin-jvm/blob/main/imx-core-sdk-kotlin-jvm/src/main/kotlin/com/immutable/sdk/Signer.kt).

#### L1 Signer

Example implementation of the L1 Signer using [web3j](https://github.com/web3j/web3j):

```kt
class L1Signer(private val credentials: Credentials) : Signer {

    val web3j = Web3j.build(HttpService(NODE_URL))

    override fun getAddress(): CompletableFuture<String> {
        return CompletableFuture.completedFuture(credentials.address)
    }

    override fun signMessage(message: String): CompletableFuture<String> {
        val signatureData = Sign.signPrefixedMessage(message.toByteArray(), credentials.ecKeyPair)
        val retval = ByteArray(65)
        System.arraycopy(signatureData.r, 0, retval, 0, 32)
        System.arraycopy(signatureData.s, 0, retval, 32, 32)
        System.arraycopy(signatureData.v, 0, retval, 64, 1)
        val signed = Numeric.toHexString(retval)
        return CompletableFuture.completedFuture(signed)
    }

    @Suppress("TooGenericExceptionCaught")
    override fun sendTransaction(rawTransaction: RawTransaction): CompletableFuture<String> {
        val signedTransaction = TransactionEncoder.signMessage(rawTransaction, credentials)
        return web3j.ethSendRawTransaction(Numeric.toHexString(signedTransaction)).sendAsync()
            .thenApply { it.transactionHash }
    }
}
```

#### L2 StarkSigner

Use `StarkKey.generateStarkPrivateKey()` to create an instance of `StandardStarkSigner`, an implementation of `StarkSigner`.
#### 🚨🚨🚨 Warning 🚨🚨🚨
> You will have to persist the Stark private key. The key is [randomly generated](/src/utils/stark/starkCurve.ts#L99) so **_cannot_** be deterministically re-generated.
```kt
val starkPrivateKey = StarkKey.generateStarkPrivateKey()
val starkSigner = StandardStarkSigner(starkPrivateKey)
```

## Autogenerated Code

Parts of the Core SDK are automagically generated.

### API Autogenerated Code

We use OpenAPI (formally known as Swagger) to auto-generate the API clients that connect to the public APIs.

The OpenAPI spec is retrieved from https://api.x.immutable.com/openapi and also saved in the repo [here](openapi.json).

When updating the `org.openapi.generator` plugin ensure that any custom templates are appropriately modified or removed. These can be found in the .openapi-generator/templates directory.

The generation always happens on preBuild so triggering a project build will regenerate them.

## Wrapping CompletableFuture

### RxJava
Rx provides a `fromFuture` method that will trigger `future.get()` which is a blocking call so it must be moved off the main thread.

Disposing the resulting observable will not cancel the future so that needs to be done manually using `doOnDispose`.
```kt
val future = immutableX.cancelOrder(orderId)
Observable.fromFuture(future)
    .subscribeOn(Schedulers.io())
    .doOnDispose { future.cancel(true) }
    .subscribe({ handleSuccess() }, { handleError() })
```

### Coroutines
The Kotlin team has a set of packages for jdk8 that provide an easy extension for using CompletableFuture.

First add this import to your project:
```kt
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutines_version"
```

Then simply call `.await()` on the workflow `CompletableFuture` and wrap it with a try/catch to handle any exceptions.
```kt
launch(Dispatchers.Default) {
    try {
        val id = immutableX.cancelOrder(orderId).await()
    } catch (e: Exception) {
        handleError(e)
    }
}
```

## Changelog Management

The following headings should be used as appropriate

- Added
- Changed
- Deprecated
- Removed
- Fixed

What follows is an example with all the change headings, for real world use only use headings when appropriate.
This goes at the top of the CHANGELOG.md above the most recent release.

```markdown
...

## [Unreleased]

### Added

for new features.

### Changed

for changes in existing functionality.

### Deprecated

for soon-to-be removed features.

### Removed

for now removed features.

### Fixed

for any bug fixes.

...
```

The version.yml will hold the value of the previous release

```yaml
version: 0.1.0
```

## Contributing

If you would like to contribute, please read the following:

- We use the [Conventional Commits specification](https://www.conventionalcommits.org/en/v1.0.0/#specification) when writing our commit messages. Why use Conventional Commits? Read [here](https://www.conventionalcommits.org/en/v1.0.0/#why-use-conventional-commits).

## Maintainers

- [How to release](./RELEASE.md)

## Getting Help

Immutable X is open to all to build on, with no approvals required. If you want to talk to us to learn more, or apply for developer grants, click below:

[Contact us](https://www.immutable.com/contact)

### Project Support

To get help from other developers, discuss ideas, and stay up-to-date on what's happening, become a part of our community on Discord.

[Join us on Discord](https://discord.gg/TkVumkJ9D6)

You can also join the conversation, connect with other projects, and ask questions in our Immutable X Discourse forum.

[Visit the forum](https://forum.immutable.com/)

#### Still need help?

You can also apply for marketing support for your project. Or, if you need help with an issue related to what you're building with Immutable X, click below to submit an issue. Select _I have a question_ or _issue related to building on Immutable X_ as your issue type.

[Contact support](https://support.immutable.com/hc/en-us/requests/new)

## License
ImmutableX Core SDK Kotlin/JVM repository is distributed under the terms of the [Apache License (Version 2.0)](LICENSE).