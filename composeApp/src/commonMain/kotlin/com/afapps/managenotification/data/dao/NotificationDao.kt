package com.afapps.managenotification.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.afapps.managenotification.data.entities.NotificationEntity
import com.afapps.managenotification.data.entities.NotificationWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotification(notification: NotificationEntity): Long

    @Query("SELECT COUNT(*) FROM notifications WHERE senderName = :senderName")
    suspend fun notificationExists(senderName: String): Int

    @Query("SELECT COUNT(*) FROM notifications WHERE icon IS NULL AND senderName = :senderName")
    suspend fun notificationIconNull(senderName: String): Int

    @Transaction
    @Query("""
        SELECT 
            n.id, 
            n.senderName, 
            n.appName, 
            n.icon,
            (SELECT MAX(time) FROM sub_notifications WHERE senderName = n.senderName) AS lastTime,
            (SELECT COUNT(*) FROM sub_notifications WHERE senderName = n.senderName) AS count,
            CASE WHEN EXISTS ( SELECT 1 FROM sub_notifications WHERE senderName = n.senderName AND isRead = 0) THEN 0 ELSE 1 END AS isRead
        FROM notifications n
        WHERE (:ids IS NULL OR n.id IN (:ids))
        ORDER BY lastTime DESC
    """)
    fun getAllNotifications(ids: List<Long>? = null): Flow<List<NotificationWithDetails>>

    @Query("UPDATE notifications SET icon = :icon WHERE senderName = :senderName")
    suspend fun updateNotificationIcon(senderName: String, icon: ByteArray)



}