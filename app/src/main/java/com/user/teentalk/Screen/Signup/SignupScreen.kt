package com.user.teentalk.Screen.Signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.user.teentalk.Components.BtnHaveAccount
import com.user.teentalk.Components.CButton
import com.user.teentalk.Components.CTextField
import com.user.teentalk.Components.DropDownMenu
import com.user.teentalk.R
import com.user.teentalk.ViewModel.SignupState
import com.user.teentalk.ViewModel.SignupViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun SignupScreen(
    navController: NavHostController,
    signupViewModel: SignupViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("") }
    val signupState by signupViewModel.signupState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(signupState) {
        when (signupState) {
            is SignupState.Error -> {
                errorMessage = (signupState as SignupState.Error).message
                showDialog = true
            }
            is SignupState.Success -> {
                navController.navigate("login_screen")
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dongker))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 100.dp)
                    .height(100.dp)
            )

            Text(
                text = "Teen Talk",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Silakan daftar dengan informasi Anda",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(bottom = 24.dp)
            )

            // Dropdown Menu
            DropDownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                onItemSelected = { selectedItem ->
                    selectedRole = selectedItem
                    expanded = false
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Text Fields
            CTextField(
                hint = "Nama",
                value = name,
                onValueChange = { name = it },
                isPasswordField = false
            )
            CTextField(
                hint = "Email",
                value = email,
                onValueChange = { email = it },
                isPasswordField = false
            )
            CTextField(
                hint = "Kata Sandi",
                value = password,
                onValueChange = { password = it },
                isPasswordField = true

            )

            Spacer(modifier = Modifier.height(40.dp))

            CButton(text = "Daftar",
                onClick = {
                    // Validate email and password format
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Email dan kata sandi Tidak boleh kosong"
                        showDialog = true
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Format email salah"
                        showDialog = true
                    } else if (password.length < 6) {
                        errorMessage = "Kata sandi minimal harus 6 karakter"
                        showDialog = true
                    } else if (name.isBlank() || selectedRole.isBlank()) {
                        errorMessage = "Nama dan Pekerjaan Tidak Boleh kosong"
                        showDialog = true
                    } else {
                        // Proceed with signup
                        try {
                            signupViewModel.signup(email, password, name, selectedRole)
                        } catch (e: Exception) {
                            Log.e("SignupScreen", "Signup error: ${e.message}")
                            errorMessage = "An unexpected error occurred"
                            showDialog = true
                        }
                    }
                })

            BtnHaveAccount(
                onLoginTap = {
                    navController.navigate("login_screen")
                }
            )
        }

        when (signupState) {
            is SignupState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
                            Log.d("SignupScreen", "OK button clicked")
                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
