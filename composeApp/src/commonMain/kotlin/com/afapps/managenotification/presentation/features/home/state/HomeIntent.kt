package com.afapps.managenotification.presentation.features.home.state

sealed class HomeIntent {
    data object LoadNotifications : HomeIntent()
    data class DeleteNotification(val id: Long) : HomeIntent()
    data class SelectSubNotifications(val senderName: String?) : HomeIntent()
    data class ReadNotification(val id: Long ? = null, val senderName: String) : HomeIntent()
}
