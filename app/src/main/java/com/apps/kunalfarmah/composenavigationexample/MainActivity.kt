package com.apps.kunalfarmah.composenavigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBar(
                            title = getTitle(backStackEntry.value),
                            navController = navController
                        )
                    }
                )

                { innerPadding ->
                    Surface(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
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
            destination.hasRoute<Login>() -> "Login"
            destination.hasRoute<Register>() -> "Register"
            destination.hasRoute<Home>() -> "Home"
            destination.hasRoute<Tabs>() -> "Tabs"
            destination.hasRoute<Detail>() -> "Details"
            else -> "Example"
        }
    }
}