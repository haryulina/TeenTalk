package com.user.teentalk.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.user.teentalk.Data.Model.User.User
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.R
import com.user.teentalk.ViewModel.SiswaScreenViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaScreen(
    onBackClicked: () -> Unit,
    viewModel: SiswaScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController // Tambahkan NavController sebagai parameter
){
    val siswa by viewModel.siswa.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Siswa",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight(700)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.dongker),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(siswa) { siswa ->
                SiswaItem(siswa) { email ->
                    navController.navigate(Screen.Chat.createRoute(email)) // Gunakan NavController untuk navigasi
                }
            }
        }
    }
}

@Composable
fun SiswaItem(siswa: User, onCounselorClicked: (String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.kuning)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                onCounselorClicked(siswa.email) // Panggil callback saat item diklik
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = siswa.name,
                fontSize = 20.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = siswa.role,
                fontSize = 16.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.White
            )
        }
    }
}