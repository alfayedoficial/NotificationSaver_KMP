package com.afapps.managenotification

import androidx.compose.ui.window.ComposeUIViewController
import com.afapps.managenotification.core.di.initializeKoin
import com.afapps.managenotification.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }