package com.apps.kunalfarmah.composenavigationexample.navigators

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.apps.kunalfarmah.composenavigationexample.routes.BottomTab
import com.apps.kunalfarmah.composenavigationexample.navGraphs.BottomTabsGraph
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.viewModel.MainViewModel

@Composable
fun BottomNavigator(bottomTabsNavController: NavHostController, rootNavController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(
        navController = bottomTabsNavController,
        route = Screens.Tabs::class,
        startDestination = BottomTab.TabA
    ) {
        BottomTabsGraph(rootNavController, mainViewModel)
    }
}