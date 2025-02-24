package com.apps.kunalfarmah.composenavigationexample

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import androidx.navigation.toRoute

@Composable
fun StackNavigator(){
    val navController = rememberNavController()
    val activity = LocalActivity.current
    val uri = "www.kunalfarmah.com"
    val scheme = "composenavigation:/"
    NavHost(
        navController = navController,
        startDestination = Login
    ){
        composable<Login> {
            val navOptions = navOptions {
                popUpTo(Login) {
                    inclusive = true
                }
            }
            LoginScreen(onLogin = { response -> navController.navigate(Home(response.token, response.userId), navOptions)})
        }

        composable<Home>(
            deepLinks = listOf(
                navDeepLink<Home>(basePath = "$scheme/homeScreen"),
                navDeepLink<Home>(basePath = "$uri/homeScreen")
            )
        ) {
            val loginResponse = it.toRoute<Home>()

            HomeScreen(homeData = loginResponse, onDetail = { id -> navController.navigate(Detail(id))}, onBack = {
                navController.popBackStack().let {
                    if(!it){
                        activity?.finish()
                    }
                }
            })
        }

        composable<Detail>(
            deepLinks = listOf(
                navDeepLink<Detail>(basePath = "$scheme/detailScreen"),
                navDeepLink<Detail>(basePath = "$uri/detailScreen")
            )
        ) {
            val id = it.toRoute<Detail>().id
            Log.d("Deeplink",
                navDeepLink<Detail>(basePath = "$scheme/detailScreen").uriPattern.toString()
            )
            DetailsScreen(id = id, onBack = {navController.popBackStack()})
        }
    }
}