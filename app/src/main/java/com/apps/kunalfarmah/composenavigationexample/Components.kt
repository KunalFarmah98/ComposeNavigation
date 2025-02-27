package com.apps.kunalfarmah.composenavigationexample

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBar(title: String, navController: NavHostController) {
    val activity = LocalActivity.current
    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        navigationIcon = {
            when (title) {
                "Login", "Register", "Home" -> IconButton(
                    onClick = {
                        navController.popBackStack().let {
                            if (!it) {
                                activity?.finish()
                            }
                        }
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close App")
                }

                else -> {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        },
        windowInsets = WindowInsets.statusBars
    )
}

@Composable
fun BottomTabBar(navController: NavHostController) {
    BottomNavigation(
        windowInsets = WindowInsets.navigationBars
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        tabs.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.name
                    )
                },
                label = { Text(item.name) },
                selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(
                                item.route::class
                            )
                        } == true,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
            )
        }
    }
}