package com.park.feature.home.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.park.feature.home.HomeRoute
import kotlinx.serialization.Serializable

object Routes {
    const val HOME = "home"
}

fun NavGraphBuilder.homeScreen(

) {
    composable(Routes.HOME) {
        HomeRoute()
    }
}
