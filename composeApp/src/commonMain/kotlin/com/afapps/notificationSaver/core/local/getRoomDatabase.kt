package com.afapps.notificationSaver.core.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.afapps.notificationSaver.core.local.migration.migration_1_To_2
import com.afapps.notificationSaver.core.local.migration.migration_2_To_3
import com.afapps.notificationSaver.core.local.migration.migration_3_To_4
import com.afapps.notificationSaver.core.local.migration.migration_4_To_5
import com.afapps.notificationSaver.core.local.trigger.roomCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun getRoomDatabase(
    builder: RoomDatabase.Builder<NotificationDatabase>
): NotificationDatabase {
    return builder
        .addMigrations(
            migration_1_To_2,
            migration_2_To_3,
            migration_3_To_4,
            migration_4_To_5,
        )
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .addCallback(roomCallback)
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}


