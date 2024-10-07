package com.afapps.notificationSaver.presentation.features.home.ui

import NotificationCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.afapps.notificationSaver.core.theme.TemplateBackground
import com.afapps.notificationSaver.presentation.features.home.components.NotificationUI
import com.afapps.notificationSaver.presentation.features.home.state.HomeIntent
import com.afapps.notificationSaver.presentation.features.home.viewModel.HomeViewModel
import com.afapps.notificationSaver.presentation.navigation.MenuNav
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 29/09/2024 - 1:15 AM
 * Last Modified: 29/09/2024 - 1:15 AM
 */


@Composable
fun HomeScreen(modifier: Modifier = Modifier ,
               onRoute: (String) -> Unit = {},
               viewModel: HomeViewModel = koinViewModel()) {


    // Call loadNotifications when the screen is first composed
    LaunchedEffect(Unit) {
        viewModel.processIntent(HomeIntent.LoadNotifications)
    }

    Scaffold(
        topBar ={
            TopAppBar(
                title = { Text(stringResource(Res.string.notifications)) },
                actions = {
                    IconButton(onClick = { onRoute(MenuNav.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(Res.string.settings))
                    }
                },
                backgroundColor = TemplateBackground
            )
        }
    ) {
        Box(modifier = modifier.fillMaxSize().padding(bottom = 20.dp), contentAlignment = Alignment.Center) {
            NotificationsListScreen(
                viewModel = viewModel,
                onNotificationClick = { senderName ->
                   onRoute(MenuNav.NotificationsBySender.route(senderName))
                }
            )
        }
    }
}

@Composable
fun BoxScope.NotificationsListScreen(
    viewModel: HomeViewModel,
    onNotificationClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    if (state.isLoading) { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        itemsIndexed(items = state.notifications) { index, notification ->
            NotificationUI(
                notification = notification,
                paddingEnd = if (index == state.notifications.lastIndex) 8.dp else 0.dp,
                onClick = { onNotificationClick(notification.senderName) }
            )
        }
    }

    state.error?.let {
        Text(text = it, color = Color.Red,modifier = Modifier.align(Alignment.Center))
    }
}
