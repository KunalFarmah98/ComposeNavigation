package com.apps.kunalfarmah.composenavigationexample

import android.app.Activity
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute

fun NavGraphBuilder.HomeGraph(navController: NavHostController, activity: Activity?, scheme: String, uri: String){
    composable<Home>(
        deepLinks = listOf(
            navDeepLink<Home>(basePath = "$scheme/homeScreen"),
            navDeepLink<Home>(basePath = "$uri/homeScreen")
        )
    ) {
        val loginResponse = it.toRoute<Home>()
        /**
         * Never pass navController to composables, instead expose a callback as an argument
         */
        HomeScreen(
            homeData = loginResponse,
            onDetail = { id -> navController.navigate(Detail(id)) },
            onBack = {
                navController.popBackStack().let {
                    if (!it) {
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
        val detailData = it.toRoute<Detail>()
        Log.d(
            "Deeplink",
            navDeepLink<Detail>(basePath = "$scheme/detailScreen").uriPattern.toString()
        )
        DetailsScreen(id = detailData.id, onBack = { navController.popBackStack() })
    }
}