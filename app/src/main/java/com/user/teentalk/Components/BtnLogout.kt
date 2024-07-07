package com.user.teentalk.Components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.user.teentalk.R

@Composable
fun BtnLogout(onLogoutClicked: () -> Unit) {
    Button(
        onClick = {
            onLogoutClicked()
        },
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.kuning)),
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Logout", color = Color.White)
    }
}