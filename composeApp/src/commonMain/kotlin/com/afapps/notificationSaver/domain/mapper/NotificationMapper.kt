package com.afapps.notificationSaver.domain.mapper

import com.afapps.notificationSaver.data.entities.NotificationWithDetails
import com.afapps.notificationSaver.domain.model.Notification

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 02/10/2024 - 10:12 PM
 * Last Modified: 02/10/2024 - 10:12 PM
 */


fun NotificationWithDetails.toDomainModel(): Notification {
    return Notification(
        id = id,
        senderName = senderName,
        appName = appName,
        lastTime = lastTime,
        icon = icon,
        count = count,
        isRead = isRead,
    )
}