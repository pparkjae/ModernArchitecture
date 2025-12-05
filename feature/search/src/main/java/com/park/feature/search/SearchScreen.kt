package com.park.feature.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    SearchScreen(
        modifier
    )
}

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Search!",
        modifier = modifier
    )
}