package com.apps.kunalfarmah.composenavigationexample.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps.kunalfarmah.composenavigationexample.components.AppBar
import com.apps.kunalfarmah.composenavigationexample.navigators.AppNavigator
import com.apps.kunalfarmah.composenavigationexample.routes.BottomTab
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.ui.theme.ComposeNavigationExampleTheme
import com.apps.kunalfarmah.composenavigationexample.viewModel.MainViewModel

@Composable
fun SetStatusBarIconsDark(darkIcons: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) { // Do not run in Preview mode
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = darkIcons
            // Optional: Set status bar background color if needed
            // window.statusBarColor = Color.White.toArgb() // Example: For a white status bar
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigationExampleTheme {
                val mainViewModel = viewModel<MainViewModel>()
                var bottomTabTitle by remember { mutableStateOf("Manage") }
                LaunchedEffect(true) {
                    mainViewModel.bottomTabTitle.collect{
                        Log.d("bottomTabTitle",it)
                        bottomTabTitle = it
                    }
                }
                SetStatusBarIconsDark(bottomTabTitle == "Manage")
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = {
                        AppBar(
                            navController = navController,
                            viewModel = mainViewModel
                        )
                    }
                )
                { innerPadding ->
                    Surface(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavigator(navController, mainViewModel)
                    }
                }
            }
        }
    }
}