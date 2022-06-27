<div align="center">
  <p align="center">
    <a  href="https://docs.x.immutable.com/docs">
      <img src="https://cdn.dribbble.com/users/1299339/screenshots/7133657/media/837237d447d36581ebd59ec36d30daea.gif" width="280"/>
    </a>
  </p>
</div>

---

# ImmutableX Core SDK Kotlin/JVM

The Immutable Core SDK Kotlin/JVM provides convenient access to the Immutable API's for applications written on the Immutable X platform.

## Documentation

See the [developer guides](https://docs.x.immutable.com) for information on building on Immutable X.

See the [API reference documentation](https://docs.x.immutable.com/reference) for more information on our API's.

## Installation

1. Add dependency to your app `build.gradle` file:
```gradle
dependencies {
    implementation 'com.immutable.sdk:imx-core-sdk-kotlin-jvm:$version'
}
```
2. Set the correct environment (Ropsten or Production/Mainnet)
```kt
ImmutableXSdk.setBase(ImmutableXBase.Ropsten)
```

## Usage

### Standard API Requests

The Core SDK includes classes that interact with the Immutable X APIs.

e.g. Get a list of collections ordered by name in ascending order
```kt
val response = CollectionsApi().getCollections(
    pageSize = 20,
    orderBy = "name",
    direction = "asc"
)
```
View the [OpenAPI spec](openapi.json) for a full list of API requests available in the Core SDK.

## Workflow Functions

Utility functions that will chain necessary API calls to complete a process or perform a transaction.

* Register a user with ImmutableX and returns the Stark key pair
* Buy cryptocurrency via Moonpay
* Buy ERC721
* Sell ERC721
* Cancel listing
* Transfer ERC20/ERC721/ETH

### Wallet Connection

In order to use any workflow functions, you will need to pass in the connected wallet provider. This means you will need to implement your own Wallet L1 [Signer](https://github.com/immutable/imx-core-sdk-kotlin-jvm/blob/main/imx-core-sdk-kotlin-jvm/src/main/kotlin/com/immutable/sdk/Signer.kt) and L2 [StarkSigner](https://github.com/immutable/imx-core-sdk-kotlin-jvm/blob/main/imx-core-sdk-kotlin-jvm/src/main/kotlin/com/immutable/sdk/Signer.kt).

Once you have a `Signer` instance you can generate the user's Stark key pair and use the result to implement a `StarkSigner`.
```kt
StarkKey.generate(signer).whenComplete { keyPair, error ->
    StarkKey.sign(keyPair, "0x5369676e2074686973")
}
```

## Autogenerated Code

Parts of the Core SDK are automagically generated.

### API Autogenerated Code

We use OpenAPI (formally known as Swagger) to auto-generate the API clients that connect to the public APIs.

The OpenAPI spec is retrieved from https://api.x.immutable.com/openapi and also saved in the repo [here](openapi.json).

When updating the `org.openapi.generator` plugin ensure that any custom templates are appropriately modified or removed. These can be found in the .openapi-generator/templates directory.

The generation always happens on preBuild so triggering a project build will regenerate them.

### Documentation

The documentation in /docs is autogenerated using Dokka and can be triggered by calling `./gradlew dokkaHtml`

Every build the docs are cleared and regenerated to ensure they are kept up to date.

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
