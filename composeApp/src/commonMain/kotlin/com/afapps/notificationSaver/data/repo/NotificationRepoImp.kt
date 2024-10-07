package com.afapps.notificationSaver.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.afapps.notificationSaver.data.dao.NotificationDao
import com.afapps.notificationSaver.data.dao.SubNotificationDao
import com.afapps.notificationSaver.data.entities.SubNotificationEntity
import com.afapps.notificationSaver.domain.mapper.toDomainModel
import com.afapps.notificationSaver.domain.mapper.toEntity
import com.afapps.notificationSaver.domain.model.Notification
import com.afapps.notificationSaver.domain.model.SubNotification
import com.afapps.notificationSaver.domain.repo.NotificationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 29/05/2024 - 1:03 AM
 * Last Modified: 29/05/2024 - 1:03 AM
 */

class NotificationRepoImp(
    private val notificationDao: NotificationDao,
    private val subNotificationDao: SubNotificationDao
) : NotificationRepo {

    override suspend fun getNotificationsAsFlow(ids: List<Long>?): Flow<List<Notification>> =
        notificationDao.getAllNotifications(ids).map { list -> list.map { it.toDomainModel() } }

    override suspend fun addNotification(subNotification: SubNotificationEntity) {
        if (notificationDao.notificationExists(subNotification.senderName) == 0) {
            notificationDao.insertNotification(subNotification.toEntity())
        } else if (notificationDao.notificationIconNull(subNotification.senderName) > 0 && subNotification.icon != null) {
            notificationDao.updateNotificationIcon(subNotification.senderName, subNotification.icon)
        }
        subNotificationDao.insertSubNotification(subNotification)
    }

    override suspend fun deleteNotification(id: Long) {
        subNotificationDao.deleteNotification(id)
    }

    override suspend fun readNotification(id: Long ?, senderName: String) {
        if (id != null)  {
            subNotificationDao.markSubNotificationAsRead(id)
        }else{
            subNotificationDao.markAllSubNotificationsAsRead(senderName)
        }
    }

    override fun getSubNotificationsPaging(senderName: String): Flow<PagingData<SubNotification>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { NotificationPagingSource(subNotificationDao, senderName) }
        ).flow
    }

    // REASON TO COMMENT THIS FUNCTION IS THAT KMP DOESN'T SUPPORT PAGING 3 UNTIL NOW
//    override fun getSubNotificationsPaging(senderName: String): Flow<PagingData<SubNotification>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
//            pagingSourceFactory = { subNotificationDao.getSubNotificationsPagingSource(senderName) }
//        ).flow.map { pagingData -> pagingData.map { it.toDomainModel() } }
//    }


}