package com.apps.kunalfarmah.composenavigationexample.components

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.apps.kunalfarmah.composenavigationexample.routes.tabs
import com.apps.kunalfarmah.composenavigationexample.util.Utils.COLOR_HOME
import com.apps.kunalfarmah.composenavigationexample.util.Utils.getTitle
import com.apps.kunalfarmah.composenavigationexample.viewModel.MainViewModel
import kotlinx.coroutines.delay


val TopAppBarExpandedHeight = 100.dp
val TopAppBarCollapsedHeight = 40.dp


val BottomTabBarExpandedHeight = 75.dp
val BottomTabBarCollapsedHeight = 0.dp

@Composable
fun AppBar(navController: NavHostController, viewModel: MainViewModel) {
    val activity = LocalActivity.current
    val backStackEntry = navController.currentBackStackEntryAsState()
    val title = getTitle(backStackEntry.value)
    var collapseTopAppBar by remember{
        mutableStateOf(false)
    }
    var bottomTabTitle by remember {
        mutableStateOf("Home")
    }

    var appBarColor by remember {
        mutableStateOf(COLOR_HOME)
    }

    LaunchedEffect(true) {
        viewModel.topAppBarCollapsedState.collect{
            Log.d("TopAppBarCollapsed",it.toString())
            collapseTopAppBar = it
        }
    }

    LaunchedEffect(true) {
        viewModel.bottomTabTitle.collect{
            Log.d("bottomTabTitle",it.toString())
            bottomTabTitle = it
        }
    }

    LaunchedEffect(true) {
        viewModel.appBarColor.collect{
            Log.d("appBarColor",it.toString())
            appBarColor = it
        }
    }


    val appBarHeight by animateDpAsState(
        targetValue = if (collapseTopAppBar) TopAppBarCollapsedHeight else TopAppBarExpandedHeight,
        animationSpec = tween(durationMillis = 300), // Adjust animation duration as needed
        label = "AppBarHeight"
    )

    // Using a Box to control the animated height and clip content
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(appBarHeight) // Animate the height of the Box
            .clipToBounds()
    ) {
        TopAppBar(
            title = {
                Text(text = if(bottomTabTitle != "") bottomTabTitle else title)
            },
            elevation = 0.dp,
            backgroundColor = appBarColor,
            contentColor = if(appBarColor == COLOR_HOME) Color.Black else Color.White,
            actions = {
                Row(Modifier.padding(end = 15.dp)) {
                    IconButton(
                        onClick = {
                            activity?.finish()
                        }
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }

                    ) {
                        Icon(Icons.Filled.Settings, contentDescription = "Search")
                    }
                }
            },
            navigationIcon = {
                IconButton(
                    modifier = Modifier.padding(start = 15.dp),
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(Icons.Filled.Menu, contentDescription = "Back")
                }
            },
            windowInsets = WindowInsets.statusBars
        )
    }
}

@Composable
fun BottomTabBar(navController: NavHostController, viewModel: MainViewModel) {
    var collapseBottomTabs by remember{
        mutableStateOf(false)
    }

    val animatedHeight by animateDpAsState(
        targetValue = if (collapseBottomTabs) BottomTabBarCollapsedHeight else BottomTabBarExpandedHeight,
        animationSpec = tween(durationMillis = 300), // Adjust animation duration as needed
        label = "AppBarHeight"
    )

    LaunchedEffect(true) {
        viewModel.bottomTabCollapsedState.collect{
            Log.d("BottomTabsBarCollapsed",it.toString())
            collapseBottomTabs = it
        }
    }
    // Using a Box to control the animated height and clip content
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(animatedHeight)
            .clipToBounds()
    ) {
        if (animatedHeight > 0.dp) {
            BottomNavigation(
                windowInsets = WindowInsets.navigationBars,
                backgroundColor = Color(0xFFECEDFF)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                tabs.forEach { item ->
                    BottomNavigationItem(
                        modifier = Modifier.padding(top = 20.dp),
                        icon = {
                            Icon(
                                if(currentDestination?.hierarchy?.any {
                                    it.hasRoute(
                                        item.route::class
                                    )
                                } == true) item.iconSelected else item.iconUnselected,
                                contentDescription = item.name
                            )
                        },
                        label = { Text(item.name) },
                        selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(
                                item.route::class
                            )
                        } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                viewModel.setBottomTabTitle(item.name)
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // re-selecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = Color.Black,
                    )
                }
            }
        }
    }
}