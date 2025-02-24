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
object Login

@Serializable
data class LoginResponse(val token: String, val userId: Long)

@Serializable
data class Home(val token: String, val userId: Long)

@Serializable
data class Detail(val id: Long? = 0)

@Composable
fun LoginScreen(onLogin: (data: LoginResponse) -> Unit){
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
    }
}


@Composable
fun HomeScreen(homeData: Home, onDetail: (id: Long)-> Unit, onBack: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Home Screen")
        Text(text = "Token: ${homeData.token}")
        Text(text = "UserId: ${homeData.userId}")
        Button(
            onClick = {
                onDetail(20)
            }
        ) {
            Text("Go to Details")
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