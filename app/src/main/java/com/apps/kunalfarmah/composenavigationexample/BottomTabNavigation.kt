package com.apps.kunalfarmah.composenavigationexample
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.BottomNavigator(navController: NavHostController) {
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