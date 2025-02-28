package com.apps.kunalfarmah.composenavigationexample.activity

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
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps.kunalfarmah.composenavigationexample.components.AppBar
import com.apps.kunalfarmah.composenavigationexample.navigators.AppNavigator
import com.apps.kunalfarmah.composenavigationexample.routes.BottomTab
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.ui.theme.ComposeNavigationExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigationExampleTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = {
                        AppBar(
                            navController = navController
                        )
                    }
                )
                { innerPadding ->
                    Surface(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavigator(navController)
                    }
                }
            }
        }
    }
}