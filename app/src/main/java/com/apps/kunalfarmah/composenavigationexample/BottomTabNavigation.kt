package com.apps.kunalfarmah.composenavigationexample
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.BottomNavigator(navController: NavHostController) {
    navigation<Screens.Tabs>(
        startDestination = BottomTab.TabA,
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