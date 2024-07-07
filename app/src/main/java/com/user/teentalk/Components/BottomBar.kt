package com.user.teentalk.Components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.user.teentalk.Navigation.BottomBarScreen
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.R
import com.user.teentalk.ViewModel.DashboardViewModel

@Composable
fun BottomBar(navController: NavHostController, dashboardViewModel: DashboardViewModel) {
    val screens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.Chat,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val userRole by dashboardViewModel.userRole.collectAsState()

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.dongker),
        contentColor = LocalContentColor.current
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
                userRole = userRole // Pass userRole to AddItem
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    userRole: String?
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = screen.ic,
                tint = Color.White,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            if (screen == BottomBarScreen.Chat) {
                // Handle navigation based on userRole
                if (userRole == "Konselor") {
                    navController.navigate(Screen.ChatSiswaScreen.route)
                } else {
                    navController.navigate(Screen.KonselorScreen.route)
                }
            } else {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    )
}
