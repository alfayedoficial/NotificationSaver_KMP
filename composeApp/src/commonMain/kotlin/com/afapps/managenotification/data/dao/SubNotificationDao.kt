package com.afapps.managenotification.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afapps.managenotification.data.entities.SubNotificationEntity

@Dao
interface SubNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubNotification(subNotification: SubNotificationEntity)

    @Query("SELECT * FROM sub_notifications WHERE senderName = :senderName ORDER BY time DESC LIMIT :loadSize OFFSET :offset")
    suspend fun getSubNotificationsBySenderName(senderName: String , offset: Int, loadSize: Int): List<SubNotificationEntity>


    // REASON TO COMMENT THIS FUNCTION IS THAT KMP DOESN'T SUPPORT PAGING 3 UNTIL NOW
    //@Query("SELECT * FROM sub_notifications WHERE senderName = :senderName ORDER BY time DESC")
    //fun getSubNotificationsPagingSource(senderName: String): PagingSource<Int, SubNotificationEntity>

    @Query("DELETE FROM sub_notifications WHERE id = :id")
    suspend fun deleteNotification(id: Long)

    @Query("UPDATE sub_notifications SET isRead = 1 WHERE id = :id")
    suspend fun markSubNotificationAsRead(id: Long)

    @Query("UPDATE sub_notifications SET isRead = 1 WHERE senderName = :senderName")
    suspend fun markAllSubNotificationsAsRead(senderName: String)

}
