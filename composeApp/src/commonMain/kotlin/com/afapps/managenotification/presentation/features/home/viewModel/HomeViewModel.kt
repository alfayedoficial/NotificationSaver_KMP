package com.afapps.managenotification.presentation.features.home.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.afapps.managenotification.core.dataState.DataState
import com.afapps.managenotification.data.entities.SubNotificationEntity
import com.afapps.managenotification.domain.model.SubNotification
import com.afapps.managenotification.domain.repo.NotificationRepo
import com.afapps.managenotification.presentation.features.home.state.HomeIntent
import com.afapps.managenotification.presentation.features.home.state.HomeState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class HomeViewModel(private val notificationRepo: NotificationRepo) : ViewModel() {

    // State exposed to the UI
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()
    private val _currentSenderName = MutableStateFlow<String?>(null)


   init {
       fetchSubNotifications()
   }

    // Intent processing
    fun processIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeIntent.LoadNotifications -> loadNotifications()
                is HomeIntent.DeleteNotification -> deleteNotification(intent.id)
                is HomeIntent.SelectSubNotifications -> selectSubNotifications(intent.senderName?:"")
                is HomeIntent.ReadNotification -> readNotification(intent.id , intent.senderName)

            }
        }
    }

    private fun loadNotifications(ids: List<Long>? = null) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            notificationRepo.getNotificationsAsFlow(ids)
                .catch { e ->
                    _state.value = _state.value.copy(isLoading = false , error = e.message ?: "Unknown error")
                }
                .collect { notifications ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        notifications = notifications
                    )
                }
        }
    }


    private fun selectSubNotifications(senderName: String) {
        _currentSenderName.value = senderName
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchSubNotifications() {
        val subNotificationsFlow: Flow<PagingData<SubNotification>> = _currentSenderName
            .filterNotNull()
            .flatMapLatest { senderName ->
                notificationRepo.getSubNotificationsPaging(senderName)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

        _state.update { it.copy(subNotifications = subNotificationsFlow) }
    }

    private fun deleteNotification(id: Long) {
        viewModelScope.launch {
            notificationRepo.deleteNotification(id)
            fetchSubNotifications()
        }
    }


    private fun readNotification(id: Long ? = null, senderName: String) {
        viewModelScope.launch {
            notificationRepo.readNotification(id, senderName)
            fetchSubNotifications()
        }
    }
}


