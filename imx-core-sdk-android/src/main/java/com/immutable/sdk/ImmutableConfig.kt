package com.immutable.sdk

internal object ImmutableConfig {
    private object Production {
        const val PUBLIC_API_URL = "https://api.x.immutable.com"
        const val MOONPAY_BUY_CRYPTO_URL = "https://buy.moonpay.io"
        const val MOONPAY_API_KEY = "pk_live_lgGxv3WyWjnWff44ch4gmolN0953"
    }

    private object Ropsten {
        const val PUBLIC_API_URL = "https://api.ropsten.x.immutable.com"
        const val MOONPAY_BUY_CRYPTO_URL = "https://buy-staging.moonpay.io"
        const val MOONPAY_API_KEY = "pk_test_nGdsu1IBkjiFzmEvN8ddf4gM9GNy5Sgz"
    }

    fun getPublicApiUrl(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.PUBLIC_API_URL
        ImmutableXBase.Ropsten -> Ropsten.PUBLIC_API_URL
    }

    fun getMoonpayApiKey(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.MOONPAY_API_KEY
        ImmutableXBase.Ropsten -> Ropsten.MOONPAY_API_KEY
    }

    fun getBuyCryptoUrl(base: ImmutableXBase) = when (base) {
        ImmutableXBase.Production -> Production.MOONPAY_BUY_CRYPTO_URL
        ImmutableXBase.Ropsten -> Ropsten.MOONPAY_BUY_CRYPTO_URL
    }
}
