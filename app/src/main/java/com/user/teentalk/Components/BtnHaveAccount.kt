package com.user.teentalk.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun BtnHaveAccount (
    onLoginTap: () -> Unit = {},
    ){
    Row (
        modifier = Modifier.padding(top = 12.dp, bottom = 52.dp)
    ){
        Text(text = "Sudah punya Akses?",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(text = "Masuk",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight(500),
                color = Color.White
            ),
            modifier = Modifier.clickable{
                onLoginTap()
            }
        )
    }

}