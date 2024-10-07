package com.afapps.notificationSaver.presentation.features.settings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.afapps.notificationSaver.core.shared.PlatformContext
import com.afapps.notificationSaver.core.shared.openAppRatingPage
import com.afapps.notificationSaver.core.shared.openUrl
import com.afapps.notificationSaver.core.theme.TemplateBackground
import com.afapps.notificationSaver.core.theme.TemplateYellow
import com.afapps.notificationSaver.presentation.features.settings.viewModel.SettingsViewModel
import com.afapps.notificationSaver.presentation.navigation.MenuNav
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.app_name
import kotlinproject.composeapp.generated.resources.developed_by
import kotlinproject.composeapp.generated.resources.ic_nsaver
import kotlinproject.composeapp.generated.resources.language
import kotlinproject.composeapp.generated.resources.notification_access
import kotlinproject.composeapp.generated.resources.privacy_policy
import kotlinproject.composeapp.generated.resources.rate_app
import kotlinproject.composeapp.generated.resources.remove_ads
import kotlinproject.composeapp.generated.resources.settings
import kotlinproject.composeapp.generated.resources.sync_data_google_drive
import kotlinproject.composeapp.generated.resources.version_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onRemoveAdsClicked: () -> Unit,
    onRoute: (String) -> Unit
) {
    val platformContext = koinInject<PlatformContext>()
    val selectedLanguageCode = remember { mutableStateOf(viewModel.getSavedLanguage()) }
    val notificationAccess by viewModel.notificationAccess.collectAsState()
    val adsEnabled by viewModel.adsEnabled.collectAsState()

    LaunchedEffect(Unit){
        viewModel.getNotificationAccess(platformContext)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { onRoute(MenuNav.Home.route) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "BACK"
                        )
                    }
                },
                backgroundColor = TemplateBackground
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(Res.drawable.ic_nsaver),
                contentDescription = stringResource(Res.string.app_name),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            SwitchItem(
                label = stringResource(Res.string.notification_access),
                checked = notificationAccess,
                onCheckedChange = { isChecked ->
                    viewModel.setNotificationAccess(
                        platformContext,
                        isChecked
                    )
                }
            )
            HorizontalDivider()


//            SettingsRow(
//                label = stringResource(Res.string.sync_data_google_drive),
//                onClick = { viewModel.setGoogleDriveSync() }
//            )
//
//            HorizontalDivider()
//
//            if (adsEnabled) {
//                SettingsRow(
//                    label = stringResource(Res.string.remove_ads),
//                    onClick = onRemoveAdsClicked
//                )
//                HorizontalDivider()
//            }

//            LanguageSwitcher(changeLanguage = { languageCode ->
//                viewModel.setLocale(platformContext, languageCode)
//            }, selectedLanguageCode = selectedLanguageCode)
//
//            HorizontalDivider()

            SettingsRow(
                label = stringResource(Res.string.privacy_policy),
                onClick = { onRoute(MenuNav.PrivacyPolicy.route) }
            )
            HorizontalDivider()

            SettingsRow(
                label = stringResource(Res.string.rate_app),
                onClick = { openAppRatingPage(platformContext) }
            )
            HorizontalDivider()

            Spacer(modifier = Modifier.weight(1f))
            Text(text = stringResource(Res.string.version_name))
            TextButton(onClick = {
                // open web browser
                platformContext.openUrl("https://www.linkedin.com/in/alfayedoficial/")
            }) {
                Text(text = stringResource(Res.string.developed_by))
            }

        }
    }
}

@Composable
fun SwitchItem(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange,
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                {
                    Box(
                        modifier = Modifier
                            .size(20.dp) // Adjust the size to match the images
                            .background( Color.Gray, shape = CircleShape)
                    )
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = TemplateYellow,
                uncheckedThumbColor = Color.Transparent,
                checkedBorderColor = TemplateYellow,
                checkedTrackColor = Color(0xFFEFEFEF),
                uncheckedTrackColor = Color(0xFFEFEFEF)
            ))
    }
}

@Composable
fun SettingsRow(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        IconButton(onClick = onClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = label)
        }
    }
}

@Composable
fun LanguageSwitcher(
    changeLanguage: (String) -> Unit,
    selectedLanguageCode: MutableState<String>
) {

    val languages = mapOf(
        "en" to "English",
        "es" to "Spanish",
        "fr" to "French",
        "de" to "German",
        "zh" to "Chinese",
        "ja" to "Japanese",
        "ko" to "Korean",
        "ar" to "Arabic"
    )

    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size
                }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(Res.string.language))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = languages[selectedLanguageCode.value] ?: "Unknown")
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = stringResource(Res.string.language)
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            androidx.compose.material3.DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, with(density) { textFieldSize.height.toDp() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1F1F1F))
            ) {
                languages.forEach { (code, name) ->
                    DropdownMenuItem(onClick = {
                        selectedLanguageCode.value = code
                        expanded = false
                        changeLanguage(code)
                    }) {
                        Text(text = name, color = Color.White)
                    }
                }
            }
        }
    }
}