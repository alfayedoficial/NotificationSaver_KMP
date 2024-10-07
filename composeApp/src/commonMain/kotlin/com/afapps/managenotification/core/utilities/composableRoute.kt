package com.afapps.managenotification.core.utilities

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val TRANSITION_ANIMATION_DURATION = 400


fun NavGraphBuilder.composableRoute(
    route: String, arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = route,
        arguments = arguments,
        enterTransition = {
            when (initialState.destination.route) {
                route ->
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                    )

                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                route ->
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                    )

                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                route ->
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                    )

                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                route ->
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                    )

                else -> null
            }
        }

    ) { backStackEntry ->
        content(backStackEntry)
    }

}