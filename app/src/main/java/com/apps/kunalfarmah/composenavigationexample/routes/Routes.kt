package com.apps.kunalfarmah.composenavigationexample.routes

import kotlinx.serialization.Serializable

sealed class Screens {

    @Serializable
    object Root : Screens()

    @Serializable
    object Auth : Screens()

    @Serializable
    data class Main(val token: String? = "abcd", val userId: Long? = 0) : Screens()

    @Serializable
    object Login : Screens()

    @Serializable
    object Register : Screens()

    @Serializable
    object Tabs : Screens()

    @Serializable
    data class Home(val token: String? = "abcd", val userId: Long? = 0) : Screens()

    @Serializable
    data class Detail(val id: Long? = 0) : Screens()
}

@Serializable
data class LoginResponse(val token: String, val userId: Long)