package com.immutable.sdk

internal object ImmutableConfig {
    private object Production {
        const val PUBLIC_API_URL = "https://api.x.immutable.com"
        const val MOONPAY_BUY_CRYPTO_URL = "https://buy.moonpay.io"
        const val MOONPAY_API_KEY = "pk_live_lgGxv3WyWjnWff44ch4gmolN0953"
        const val CORE_CONTRACT_ADDRESS = "0x5FDCCA53617f4d2b9134B29090C87D01058e27e9"
        const val REGISTRATION_CONTRACT_ADDRESS = "0x72a06bf2a1CE5e39cBA06c0CAb824960B587d64c"
    }

    private object Sandbox {
        const val PUBLIC_API_URL = "https://api.sandbox.x.immutable.com"
        const val MOONPAY_BUY_CRYPTO_URL = "https://buy-staging.moonpay.io"
        const val MOONPAY_API_KEY = "pk_test_nGdsu1IBkjiFzmEvN8ddf4gM9GNy5Sgz"
        const val CORE_CONTRACT_ADDRESS = "0x7917eDb51ecD6CdB3F9854c3cc593F33de10c623"
        const val REGISTRATION_CONTRACT_ADDRESS = "0x1C97Ada273C9A52253f463042f29117090Cd7D83"
    }

    private object Ropsten {
        const val PUBLIC_API_URL = "https://api.ropsten.x.immutable.com"
        const val MOONPAY_BUY_CRYPTO_URL = "https://buy-staging.moonpay.io"
        const val MOONPAY_API_KEY = "pk_test_nGdsu1IBkjiFzmEvN8ddf4gM9GNy5Sgz"
        const val CORE_CONTRACT_ADDRESS = "0x4527BE8f31E2ebFbEF4fCADDb5a17447B27d2aef"
        const val REGISTRATION_CONTRACT_ADDRESS = "0x6C21EC8DE44AE44D0992ec3e2d9f1aBb6207D864"
    }

    fun getPublicApiUrl(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.PUBLIC_API_URL
        ImmutableXBase.Ropsten -> Ropsten.PUBLIC_API_URL
        ImmutableXBase.Sandbox -> Sandbox.PUBLIC_API_URL
    }

    fun getMoonpayApiKey(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.MOONPAY_API_KEY
        ImmutableXBase.Ropsten -> Ropsten.MOONPAY_API_KEY
        ImmutableXBase.Sandbox -> Sandbox.MOONPAY_API_KEY
    }

    fun getBuyCryptoUrl(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.MOONPAY_BUY_CRYPTO_URL
        ImmutableXBase.Ropsten -> Ropsten.MOONPAY_BUY_CRYPTO_URL
        ImmutableXBase.Sandbox -> Sandbox.MOONPAY_BUY_CRYPTO_URL
    }

    fun getCoreContractAddress(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.CORE_CONTRACT_ADDRESS
        ImmutableXBase.Ropsten -> Ropsten.CORE_CONTRACT_ADDRESS
        ImmutableXBase.Sandbox -> Sandbox.CORE_CONTRACT_ADDRESS
    }

    fun getRegistrationContractAddress(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.REGISTRATION_CONTRACT_ADDRESS
        ImmutableXBase.Ropsten -> Ropsten.REGISTRATION_CONTRACT_ADDRESS
        ImmutableXBase.Sandbox -> Sandbox.REGISTRATION_CONTRACT_ADDRESS
    }
}
