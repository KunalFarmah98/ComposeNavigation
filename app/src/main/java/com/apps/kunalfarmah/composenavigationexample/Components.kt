package com.apps.kunalfarmah.composenavigationexample

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController

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
fun BottomTabBar(navController: NavHostController, backStackEntry: NavBackStackEntry?, selectedTab: BottomTab = BottomTab.TabA,
                 onChangeTab: (BottomTab) -> Unit = {}){

    val destination = backStackEntry?.destination
    when {
        destination?.hasRoute<BottomTab.TabA>() == true || destination?.hasRoute<BottomTab.TabB>() == true || destination?.hasRoute<BottomTab.TabC>() == true -> {

            BottomNavigation(
                windowInsets = WindowInsets.navigationBars
            ) {
                // Handle Back Press and navigate to TabA
                BackHandler{
                    if(selectedTab != BottomTab.TabA) {
                        onChangeTab(BottomTab.TabA)
                        navController.navigate(BottomTab.TabA) {
                            // Clear the back stack of other tabs
                            popUpTo(BottomTab.TabA) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                    else{
                        navController.navigateUp()
                    }
                }

                tabs.forEach { route ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                route.icon,
                                contentDescription = route.name
                            )
                        },
                        label = { Text(route.name) },
                        selected = selectedTab == route.route,
                        onClick = {
                            onChangeTab(route.route)
                            navController.navigate(route.route) {
                                popUpTo(BottomTab.TabA)
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Gray,
                    )

                }
            }
        }
        else -> null
    }
}