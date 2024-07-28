package com.user.teentalk.App

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.Screen.Chat.ChatScreen
import com.user.teentalk.Screen.Chat.ChatSiswaScreen
import com.user.teentalk.Screen.Dashboard.DashboardScreen
import com.user.teentalk.Screen.Educate.DetailScreen
import com.user.teentalk.Screen.Educate.EducateListScreen
import com.user.teentalk.Screen.History.HistoryScreen
import com.user.teentalk.Screen.IsiBiodata.IsiBiodataScreen
import com.user.teentalk.Screen.Konselor.KonselorScreen
import com.user.teentalk.Screen.Kuisioner.KuesionerListScreen
import com.user.teentalk.Screen.Kuisioner.KuesionerScreen
import com.user.teentalk.Screen.Login.LoginScreen
import com.user.teentalk.Screen.Profile.ProfileScreen
import com.user.teentalk.Screen.Result.ResultScreen
import com.user.teentalk.Screen.Signup.SignupScreen
import com.user.teentalk.Screen.Siswa.SiswaScreen
import com.user.teentalk.Screen.Splash.SplashScreen
import com.user.teentalk.Screen.Welcome.WelcomeScreen
import com.user.teentalk.Screen.chatHistory.ChatHistoryScreen
import com.user.teentalk.Screen.detailKonselor.DetailKonselorScreen
import com.user.teentalk.ViewModel.DashboardViewModel
import com.user.teentalk.ViewModel.KuesionerViewModel
import com.user.teentalk.ViewModel.ResultViewModel


@Composable
fun TeenTalkApp(
    navController: NavHostController = rememberNavController(),
    dashboardViewModel: DashboardViewModel = viewModel(),
    kuesionerViewModel: KuesionerViewModel = viewModel(),
    resultViewModel: ResultViewModel = viewModel()
) {
    LaunchedEffect(key1 = true) {
        dashboardViewModel.checkForActiveSession()
    }

    val userRole by dashboardViewModel.userRole.collectAsState(initial = "")

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController, dashboardViewModel)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(route = Screen.Signup.route) {
            SignupScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                navigateToDetail = { educateId ->
                    navController.navigate(Screen.DetailScreen.createRoute(educateId))
                },
                navController = navController,
            )
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(
                navArgument("educateId") { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt("educateId") ?: -1
            DetailScreen(
                educateId = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.EducateListScreen.route) {
            EducateListScreen(navigateToDetail = { educateId ->
                navController.navigate(Screen.DetailScreen.createRoute(educateId))
            }, navigateBack = {
                navController.navigateUp()
            })
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClicked = { navController.popBackStack() },
                onLogoutClicked = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onChangePhotoClicked = { /* Handle change photo action */ },
                onIsiBiodataClicked = {navController.navigate(Screen.Biodata.route)}

            )
        }
        composable(Screen.KonselorScreen.route) {
            KonselorScreen(
                onBackClicked = { navController.popBackStack() },
                navController = navController
            )
        }
        composable(Screen.Kuesioner.route) {
            KuesionerScreen(
                onBackClicked = { navController.popBackStack() },
                navController
            )
        }
        composable(Screen.KuesionerList.route) {
            KuesionerListScreen(
                viewModel = kuesionerViewModel,
                navController = navController
            )
        }
        composable(Screen.ChatSiswaScreen.route) {
            ChatSiswaScreen(
                onBackClicked = { navController.popBackStack() },
                navController = navController
            )
        }
        composable(
            route = Screen.Chat.route + "/{otherUserEmail}",
            arguments = listOf(navArgument("otherUserEmail") { type = NavType.StringType })
        ) {
            val otherUserEmail = it.arguments?.getString("otherUserEmail") ?: ""
            ChatScreen(
                otherUserEmail = otherUserEmail
            )
        }

        composable(Screen.SiswaScreen.route) {
            SiswaScreen(
                onBackClicked = { navController.popBackStack() },
                navController = navController
            )
        }
        composable(Screen.Result.route) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val studentName = currentUser?.displayName ?: "Unknown"
            ResultScreen(viewModel = kuesionerViewModel, resultViewModel = resultViewModel, navController = navController, studentName = studentName)
        }

        composable(Screen.History.route) {
            userRole?.let { it1 -> HistoryScreen(resultViewModel = resultViewModel, userRole = it1) }
        }
        composable(Screen.Biodata.route) {
            IsiBiodataScreen(onBackClicked = { navController.popBackStack() })
        }
        composable("detail_konselor") {
            KonselorScreen(onBackClicked = { navController.popBackStack() }, navController = navController)
        }
        composable("detail_konselor/{counselorId}") { backStackEntry ->
            val counselorId = backStackEntry.arguments?.getString("counselorId") ?: return@composable
            DetailKonselorScreen(
                counselorId = counselorId,
                onBackClicked = { navController.popBackStack() },
                navController= navController
            )
        }
        composable(Screen.Chat_History.route) {
            ChatHistoryScreen(navController = navController, onBackClicked = { navController.popBackStack() })
        }
    }
}
