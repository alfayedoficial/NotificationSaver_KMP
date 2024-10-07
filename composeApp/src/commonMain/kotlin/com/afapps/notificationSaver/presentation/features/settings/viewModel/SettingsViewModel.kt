package com.afapps.notificationSaver.presentation.features.settings.viewModel

import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import com.afapps.notificationSaver.core.shared.PlatformContext
import com.afapps.notificationSaver.core.shared.Preferences
import com.afapps.notificationSaver.core.shared.checkNotificationPermission
import com.afapps.notificationSaver.core.shared.getSavedLanguage
import com.afapps.notificationSaver.core.shared.requestNotificationPermission
import com.afapps.notificationSaver.core.shared.setLocale
import com.afapps.notificationSaver.core.utilities.AppConstant.GOOGLE_DRIVE_SYNC
import com.afapps.notificationSaver.core.utilities.AppConstant.NOTIFICATION_ACCESS
import com.afapps.notificationSaver.core.utilities.AppConstant.ADS_ENABLED
import com.afapps.notificationSaver.core.utilities.AppConstant.LANGUAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SettingsViewModel(
    private val sharedPreferences: Preferences,
) : ViewModel() {

    private val _notificationAccess = MutableStateFlow(false)
    val notificationAccess: StateFlow<Boolean> = _notificationAccess


    private val _adsEnabled = MutableStateFlow(getAdsEnabled())
    val adsEnabled: StateFlow<Boolean> = _adsEnabled


    fun setNotificationAccess(platformContext: PlatformContext, access: Boolean) {
        _notificationAccess.value = access
        requestNotificationPermission(platformContext)

    }

    fun setGoogleDriveSync() {

    }

    fun disableAds() {
        _adsEnabled.value = false
        sharedPreferences.setValue(ADS_ENABLED, false)
    }


    fun setLocale(platformContext: PlatformContext,languageCode: String) {
        setLocale(platformContext, languageCode, sharedPreferences)
    }

    fun getSavedLanguage(): String {
        return getSavedLanguage(sharedPreferences)
    }

    fun getNotificationAccess(platformContext: PlatformContext): Boolean {
        val isNotificationAccess = checkNotificationPermission(platformContext)
        _notificationAccess.value = isNotificationAccess
        return isNotificationAccess
    }


    private fun getAdsEnabled(): Boolean {
        return sharedPreferences.getBooleanValue(ADS_ENABLED, true)
    }
}
