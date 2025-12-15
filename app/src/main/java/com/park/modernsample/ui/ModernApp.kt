package com.park.modernsample.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.park.core.design.component.ModernTopAppBar
import com.park.core.design.component.SearchTopAppBar
import com.park.feature.home.navigation.homeScreen
import com.park.feature.search.navigation.searchScreen
import com.park.modernsample.navigation.AppDestinations

@Composable
fun ModernApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    // TODO: ModernAppState 분리 필요
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    var searchKeyWord by rememberSaveable { mutableStateOf("") }
    var submitKeyWord by rememberSaveable { mutableStateOf("pparkjae") }

    BackHandler(enabled = isSearchActive) {
        isSearchActive = false
        searchKeyWord = ""
    }

    ModernApp(
        isSearchActive = isSearchActive,
        searchKeyWord = searchKeyWord,
        submitKeyWord = submitKeyWord,
        onSearchKeyWordChange = {
            searchKeyWord = it
        },
        onSearchActiveChange = {
            isSearchActive = it
        },
        onSearchAction = {
            submitKeyWord = searchKeyWord
        },
        currentDestination = currentDestination,
        snackbarHostState = snackbarHostState,
        navController = navController,
        modifier = modifier
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
)
@Composable
internal fun ModernApp(
    isSearchActive: Boolean,
    searchKeyWord: String,
    submitKeyWord: String,
    onSearchKeyWordChange: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    onSearchAction: () -> Unit,
    currentDestination: NavDestination?,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val currentTopLevelDestination = AppDestinations.entries.find { destination ->
        currentDestination?.hierarchy?.any { it.route == destination.route } == true
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true

                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(destination.contentDescription)
                        )
                    },
                    label = { Text(stringResource(destination.label)) },
                    selected = selected,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        layoutType = layoutType,
        modifier = modifier,
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = {
                ModernAppTopBar(
                    currentTopLevelDestination = currentTopLevelDestination,
                    isSearchActive = isSearchActive,
                    searchKeyWord = searchKeyWord,
                    onSearchKeyWordChange = onSearchKeyWordChange,
                    onSearchActiveChange = onSearchActiveChange,
                    onSearchAction = onSearchAction
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) { padding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = AppDestinations.HOME.route,
                ) {
                    homeScreen(submitKeyWord)
                    searchScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernAppTopBar(
    currentTopLevelDestination: AppDestinations?,
    isSearchActive: Boolean,
    searchKeyWord: String,
    onSearchKeyWordChange: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    onSearchAction: () -> Unit
) {
    AnimatedContent(
        targetState = isSearchActive,
        label = "TopBarAnimation"
    ) { searchMode ->
        if (searchMode) {
            SearchTopAppBar(
                searchKeyWord = searchKeyWord,
                onSearchKeyWordChange = onSearchKeyWordChange,
                onBackClick = {
                    onSearchActiveChange(false)
                    onSearchKeyWordChange("")
                },
                onClearClick = { onSearchKeyWordChange("") },
                onSearchAction = {
                    onSearchAction()
                }
            )
        } else {
            if (currentTopLevelDestination != null) {
                ModernTopAppBar(
                    titleRes = currentTopLevelDestination.label,
                    actionIcon = currentTopLevelDestination.topBarActionIcon,
                    onActionClick = {
                        when (currentTopLevelDestination) {
                            AppDestinations.HOME -> onSearchActiveChange(true)
                            else -> {}
                        }
                    }
                )
            }
        }
    }
}