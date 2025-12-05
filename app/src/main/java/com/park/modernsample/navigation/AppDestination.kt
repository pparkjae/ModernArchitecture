package com.park.modernsample.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.park.feature.home.R as HomeR
import com.park.feature.search.R as SearchR
import com.park.feature.home.navigation.Routes as HomeRoutes
import com.park.feature.search.navigation.Routes as SearchRoutes

enum class AppDestinations(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int,
) {
    HOME(
        route = HomeRoutes.HOME,
        label = HomeR.string.feature_home_title,
        icon = Icons.Rounded.Home,
        contentDescription = HomeR.string.feature_home_title
    ),
    SEARCH(
        route = SearchRoutes.SEARCH,
        label = SearchR.string.feature_search_title,
        icon = Icons.Rounded.Search,
        contentDescription = SearchR.string.feature_search_title
    ),
}