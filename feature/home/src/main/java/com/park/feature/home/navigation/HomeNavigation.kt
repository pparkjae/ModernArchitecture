package com.park.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.park.feature.home.HomeRoute

object Routes {
    const val HOME = "home"
}

fun NavGraphBuilder.homeScreen(
    submitKeyWord: String,
) {
    composable(Routes.HOME) {
        HomeRoute(submitKeyWord)
    }
}
