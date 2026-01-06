package com.park.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.park.feature.search.SearchRoute

object Routes {
    const val SEARCH = "search"
}

fun NavGraphBuilder.searchScreen(

) {
    composable(Routes.SEARCH) {
        SearchRoute()
    }
}
