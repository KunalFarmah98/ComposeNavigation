package com.apps.kunalfarmah.composenavigationexample.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class BottomTabs<T : Any>(val name: String, val route: T, val icon: ImageVector)

val tabs = listOf(
    BottomTabs("TabA", BottomTab.TabA, Icons.Filled.Home),
    BottomTabs("TabB", BottomTab.TabB, Icons.Filled.Search),
    BottomTabs("TabC", BottomTab.TabC, Icons.Filled.Settings)
)

sealed class BottomTab{
    @Serializable
    object TabA: BottomTab()
    @Serializable
    object TabB: BottomTab()
    @Serializable
    object TabC: BottomTab()
}
