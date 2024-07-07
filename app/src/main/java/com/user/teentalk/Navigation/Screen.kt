package com.user.teentalk.Navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Welcome : Screen("welcome_screen")
    object Signup : Screen("signup_screen" )
    object Login : Screen("login_screen" )
    object Dashboard : Screen("dashboard_screen" )
    object Profile : Screen ("profile_screen")
    object ChatSiswaScreen : Screen("chat_siswa_screen")
    object Chat : Screen("chat_screen") {
        fun createRoute(otherUserEmail: String): String {
            return "chat_screen/$otherUserEmail"
        }
    }
    object Kuesioner : Screen ("kuesioner_screen")
    object KuesionerList : Screen ("kuesionerlist_screen")
    object EducateListScreen : Screen("educate_screen")
    object KonselorScreen : Screen("konselor_screen")
    object SiswaScreen : Screen("siswa_screen")
    object DetailScreen : Screen("dashboard_screen/{educateId}") {
        fun createRoute(educateId: Int) = "dashboard_screen/$educateId"
    }
    object Result : Screen("result_screen")
}
