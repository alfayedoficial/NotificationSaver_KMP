package com.afapps.managenotification.domain.mapper

import com.afapps.managenotification.data.entities.NotificationEntity
import com.afapps.managenotification.data.entities.SubNotificationEntity
import com.afapps.managenotification.domain.model.SubNotification


fun SubNotificationEntity.toDomainModel(): SubNotification {
    return SubNotification(
        id = id,
        senderName = senderName,
        appName = appName,
        title = title,
        content = content,
        time = time,
        icon = icon,
        extras = extras,
        largeIcon = largeIcon,
        picture = picture,
        actions = actions,
        smallIconId = smallIconId,
        subText = subText,
        bigText = bigText,
        summaryText = summaryText,
        isRead = isRead
    )
}

fun SubNotificationEntity.toEntity(): NotificationEntity {
    return NotificationEntity(
        id = id,
        senderName = senderName,
        appName = appName,
        icon = icon
    )
}