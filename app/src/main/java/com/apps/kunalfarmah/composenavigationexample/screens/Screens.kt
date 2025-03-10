package com.apps.kunalfarmah.composenavigationexample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.apps.kunalfarmah.composenavigationexample.components.BottomTabBar
import com.apps.kunalfarmah.composenavigationexample.navigators.BottomNavigator
import com.apps.kunalfarmah.composenavigationexample.routes.LoginResponse
import com.apps.kunalfarmah.composenavigationexample.routes.Screens

@Composable
fun LoginScreen(onLogin: (data: LoginResponse) -> Unit, goToRegister: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onLogin(
                    LoginResponse(
                        token = "token", userId = 1
                    )
                )
            }) {
            Text("Login")
        }

        Button(
            onClick = {
                goToRegister()
            }) {
            Text("Register")
        }
    }
}

@Composable
fun RegisterScreen(onRegister: (data: LoginResponse) -> Unit, goToLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onRegister(
                    LoginResponse(
                        token = "token", userId = 1
                    )
                )
            }

        ) {
            Text("Register")
        }

        Button(
            onClick = {
                goToLogin()
            }) {
            Text("Login")
        }
    }
}

/**
 * Composable functions can have the same parameter type as their Type param for their route
 * Here HomeScreen is defined with Home as route
 */
@Composable
fun HomeScreen(homeData: Screens.Home, goToTabs: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        Text(text = "Token: ${homeData.token}")
        Text(text = "UserId: ${homeData.userId}")
        Button(
            onClick = {
                goToTabs()
            }) {
            Text("Go to Tabs")
        }

        Button(
            onClick = {
                onBack()
            }) {
            Text("Go back")
        }
    }
}

/**
 * Composable functions can have destructed params as their Type param for their route
 * Here DetailsScreen is defined with Detail as route but we directly take id from the Detail class
 */
@Composable
fun DetailsScreen(id: Long? = 0, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Details Screen")
        Text(text = "Id: $id")

        Button(
            onClick = {
                onBack()
            }) {
            Text("Go back")
        }
    }

}

@Composable
fun TabAScreen(onDetails: (Long?) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tab A")
        Button(
            onClick = { onDetails(100) }
        ) {
            Text("Go To Details")
        }
    }
}

@Composable
fun TabBScreen(onDetails: (Long?) -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tab B")
        Button(
            onClick = { onDetails(200) }) {
            Text("Go To Details")
        }
    }
}

@Composable
fun TabCScreen(onDetails: (Long?) -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tab C")
        Button(
            onClick = { onDetails(300) }) {
            Text("Go To Details")
        }
    }
}


@Composable
fun TabsScreen(navController: NavHostController) {
    val bottomTabsNavController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomTabBar(bottomTabsNavController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
           BottomNavigator(bottomTabsNavController, navController)
        }
    }
}