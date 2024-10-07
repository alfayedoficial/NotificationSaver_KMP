package com.afapps.notificationSaver.core.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 06/10/2024 - 12:56 AM
 * Last Modified: 06/10/2024 - 12:56 AM
 */

val  migration_3_To_4 = object : Migration(3, 4) {
    override fun migrate(connection: SQLiteConnection) {
        // Adding new columns to the `sub_notifications` table
        connection.execSQL("ALTER TABLE sub_notifications ADD COLUMN subText TEXT")
        connection.execSQL("ALTER TABLE sub_notifications ADD COLUMN bigText TEXT")
        connection.execSQL("ALTER TABLE sub_notifications ADD COLUMN summaryText TEXT")
        connection.execSQL("ALTER TABLE sub_notifications ADD COLUMN smallIconId INTEGER")

    }
}
