package com.apps.kunalfarmah.composenavigationexample.util

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import com.apps.kunalfarmah.composenavigationexample.routes.BottomTab
import com.apps.kunalfarmah.composenavigationexample.routes.Screens

object Utils {
    fun getTitle(backStackEntry: NavBackStackEntry?): String {
        val destination = backStackEntry?.destination
        // set names based on the KClass routes
        return when {
            destination == null -> "Example"
            destination.hasRoute<Screens.Login>() -> "Login"
            destination.hasRoute<Screens.Register>() -> "Register"
            destination.hasRoute<Screens.Home>() -> "Home"
            destination.hasRoute<Screens.Tabs>() -> "Tabs"
            destination.hasRoute<BottomTab.TabA>() -> "Tab A"
            destination.hasRoute<BottomTab.TabB>() -> "Tab B"
            destination.hasRoute<BottomTab.TabC>() -> "Tab C"
            destination.hasRoute<Screens.Detail>() -> "Details"
            else -> "Example"
        }
    }
}