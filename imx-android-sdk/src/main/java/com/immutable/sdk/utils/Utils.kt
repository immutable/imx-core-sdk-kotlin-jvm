package com.immutable.sdk.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import com.immutable.sdk.utils.Constants.DEFAULT_CHROME_CUSTOM_TAB_ADDRESS_BAR_COLOUR

internal object Utils {
    /**
     * Launches a Chrome Custom Tab with the given [url]
     *
     * @param context the source context
     * @param url the URL to open
     * @param toolbarColourInt (optional) the colour of the Chrome Custom Tab address bar. The default
     * value is [DEFAULT_CHROME_CUSTOM_TAB_ADDRESS_BAR_COLOUR]
     */
    fun launchCustomTabs(
        context: Context,
        url: String,
        @ColorInt toolbarColourInt: Int = Color.parseColor(
            DEFAULT_CHROME_CUSTOM_TAB_ADDRESS_BAR_COLOUR
        )
    ) {
        println("launchCustomTabs")
        val customTabsIntent = CustomTabsIntent.Builder().setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(toolbarColourInt)
                .build()
        ).build()
        customTabsIntent.run {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            launchUrl(context, Uri.parse(url))
        }
    }
}
