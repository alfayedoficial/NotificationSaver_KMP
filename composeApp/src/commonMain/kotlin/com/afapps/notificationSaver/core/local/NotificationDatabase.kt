package com.afapps.notificationSaver.core.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.afapps.notificationSaver.data.dao.NotificationDao
import com.afapps.notificationSaver.data.dao.SubNotificationDao
import com.afapps.notificationSaver.data.entities.NotificationEntity
import com.afapps.notificationSaver.data.entities.SubNotificationEntity

@Database(
    entities = [
        NotificationEntity::class,
        SubNotificationEntity::class
    ],
    version = 5,
    exportSchema = true
)
@ConstructedBy(NotificationConstructor::class)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
    abstract fun subNotificationDao(): SubNotificationDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NotificationConstructor : RoomDatabaseConstructor<NotificationDatabase> {
    override fun initialize(): NotificationDatabase
}

