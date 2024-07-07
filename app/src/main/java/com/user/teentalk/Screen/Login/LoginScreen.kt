package com.user.teentalk.Screen.Login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.user.teentalk.Components.BtnAccount
import com.user.teentalk.Components.CButton
import com.user.teentalk.Components.CTextField
import com.user.teentalk.R
import com.user.teentalk.ViewModel.LoginState
import com.user.teentalk.ViewModel.LoginViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by loginViewModel.loginState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Error -> {
                errorMessage = (loginState as LoginState.Error).message
                showDialog = true
            }
            is LoginState.Success -> {
                navController.navigate("dashboard_screen")
            }
            else -> {}
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dongker))
    )
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ){
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 100.dp)
                .height(100.dp))

        Text(text = "TEEN TALK",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight(500),
                color = Color.White)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Silakan masuk dengan Akun Anda",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.LightGray
            ),
            modifier = Modifier
                .padding(bottom = 24.dp)
        )

        //Text Field
        CTextField(
            value = email,
            onValueChange = { email = it },
            hint = "Email",
            isPasswordField = false
        )

        CTextField(
            value = password,
            onValueChange = {  password= it },
            hint = "Kata Sandi",
            isPasswordField = true
        )

        Spacer(modifier = Modifier.height(40.dp))

        when (loginState) {
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }
            else -> {}
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    Log.d("SignupScreen", "Dialog dismissed")
                    showDialog = false
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(text = errorMessage)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            Log.d("LoginScreen", "OK button clicked")
                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        CButton(text = "Masuk",
            onClick = { if (email.isBlank() || password.isBlank()) {
                errorMessage = "Email dan kata sandi Tidak boleh kosong"
                showDialog = true
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errorMessage = "Format email salah"
                showDialog = true
            } else if (password.length < 6) {
                errorMessage = "Kata sandi minimal harus 6 karakter"
                showDialog = true
            } else {
                try {
                    loginViewModel.login(email, password)
                } catch (e: Exception) {
                    Log.e("LoginScreen", "Login error: ${e.message}")
                    errorMessage = "An unexpected error occurred"
                    showDialog = true
                }
            }
            })

        BtnAccount(
            onSignupTap = {
                navController.navigate("signup_screen")
            }
        )

    }
}
@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}