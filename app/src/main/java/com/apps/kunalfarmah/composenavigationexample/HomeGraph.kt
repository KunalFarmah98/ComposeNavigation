package com.apps.kunalfarmah.composenavigationexample

import android.app.Activity
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute

fun NavGraphBuilder.HomeGraph(navController: NavHostController, activity: Activity?, scheme: String, uri: String){
    composable<Screens.Home>(
        deepLinks = listOf(
            navDeepLink<Screens.Home>(basePath = "$scheme/homeScreen"),
            navDeepLink<Screens.Home>(basePath = "$uri/homeScreen")
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
        TabsScreen(navController = navController)
    }

    composable<Screens.Detail>(
        deepLinks = listOf(
            navDeepLink<Screens.Detail>(basePath = "$scheme/detailScreen"),
            navDeepLink<Screens.Detail>(basePath = "$uri/detailScreen")
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