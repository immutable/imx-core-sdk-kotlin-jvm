# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project
adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html)

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

## [1.0.0-beta.2] - 2022-12-01

### Changed

* `Signer` `signTransaction` method to `sendTransaction`
* `StarkKey.generateLegacyStarkPrivateKey` to take in a `signer` instead of the seed and address

## [1.0.0-beta.1] - 2022-11-28

### Added

* Added Deposit and Withdraw ETH/ERC20/ERC721 workflows
* Ability to check if user is registered on chain
* Support for Sandbox environment
* Added `StarkKey.generateStarkPrivateKey` and `StarkKey.generateLegacyStarkPrivateKey`

### Changed

* Changed `Crypto` object to internal
* Changed `ECKeyPair.getStarkPublicKey()` extension to internal
* Expose fewer public methods to make it easier for us to maintain the SDK.
* Introduced a [single entry point](https://github.com/immutable/imx-core-sdk-kotlin-jvm/blob/main/imx-core-sdk-kotlin-jvm/src/main/kotlin/com/immutable/sdk/ImmutableX.kt) for the SDK to improve discoverability
* ImmutableX class exposes APIs for users who need access outside of basic workflows

### Deprecated

* Ropsten test network

### Removed

* Key derivation

### Fixed

* Incorrect transfer amount in Transfer workflow

## [0.6.0] - 2022-08-12

### Added

* Added `StandardStarkSigner` as the generic implementation of `StarkSigner`

### Changed

* Updated OpenAPI spec which includes breaking change to the `Transfer` object, replacing `data`
  and `type` with `token` field.
* Renamed `cancel` workflow to `cancelOrder`
* `StarkKey.sign` was made private, use `StandardStarkSigner` to handle this case

## [0.5.2] - 2022-07-05

### Added

* Added `ethSignature` and `ethAddress` headers to workflows

### Fixed

* `StarkKey.fixMessage` not stripping hex

## [0.5.1] - 2022-06-20

Initial release with a client for the public API and the following workflows:

* Buy
* Sell
* Cancel sell
* Transfer
* Register
* Buy crypto