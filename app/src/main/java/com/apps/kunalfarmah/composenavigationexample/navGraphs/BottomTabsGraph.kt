package com.apps.kunalfarmah.composenavigationexample.navGraphs
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.apps.kunalfarmah.composenavigationexample.routes.BottomTab
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.screens.TabAScreen
import com.apps.kunalfarmah.composenavigationexample.screens.TabBScreen
import com.apps.kunalfarmah.composenavigationexample.screens.TabCScreen
import com.apps.kunalfarmah.composenavigationexample.screens.TopPagerScreen
import com.apps.kunalfarmah.composenavigationexample.viewModel.MainViewModel

fun NavGraphBuilder.BottomTabsGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    composable<BottomTab.TabA> {
        TopPagerScreen(mainViewModel)
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