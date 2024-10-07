package com.afapps.notificationSaver.presentation.features.notifications.ui

import NotificationCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import com.afapps.notificationSaver.core.theme.TemplateBackground
import com.afapps.notificationSaver.core.utilities.items
import com.afapps.notificationSaver.core.utilities.itemsIndexed
import com.afapps.notificationSaver.presentation.features.home.state.HomeIntent
import com.afapps.notificationSaver.presentation.features.home.state.HomeState
import com.afapps.notificationSaver.presentation.features.home.viewModel.HomeViewModel
import kotlinproject.composeapp.generated.resources.*
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 29/09/2024 - 1:15 AM
 * Last Modified: 29/09/2024 - 1:15 AM
 */


@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    senderName: String? = null,
    onBack: () -> Unit = {},
    viewModel: HomeViewModel = koinViewModel()
) {


    // Call loadNotifications when the screen is first composed
    LaunchedEffect(Unit) {
        viewModel.processIntent(HomeIntent.SelectSubNotifications(senderName))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.notifications)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "BACK")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.processIntent(HomeIntent.ReadNotification(senderName = senderName?:""))
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(Res.string.settings))
                    }
                },
                backgroundColor = TemplateBackground
            )
        }
    ) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            NotificationsListScreen(
                homeStateFlow = viewModel.state,
                onDeleteClick = { id ->
                    viewModel.processIntent(HomeIntent.DeleteNotification(id ))
                },
                onReadClick = { id ->
                    viewModel.processIntent(HomeIntent.ReadNotification(id = id, senderName = senderName ?:""))
                }
            )
        }
    }
}

@Composable
fun BoxScope.NotificationsListScreen(
    homeStateFlow: StateFlow<HomeState>,
    onReadClick: (id: Long) -> Unit = {},
    onDeleteClick: (id: Long) -> Unit = {},
) {
    val state by homeStateFlow.collectAsState()

    // Collect PagingData as LazyPagingItems
    val subNotifications = state.subNotifications.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {


        itemsIndexed(
            items = subNotifications,
            key = { index, notification -> notification?.id ?: index }
        ) { index, notification ->
            notification?.let { item ->
                NotificationCard(
                    item,
                    paddingEnd = if (index == subNotifications.itemCount - 1) 8.dp else 0.dp,
                    onDeleteClick = onDeleteClick,
                    onReadClick = onReadClick
                )
            }
        }


//        items(
//            items = subNotifications,
//            key = { notification -> notification.id }
//        ) { notification ->
//            notification?.let { item ->
//                NotificationCard(
//                    item,
//                    onDeleteClick = onDeleteClick,
//                    onReadClick = onReadClick
//                )
//            }
//        }

        subNotifications.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) }
                }

                is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        Text(
                            text = "Error: ${error.error.message}",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                is LoadState.NotLoading -> {}
            }
        }
    }
}

