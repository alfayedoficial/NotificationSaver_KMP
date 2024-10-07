package com.afapps.notificationSaver

import androidx.compose.ui.window.ComposeUIViewController
import com.afapps.notificationSaver.core.di.initializeKoin
import com.afapps.notificationSaver.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }