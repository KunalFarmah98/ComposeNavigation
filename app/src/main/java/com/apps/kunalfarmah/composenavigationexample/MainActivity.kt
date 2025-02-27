package com.apps.kunalfarmah.composenavigationexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps.kunalfarmah.composenavigationexample.ui.theme.ComposeNavigationExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigationExampleTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                var selectedTab = remember {
                    mutableStateOf<BottomTab>(BottomTab.TabA)
                }
                LaunchedEffect(backStackEntry) {
                    Log.d("Navigation", backStackEntry.value.toString())
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBar(
                            title = getTitle(backStackEntry.value),
                            navController = navController
                        )
                    },
                    bottomBar = {
                        BottomTabBar(navController, backStackEntry.value, selectedTab.value, onChangeTab = {
                            selectedTab.value = it
                        })
                    }
                )
                { innerPadding ->
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        AppNavigator(navController)
                    }
                }
            }
        }
    }

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