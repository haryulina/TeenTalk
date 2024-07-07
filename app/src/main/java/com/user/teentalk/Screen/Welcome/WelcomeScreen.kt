package com.user.teentalk.Screen.Welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.user.teentalk.Components.CButton
import com.user.teentalk.R
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun WelcomeScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dongker))
        ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ){
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.welcome),
                contentDescription = null,
                modifier = Modifier
                    .width(240.dp)
                    .height(240.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.weight(0.2f))
            Text(text = "Selamat Datang",
                fontSize = 28.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight(700),
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(top = 1.dp, bottom = 8.dp))
            Text(text = "Teman Curhat Remaja",
                textAlign = TextAlign.Center,
                fontFamily = PoppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight(300),
                color = Color.White
           )
            Spacer(modifier = Modifier.weight(1f))

            CButton(text = "Mulai Sekarang",
                onClick = {
                    navController.navigate("login_screen")
                })
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen(rememberNavController())
}