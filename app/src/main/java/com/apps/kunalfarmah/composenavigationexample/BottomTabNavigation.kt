package com.apps.kunalfarmah.composenavigationexample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomTabNavigation(navController: NavHostController, modifier: Modifier, data: Screens.Tabs) {
    NavHost(
        route = Screens.Tabs::class,
        navController = navController,
        startDestination = BottomTab.TabA,
        modifier = modifier
    ) {
        composable<BottomTab.TabA> {
            TabAScreen() {
                navController.navigate(Screens.Detail(it))
            }
        }
        composable<BottomTab.TabB> {
            TabBScreen() {
                navController.navigate(Screens.Detail(it))
            }
        }
        composable<BottomTab.TabC> {
            TabCScreen() {
                navController.navigate(Screens.Detail(it))
            }
        }
    }
}