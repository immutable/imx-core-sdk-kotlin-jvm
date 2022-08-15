# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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

## [0.6.0] - 2022-08-12

### Added

* Added `StandardStarkSigner` as the generic implementation of `StarkSigner`

### Changed

* Updated OpenAPI spec which includes breaking change to the `Transfer` object, replacing `data` and `type` with `token` field.
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