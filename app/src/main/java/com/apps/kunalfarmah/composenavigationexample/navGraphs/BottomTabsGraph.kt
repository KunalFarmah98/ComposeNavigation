package com.apps.kunalfarmah.composenavigationexample.navGraphs
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.apps.kunalfarmah.composenavigationexample.routes.BottomTab
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.screens.TabCScreen
import com.apps.kunalfarmah.composenavigationexample.screens.TopPagerScreen1
import com.apps.kunalfarmah.composenavigationexample.screens.TopPagerScreen2
import com.apps.kunalfarmah.composenavigationexample.viewModel.MainViewModel

fun NavGraphBuilder.BottomTabsGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    composable<BottomTab.TabA> {
        TopPagerScreen1(mainViewModel)
    }
    composable<BottomTab.TabB> {
        TopPagerScreen2(mainViewModel)
    }
    composable<BottomTab.TabC> {
        TabCScreen() {
            navController.navigate(Screens.Detail(it))
        }
    }
}