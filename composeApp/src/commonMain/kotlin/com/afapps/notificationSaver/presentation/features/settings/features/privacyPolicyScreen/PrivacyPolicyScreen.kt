package com.afapps.notificationSaver.presentation.features.settings.features.privacyPolicyScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import multiplatformWebView.WebViewEngine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onBackClick: () -> Unit) {

    var isLoadingDescription by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text("Privacy Policy") }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .windowInsetsPadding(WindowInsets.ime)
            ) {
                WebViewEngine(
                    url = "https://github.com/alfayedoficial/NotificationSaver_KMP/blob/main/PrivacyPolicy.md",
                    isLoading = { loadDelegate ->
                        isLoadingDescription = loadDelegate
                    },
                    onUrlClicked = { url -> },
                    onCreated = {},
                    onDispose = {}
                )
                if (isLoadingDescription) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                    )
                }
                if (isLoadingDescription) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}
