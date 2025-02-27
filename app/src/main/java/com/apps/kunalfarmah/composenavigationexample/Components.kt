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
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBar(title: String, navController: NavHostController){
    val activity = LocalActivity.current
    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        navigationIcon = {
            when(title) {
                "Login", "Register", "Home" -> IconButton(
                    onClick = {navController.popBackStack().let{
                        if(!it){
                            activity?.finish()
                        }
                    } }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close App")
                }
                else -> {
                    IconButton(
                        onClick = {navController.navigateUp()}
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        },
        windowInsets =  WindowInsets.statusBars
    )
}

@Composable
fun BottomTabBar(navController: NavHostController, backStackEntry: NavBackStackEntry?){

    val destination = backStackEntry?.destination
    when {
        destination?.hasRoute<BottomTab.TabA>() == true || destination?.hasRoute<BottomTab.TabB>() == true || destination?.hasRoute<BottomTab.TabC>() == true -> {

            BottomNavigation(
                windowInsets = WindowInsets.navigationBars
            ) {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry.value?.destination
                tabs.forEach { route ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                route.icon,
                                contentDescription = route.name
                            )
                        },
                        label = { androidx.compose.material3.Text(route.name) },
                        selected = navController.currentDestination?.hierarchy?.any {
                            it.hasRoute(
                                route.route::class
                            )
                        } == true,
                        onClick = {
                            navController.navigate(route.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = MaterialTheme.colors.secondary,
                    )

                }
            }
        }
        else -> null
    }
}