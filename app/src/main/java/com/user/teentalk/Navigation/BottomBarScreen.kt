package com.user.teentalk.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val ic: ImageVector
) {
    object Dashboard : BottomBarScreen(
        route = "dashboard_screen",
        ic = Icons.Default.Home
    )
    object Chat : BottomBarScreen("chat_screen/{otherUserEmail}",
        ic = Icons.Default.Email) {
        fun createRoute(otherUserEmail: String) = "chat_screen/$otherUserEmail"
    }
    object Profile : BottomBarScreen(
        route = "profile_screen",
        ic = Icons.Default.Person
    )
}