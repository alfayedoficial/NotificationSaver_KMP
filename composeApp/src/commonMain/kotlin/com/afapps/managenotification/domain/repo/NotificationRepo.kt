package com.afapps.managenotification.domain.repo

import androidx.paging.PagingData
import com.afapps.managenotification.data.entities.SubNotificationEntity
import com.afapps.managenotification.domain.model.Notification
import com.afapps.managenotification.domain.model.SubNotification
import kotlinx.coroutines.flow.Flow

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 29/05/2024 - 1:03 AM
 * Last Modified: 29/05/2024 - 1:03 AM
 */

interface NotificationRepo {

    suspend fun getNotificationsAsFlow(ids: List<Long>? = null): Flow<List<Notification>>

    suspend fun addNotification(subNotification: SubNotificationEntity)

    fun getSubNotificationsPaging(senderName: String): Flow<PagingData<SubNotification>>

    suspend fun deleteNotification(id: Long)

    suspend fun readNotification(id: Long ? = null, senderName: String)

}