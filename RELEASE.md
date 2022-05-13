# How to release SDK

## How to release to Github Packages

1. Test your code locally using the sample app or [ImmutableX Wallet Android SDK](https://github.com/immutable/imx-wallet-android-sdk) sample app.
2. Increase the version in `imx-core-sdk-android/build.gradle`
3. Create a PR (branch name needs to start with `releases/`)
4. Get approval for your PR
5. Merge your PR
6. If the [Release to Github Package](https://github.com/immutable/imx-core-sdk-android/actions/workflows/releaseGithubPackage.yml) workflow is successful, you should see the new version in [Packages - com.immutable.sdk.imx-core-sdk-android](https://github.com/immutable/imx-core-sdk-android/packages/1410108).