package com.apps.kunalfarmah.composenavigationexample

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavigator(navController: NavHostController){
    val activity = LocalActivity.current
    val uri = "www.kunalfarmah.com"
    val scheme = "composenavigation:/"
    NavHost(
        route = Screens.Root::class,
        navController = navController,
        startDestination = Screens.Auth
    ){
        //login flow
        AuthGraph(navController)
        // Home and Tabs Flow
        HomeGraph(navController, activity, scheme, uri)

    }
}