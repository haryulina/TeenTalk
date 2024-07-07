package com.user.teentalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.FirebaseApp
import com.user.teentalk.App.TeenTalkApp
import com.user.teentalk.ui.theme.TeenTalkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        installSplashScreen()
        setContent {
            TeenTalkTheme {
                TeenTalkApp()
            }
        }
    }
}
