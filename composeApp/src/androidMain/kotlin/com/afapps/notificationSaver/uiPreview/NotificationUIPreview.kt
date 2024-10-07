package com.afapps.notificationSaver.uiPreview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.afapps.notificationSaver.domain.model.Notification
import com.afapps.notificationSaver.presentation.features.home.components.NotificationUI

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 05/10/2024 - 12:34 AM
 * Last Modified: 05/10/2024 - 12:34 AM
 */

@Composable
fun NotificationCard(){
    val notification = Notification(
        senderName = "android",
        appName = "Manage Notification",
        lastTime = System.currentTimeMillis(),
        count = 101,
    )
    NotificationUI(
        notification = notification,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun NotificationCardIPreview2() {
    NotificationCard()
}