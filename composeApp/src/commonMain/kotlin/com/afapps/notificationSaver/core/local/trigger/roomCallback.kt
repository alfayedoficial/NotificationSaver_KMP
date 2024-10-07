package com.afapps.notificationSaver.core.local.trigger

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 07/10/2024 - 10:08 AM
 * Last Modified: 07/10/2024 - 10:08 AM
 */

val roomCallback = object : RoomDatabase.Callback() {

    override fun onCreate(connection: SQLiteConnection) {
        connection.execSQL("""
            CREATE TRIGGER IF NOT EXISTS delete_notification_after_sub_notification_deleted
            AFTER DELETE ON sub_notifications
            BEGIN
                DELETE FROM notifications
                WHERE senderName = OLD.senderName
                  AND NOT EXISTS (
                      SELECT 1 FROM sub_notifications WHERE senderName = OLD.senderName
                  );
            END;
        """.trimIndent())
    }


}