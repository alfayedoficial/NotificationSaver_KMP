package com.afapps.notificationSaver.core.shared

import android.content.Intent
import android.provider.Settings
import androidx.activity.ComponentActivity
import com.afapps.notificationSaver.core.utilities.AppConstant
import java.util.Locale

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 03/10/2024 - 1:39 AM
 * Last Modified: 03/10/2024 - 1:39 AM
 */
actual fun checkNotificationPermission(platformContext: PlatformContext): Boolean {
    val context = platformContext.context
    val enabledListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
    val packageName = context.packageName
    return enabledListeners != null && enabledListeners.contains(packageName)

}

actual fun requestNotificationPermission(platformContext: PlatformContext) {
    val context = platformContext.context
    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

actual fun setLocale(platformContext: PlatformContext,languageCode: String, preferences: Preferences) {
    val context = platformContext.context
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    // Save the selected language in shared preferences
    preferences.setValue(AppConstant.LANGUAGE, languageCode)

    // Recreate activity to apply changes if applicable
    (context as? ComponentActivity)?.recreate()
}

actual fun getSavedLanguage(preferences: Preferences): String {
    return preferences.getStringValue(AppConstant.LANGUAGE, Locale.getDefault().language) ?: "en"
}

