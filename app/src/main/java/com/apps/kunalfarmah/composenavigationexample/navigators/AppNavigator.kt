package com.apps.kunalfarmah.composenavigationexample.navigators

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.apps.kunalfarmah.composenavigationexample.navGraphs.AuthGraph
import com.apps.kunalfarmah.composenavigationexample.navGraphs.MainGraph
import com.apps.kunalfarmah.composenavigationexample.routes.Screens

@Composable
fun AppNavigator(navController: NavHostController){
    val activity = LocalActivity.current
    val uri = "www.kunalfarmah.com"
    val scheme = "composenavigation:/"
    NavHost(
        route = Screens.Root::class,
        navController = navController,
        startDestination = Screens.Auth
    ) {
        // Auth flow
        AuthGraph(navController)
        // Main Flow
        MainGraph(navController, activity, scheme, uri)
    }
}