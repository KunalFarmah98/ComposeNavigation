package com.apps.kunalfarmah.composenavigationexample

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation

fun NavGraphBuilder.AuthGraph(navController: NavHostController){
    navigation<Screens.Auth>(startDestination = Screens.Login) {
        composable<Screens.Login> {
            val navOptions = navOptions {
                popUpTo(0)
            }
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            LoginScreen(
                onLogin = { response -> navController.navigate(Screens.Home(response.token, response.userId), navOptions)},
                goToRegister = { navController.navigate(Screens.Register, navOptions) }
            )
        }

        composable<Screens.Register> {
            val navOptions = navOptions {
                popUpTo(0)
            }
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            RegisterScreen(
                onRegister = { response -> navController.navigate(Screens.Home(response.token, response.userId), navOptions)},
                goToLogin = { navController.navigate(Screens.Login, navOptions) }
            )
        }
    }

}