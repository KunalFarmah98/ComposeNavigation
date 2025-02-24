package com.apps.kunalfarmah.composenavigationexample

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute

@Composable
fun StackNavigator(){
    val navController = rememberNavController()
    val activity = LocalActivity.current
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

        composable<Home> {
            val loginResponse = it.toRoute<Home>()

            HomeScreen(homeData = loginResponse, onDetail = { id -> navController.navigate(Detail(id))}, onBack = {
                navController.popBackStack().let {
                    if(!it){
                        activity?.finish()
                    }
                }
            })
        }

        composable<Detail> {
            val id = it.toRoute<Detail>().id
            DetailsScreen(id = id, onBack = {navController.popBackStack()})
        }
    }
}