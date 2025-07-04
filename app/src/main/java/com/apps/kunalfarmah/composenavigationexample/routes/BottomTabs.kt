package com.apps.kunalfarmah.composenavigationexample.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class BottomTabs<T : Any>(val name: String, val route: T, val iconSelected: ImageVector, val iconUnselected: ImageVector)

val tabs = listOf(
    BottomTabs("Home", BottomTab.TabA, Icons.Filled.Home, Icons.Outlined.Home),
    BottomTabs("Favourites", BottomTab.TabB, Icons.Filled.Favorite, Icons.Outlined.Favorite),
    BottomTabs("Settings", BottomTab.TabC, Icons.Filled.Settings, Icons.Outlined.Settings)
)

sealed class BottomTab{
    @Serializable
    object TabA: BottomTab()
    @Serializable
    object TabB: BottomTab()
    @Serializable
    object TabC: BottomTab()
}
