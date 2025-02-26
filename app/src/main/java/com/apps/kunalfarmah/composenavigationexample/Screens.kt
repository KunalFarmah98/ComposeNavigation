package com.apps.kunalfarmah.composenavigationexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object Auth

@Serializable
object Login

@Serializable
object Register

@Serializable
data class Tabs(val token: String? = "tabs", val userId: Long? = 20)

@Serializable
data class LoginResponse(val token: String, val userId: Long)

@Serializable
data class Home(val token: String? = "abcd", val userId: Long? = 0)

@Serializable
data class Detail(val id: Long? = 0)

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
fun HomeScreen(homeData: Home, goToTabs: ()-> Unit, onBack: () -> Unit){
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
fun TabsScreen(data: Tabs, onBack: () -> Unit, onDetails: (id: Long?) -> Unit){

}