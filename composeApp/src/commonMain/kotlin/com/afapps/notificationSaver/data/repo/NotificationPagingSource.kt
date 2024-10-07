package com.afapps.notificationSaver.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.afapps.notificationSaver.data.dao.SubNotificationDao
import com.afapps.notificationSaver.domain.mapper.toDomainModel
import com.afapps.notificationSaver.domain.model.SubNotification
import kotlinx.coroutines.delay

class NotificationPagingSource(
    private val subNotificationDao: SubNotificationDao,
    private val senderName: String
) : PagingSource<Int, SubNotification>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SubNotification> {
        // Add a delay of 500 milliseconds
        delay(500)

        return try {

            // Determine the starting position (offset)
            val offset = params.key ?: 0
            // Load the data
            val loadSize = params.loadSize

            val notifications = subNotificationDao.getSubNotificationsBySenderName(
                senderName = senderName,
                offset = offset,
                loadSize = loadSize
            )

            // Determine the next and previous keys for pagination
            val nextKey = if (notifications.isEmpty()) null else offset + loadSize
            val prevKey = if (offset == 0) null else offset - loadSize

            LoadResult.Page(
                data = notifications.map { it.toDomainModel() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SubNotification>): Int? {
        // Return the anchor position for refreshing
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }
}
