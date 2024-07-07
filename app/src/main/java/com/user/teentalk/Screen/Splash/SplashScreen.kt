package com.user.teentalk.Screen.Splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.R
import com.user.teentalk.ViewModel.DashboardViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, dashboardViewModel: DashboardViewModel) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)

        // Setelah animasi selesai, cek apakah user login atau tidak
        val startDestination = if (dashboardViewModel.isUserLoggedIn.value == true) {
            Screen.Dashboard.route
        } else {
            Screen.Welcome.route
        }

        navController.navigate(startDestination) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Splash(alpha = alphaAnim)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme())
                    colorResource(id = R.color.dongker)
                else colorResource(id = R.color.dongker)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .width(240.dp)
                    .height(240.dp)
                    .alpha(alpha = alpha), // Apply alpha animation here
                contentScale = ContentScale.Fit
            )
            Text(
                text = "T E E N  T A L K",
                fontFamily = PoppinsFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                modifier = Modifier.alpha(alpha = alpha)
            )
        }
    }
}
