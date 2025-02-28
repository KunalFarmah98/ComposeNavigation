package com.apps.kunalfarmah.composenavigationexample

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
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            LoginScreen(onLogin = { response -> navController.navigate(Home(response.token, response.userId), navOptions)})
        }

        composable<Home>(
            deepLinks = listOf(
                navDeepLink<Home>(basePath = "$scheme/homeScreen")
            )
        ) {
            val loginResponse = it.toRoute<Home>()
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
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
                navDeepLink<Detail>(basePath = "$scheme/detailScreen")
            )
        ) {
            val detailData = it.toRoute<Detail>()
            DetailsScreen(id = detailData.id, onBack = {navController.popBackStack()})
        }
    }
}