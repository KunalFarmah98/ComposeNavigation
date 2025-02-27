package com.apps.kunalfarmah.composenavigationexample

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
        // Home Flow
        MainGraph(navController, activity, scheme, uri)
    }
}