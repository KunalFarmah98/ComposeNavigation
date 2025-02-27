package com.apps.kunalfarmah.composenavigationexample

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.AuthGraph(navController: NavHostController) {
    navigation<Screens.Auth>(startDestination = Screens.Login) {
        composable<Screens.Login> {
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            LoginScreen(
                onLogin = { response ->
                    navController.navigate(Screens.Main(response.token, response.userId)) {
                        popUpTo(Screens.Auth) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                goToRegister = { navController.navigate(Screens.Register){
                    popUpTo(Screens.Auth) { inclusive = true }
                    launchSingleTop = true
                } }
            )
        }

        composable<Screens.Register> {
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            RegisterScreen(
                onRegister = { response ->
                    navController.navigate(Screens.Main(response.token, response.userId)) {
                        popUpTo(Screens.Auth) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                goToLogin = { navController.navigate(Screens.Login){
                    popUpTo(Screens.Auth) { inclusive = true }
                    launchSingleTop = true
                } }
            )
        }
    }

}