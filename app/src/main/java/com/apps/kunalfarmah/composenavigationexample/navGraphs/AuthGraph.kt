package com.apps.kunalfarmah.composenavigationexample.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.screens.LoginScreen
import com.apps.kunalfarmah.composenavigationexample.screens.RegisterScreen

fun NavGraphBuilder.AuthGraph(navController: NavHostController) {
    navigation<Screens.Auth>(startDestination = Screens.Login) {
        // pop the Auth graph and start the new screen a fresh
        val navOptions = navOptions {
            popUpTo<Screens.Auth> { inclusive = true }
            launchSingleTop = true
        }
        composable<Screens.Login> {
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            LoginScreen(
                onLogin = { response ->
                    navController.navigate(
                        Screens.Main(response.token, response.userId),
                        navOptions
                    )
                },
                goToRegister = {
                    navController.navigate(Screens.Register, navOptions)
                }
            )
        }

        composable<Screens.Register> {
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            RegisterScreen(
                onRegister = { response ->
                    navController.navigate(
                        Screens.Main(response.token, response.userId),
                        navOptions
                    )
                },
                goToLogin = {
                    navController.navigate(Screens.Login, navOptions)
                }
            )
        }
    }

}