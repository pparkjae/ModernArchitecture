package com.park.modernsample.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout   .WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.park.core.design.component.ModernTopAppBar
import com.park.feature.home.navigation.Routes
import com.park.feature.home.navigation.homeScreen
import com.park.feature.search.navigation.searchScreen
import com.park.modernsample.navigation.AppDestinations

@Composable
fun ModernApp(
    modifier: Modifier = Modifier,
) {
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val navController = rememberNavController()

    // 현재 라우트 감지
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModernApp(
        currentRoute = currentRoute,
        snackbarHostState = snackbarHostState,
        showSettingsDialog = showSettingsDialog,
        onTopAppBarActionClick = { showSettingsDialog = true },
        onDismissDialog = { showSettingsDialog = false },
        navController = navController
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
)
@Composable
inline fun ModernApp(
    currentRoute: String?,
    snackbarHostState: SnackbarHostState,
    showSettingsDialog: Boolean,
    onTopAppBarActionClick: () -> Unit,
    onDismissDialog: () -> Unit,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
    val layoutType = NavigationSuiteScaffoldDefaults
        .calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val currentDestination = AppDestinations.entries.find { it.route == currentRoute }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = stringResource(destination.contentDescription)
                        )
                    },
                    label = { Text(stringResource(destination.label)) },
                    selected = destination.route == currentRoute,
                    onClick = {
                        if (currentRoute != destination.route) {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        },
        layoutType = layoutType,
    ) {
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing.exclude(
                            WindowInsets.ime,
                        ),
                    ),
                )
            }
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                val test = "test"

                currentDestination?.let {
                    ModernTopAppBar(
                        titleRes = it.label,
                        actionIcon = it.icon,
                        actionIconContentDescription = stringResource(it.contentDescription),
                        onActionClick = {
                            when (it.route) {
                                Routes.HOME -> {

                                }
                            }
                        }
                    )
                }

                Box(Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = AppDestinations.HOME.route,
                    ) {
                        homeScreen()
                        searchScreen()
                    }
                }
            }
        }
    }
}