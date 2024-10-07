package com.afapps.managenotification.core.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val  migration_1_To_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        // Adding new columns to the `sub_notifications` table
        connection.execSQL("ALTER TABLE sub_notifications ADD COLUMN largeIcon BLOB")
        connection.execSQL("ALTER TABLE sub_notifications ADD COLUMN picture BLOB")
        connection.execSQL("ALTER TABLE sub_notifications ADD COLUMN actions TEXT")

        // Create an index on the senderName column
        connection.execSQL("CREATE INDEX index_sub_notifications_senderName ON sub_notifications(senderName)")

    }
}

