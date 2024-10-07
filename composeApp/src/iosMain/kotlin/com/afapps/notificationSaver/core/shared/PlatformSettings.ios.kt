package com.afapps.notificationSaver.core.shared

import com.afapps.notificationSaver.core.utilities.AppConstant
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 03/10/2024 - 1:39 AM
 * Last Modified: 03/10/2024 - 1:39 AM
 */
actual fun checkNotificationPermission(platformContext: PlatformContext): Boolean {
    // iOS does not have an equivalent to Android's notification listener permission.
    // You can either return a default value or implement a custom check.
    return true
}

actual fun requestNotificationPermission(platformContext: PlatformContext) {
    // iOS does not provide a way to directly open notification settings for a listener service.
}

actual fun setLocale(platformContext: PlatformContext,languageCode: String, preferences: Preferences) {
    // iOS does not allow changing the locale at runtime for the app.
    // Instead, save the preference for use in your app.
    preferences.setValue("LANGUAGE", languageCode)
}

actual fun getSavedLanguage(preferences: Preferences): String {
    return preferences.getStringValue(AppConstant.LANGUAGE, NSLocale.currentLocale.languageCode)

}
