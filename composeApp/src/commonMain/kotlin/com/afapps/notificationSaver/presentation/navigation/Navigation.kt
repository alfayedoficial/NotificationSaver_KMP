package com.afapps.notificationSaver.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.afapps.notificationSaver.core.utilities.composableRoute
import com.afapps.notificationSaver.presentation.features.home.ui.HomeScreen
import com.afapps.notificationSaver.presentation.features.notifications.ui.NotificationsScreen
import com.afapps.notificationSaver.presentation.features.settings.ui.SettingsScreen
import com.afapps.notificationSaver.presentation.features.splash.SplashScreen
import com.afapps.notificationSaver.presentation.features.settings.features.privacyPolicyScreen.PrivacyPolicyScreen

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 29/09/2024 - 9:50 AM
 * Last Modified: 29/09/2024 - 9:50 AM
 */


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = MenuNav.Splash.route) {

        composableRoute(MenuNav.Splash.route) {
            SplashScreen{
                navController.navigate(MenuNav.Home.route)
            }
        }

        composableRoute(MenuNav.Home.route) {
            HomeScreen(onRoute = { route ->
                navController.navigate(route)
            })
        }

        // NotificationsBySender getting from params
        composableRoute(MenuNav.NotificationsBySender.route) { backStackEntry ->
            val senderName = backStackEntry.arguments?.getString("senderName")
            NotificationsScreen(senderName = senderName, onBack = { navController.popBackStack() })
        }

        composableRoute(MenuNav.Settings.route) {
            SettingsScreen(
                onRemoveAdsClicked = {
                    // Remove Ads
                },
                onRoute = { route ->
                    navController.navigate(route)
                }
            )
        }


        composableRoute(route = MenuNav.PrivacyPolicy.route, content = {
            PrivacyPolicyScreen(onBackClick = { navController.popBackStack() })
        })
    }

}