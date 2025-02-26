package com.apps.kunalfarmah.composenavigationexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.serialization.Serializable


sealed class Screens{

    @Serializable
    object Root: Screens()

    @Serializable
    object Auth: Screens()

    @Serializable
    object Login: Screens()

    @Serializable
    object Register: Screens()

    @Serializable
    object Tabs: Screens()

    @Serializable
    data class Home(val token: String? = "abcd", val userId: Long? = 0): Screens()

    @Serializable
    data class Detail(val id: Long? = 0): Screens()
}

@Serializable
data class LoginResponse(val token: String, val userId: Long)


@Composable
fun LoginScreen(onLogin: (data: LoginResponse) -> Unit, goToRegister: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onLogin(LoginResponse(
                    token = "token",
                    userId = 1
                ))
            }
        ) {
            Text("Login")
        }

        Button(
            onClick = {
                goToRegister()
            }
        ) {
            Text("Register")
        }
    }
}

@Composable
fun RegisterScreen(onRegister: (data: LoginResponse) -> Unit, goToLogin: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onRegister(LoginResponse(
                    token = "token",
                    userId = 1
                ))
            }

        ) {
            Text("Register")
        }

        Button(
            onClick = {
                goToLogin()
            }
        ) {
            Text("Login")
        }
    }
}

/**
 * Composable functions can have the same parameter type as their Type param for their route
 * Here HomeScreen is defined with Home as route
 */
@Composable
fun HomeScreen(homeData: Screens.Home, goToTabs: ()-> Unit, onBack: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Home Screen")
        Text(text = "Token: ${homeData.token}")
        Text(text = "UserId: ${homeData.userId}")
        Button(
            onClick = {
                goToTabs()
            }
        ) {
            Text("Go to Tabs")
        }

        Button(
            onClick = {
                onBack()
            }
        ) {
            Text("Go back")
        }
    }
}

/**
 * Composable functions can have destructed params as their Type param for their route
 * Here DetailsScreen is defined with Detail as route but we directly take id from the Detail class
 */
@Composable
fun DetailsScreen(id: Long? = 0, onBack: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Details Screen")
        Text(text = "Id: $id")

        Button(
            onClick = {
                onBack()
            }
        ) {
            Text("Go back")
        }
    }

}

@Composable
fun TabAScreen(onDetails: (Long?) -> Unit){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Button(
            onClick = {onDetails(100)}
        ) {
            Text("Go To Details")
        }
    }
}

@Composable
fun TabBScreen(onDetails: (Long?) -> Unit){


    Button(
        onClick = {onDetails(200)}
    ) {
        Text("Go To Details")
    }
}

@Composable
fun TabCScreen(onDetails: (Long?) -> Unit){


    Button(
        onClick = {onDetails(300)}
    ) {
        Text("Go To Details")
    }
}


@Composable
fun TabsScreen(navController: NavHostController){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                windowInsets = WindowInsets.navigationBars
            ){
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry.value?.destination
                tabs.forEach { route->
                    BottomNavigationItem(
                        icon = { Icon(route.icon, contentDescription = route.name) },
                        label = { Text(route.name) },
                        selected = navController.currentDestination?.hierarchy?.any {
                            it.hasRoute(
                                route.route::class
                            )
                        } == true,
                        onClick = {
                            navController.navigate(route.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = MaterialTheme.colors.secondary,
                    )

                }
            }
        }

    ){ paddingValues ->
        BottomTabNavigation(navController, Modifier.padding(paddingValues))
//        Text(modifier = Modifier.padding(paddingValues), text = "Tabs")
    }
}