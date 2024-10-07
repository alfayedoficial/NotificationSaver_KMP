package com.afapps.managenotification.presentation.navigation

sealed class MenuNav(val route: String, val icon: Int? = null, val titleResId: Int? = null) {
    data object Splash : MenuNav(route = ScreenType.SPLASH_SCREEN)
    data object Home : MenuNav(route = ScreenType.HOME_SCREEN)
    data object Settings : MenuNav(route = ScreenType.SETTINGS_SCREEN)
    data object PrivacyPolicy : MenuNav(route = ScreenType.PRIVACY_POLICY)
    data object NotificationsBySender : MenuNav(route = "${ScreenType.NOTIFICATIONS_BY_SENDER_SCREEN}/{senderName}") {
        fun route(senderName: String) = "${ScreenType.NOTIFICATIONS_BY_SENDER_SCREEN}/$senderName"
    }

}

object ScreenType {
    const val SPLASH_SCREEN = "SPLASH"
    const val HOME_SCREEN = "HOME"
    const val SETTINGS_SCREEN = "SETTINGS"
    const val PRIVACY_POLICY = "PRIVACY_POLICY"
    const val NOTIFICATIONS_BY_SENDER_SCREEN = "NOTIFICATIONS_BY_SENDER"
}
