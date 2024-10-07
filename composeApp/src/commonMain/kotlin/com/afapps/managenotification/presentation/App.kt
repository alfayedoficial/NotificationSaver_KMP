package com.afapps.managenotification.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.afapps.managenotification.presentation.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}