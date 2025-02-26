package com.apps.kunalfarmah.composenavigationexample

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun StackNavigator(){
    val navController = rememberNavController()
    val activity = LocalActivity.current
    val uri = "www.kunalfarmah.com"
    val scheme = "composenavigation:/"
    NavHost(
        navController = navController,
        startDestination = Auth
    ){
        //login flow
        AuthGraph(navController)
        // Home and Tabs Flow
        HomeGraph(navController, activity, scheme, uri)
    }
}