package com.apps.kunalfarmah.composenavigationexample

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation

fun NavGraphBuilder.AuthGraph(navController: NavHostController){
    navigation<Auth>(startDestination = Login) {
        composable<Login> {
            val navOptions = navOptions {
                popUpTo(Login) {
                    inclusive = true
                }
            }
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            LoginScreen(
                onLogin = { response -> navController.navigate(Home(response.token, response.userId), navOptions)},
                goToRegister = { navController.navigate(Register, navOptions) }
            )
        }

        composable<Register> {
            val navOptions = navOptions {
                popUpTo(Register) {
                    inclusive = true
                }
            }
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            RegisterScreen(
                onRegister = { response -> navController.navigate(Home(response.token, response.userId), navOptions)},
                goToLogin = { navController.navigate(Login, navOptions) }
            )
        }
    }

}