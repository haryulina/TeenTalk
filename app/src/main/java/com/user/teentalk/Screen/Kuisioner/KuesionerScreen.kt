package com.user.teentalk.Screen.Kuisioner

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.R
import com.user.teentalk.ViewModel.DashboardViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KuesionerScreen(
    onBackClicked: () -> Unit,
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val userRole by dashboardViewModel.userRole.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Kuesioner",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight(600)
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
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Ensure some space below the Box
        ) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .background(
                            colorResource(id = R.color.dongker),
                            shape = RoundedCornerShape(
                                bottomEnd = 25.dp,
                                bottomStart = 25.dp
                            )
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.kuesioner_bg),
                            contentDescription = null,
                            modifier = Modifier
                                .width(600.dp)
                                .height(200.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.weight(0.2f))
                        Text(
                            text = if (userRole == "Konselor") "Riwayat Pengisian Kuesioner DASS Siswa" else "Pengisian Kuesioner DASS",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight(700),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.weight(0.2f))

                        Text(
                            text = "Depression Anxienty Stress Scale",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = PoppinsFontFamily,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight(500),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp)
                ) {
                    if (userRole != "Konselor") {
                        Button(
                            onClick = {
                                navController.navigate("kuesionerlist_screen")
                            },
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.dongker)
                            ),
                            modifier = Modifier
                                .height(75.dp)
                                .width(300.dp)
                                .padding(
                                    top = 25.dp,
                                    start = 20.dp,
                                    end = 20.dp
                                )
                                .clickable {
                                    navController.navigate(Screen.KuesionerList.route)
                                },
                        ) {
                            Text(
                                text = "Mulai Sekarang",
                                Modifier.shadow(10.dp),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = PoppinsFontFamily,
                                    fontWeight = FontWeight(500),
                                    color = Color.White
                                )
                            )
                        }
                    }

                    Button(
                        onClick = {
                            navController.navigate(Screen.History.route)
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.dongker)
                        ),
                        modifier = Modifier
                            .height(75.dp)
                            .width(300.dp)
                            .padding(
                                top = 25.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                            .clickable {
                                navController.navigate(Screen.History.route)
                            },
                    ) {
                        Text(
                            text = "Lihat Riwayat",
                            Modifier.shadow(10.dp),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight(500),
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}



