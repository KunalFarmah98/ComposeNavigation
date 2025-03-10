package com.apps.kunalfarmah.composenavigationexample.navGraphs

import android.app.Activity
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.screens.DetailsScreen
import com.apps.kunalfarmah.composenavigationexample.screens.HomeScreen
import com.apps.kunalfarmah.composenavigationexample.screens.TabsScreen

fun NavGraphBuilder.MainGraph(navController: NavHostController, activity: Activity?, scheme: String){
    navigation<Screens.Main>(
        startDestination = Screens.Home::class
    ) {
        composable<Screens.Home>(
            deepLinks = listOf(
                navDeepLink<Screens.Home>(basePath = "$scheme/homeScreen")
            )
        ) {
            val loginResponse = it.toRoute<Screens.Home>()
            /**
             * Never pass navController to composables, instead expose a callback as an argument
             */
            HomeScreen(
                homeData = loginResponse,
                goToTabs = { navController.navigate(Screens.Tabs) },
                onBack = {
                    navController.popBackStack().let {
                        if (!it) {
                            activity?.finish()
                        }
                    }
                })
        }

        composable<Screens.Tabs> {
            TabsScreen(navController)
        }

        composable<Screens.Detail>(
            deepLinks = listOf(
                navDeepLink<Screens.Detail>(basePath = "$scheme/detailScreen")
            )
        ) {
            val detailData = it.toRoute<Screens.Detail>()
            Log.d(
                "Deeplink",
                navDeepLink<Screens.Detail>(basePath = "$scheme/detailScreen").uriPattern.toString()
            )
            DetailsScreen(id = detailData.id, onBack = { navController.popBackStack() })
        }
    }
}