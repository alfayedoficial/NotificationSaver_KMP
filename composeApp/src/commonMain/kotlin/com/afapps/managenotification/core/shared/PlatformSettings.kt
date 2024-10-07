package com.afapps.managenotification.core.shared

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 03/10/2024 - 1:39 AM
 * Last Modified: 03/10/2024 - 1:39 AM
 */


expect fun checkNotificationPermission(platformContext: PlatformContext): Boolean
expect fun requestNotificationPermission(platformContext: PlatformContext)
expect fun setLocale(platformContext: PlatformContext,languageCode: String, preferences: Preferences)
expect fun getSavedLanguage(preferences: Preferences): String