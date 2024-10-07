package com.afapps.managenotification.presentation.features.home.state

import androidx.paging.PagingData
import com.afapps.managenotification.domain.model.Notification
import com.afapps.managenotification.domain.model.SubNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val subNotifications: Flow<PagingData<SubNotification>> = emptyFlow(),
    val error: String? = null
)


