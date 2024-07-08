package com.user.teentalk.Screen.Dashboard

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.user.teentalk.Components.BottomBar
import com.user.teentalk.Components.EducateItem
import com.user.teentalk.Data.Model.Educate.Educate
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.R
import com.user.teentalk.ViewModel.ChatScreenViewModel
import com.user.teentalk.ViewModel.DashboardViewModel
import com.user.teentalk.ViewModel.ProfileViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavHostController = rememberNavController(),
    dashboardViewModel: DashboardViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    val educates = dashboardViewModel.educates
    val userProfile by profileViewModel.userProfile.collectAsState()
    val profileImageUrl by profileViewModel.profileImageUrl.collectAsState()
    val userRole by dashboardViewModel.userRole.collectAsState()

    val imagePainter = if (profileImageUrl?.isNotEmpty() == true) {
        rememberAsyncImagePainter(profileImageUrl)
    } else {
        rememberAsyncImagePainter("https://via.placeholder.com/100")
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, dashboardViewModel = DashboardViewModel())
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Ensure some space below the ConstraintLayout
            ) {
                val (topImg, profile) = createRefs()
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .constrainAs(topImg) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .background(
                            colorResource(id = R.color.dongker),
                            shape = RoundedCornerShape(
                                bottomEnd = 25.dp,
                                bottomStart = 25.dp
                            )
                        )
                )
                Row(
                    modifier = Modifier
                        .padding(
                            top = 38.dp,
                            start = 24.dp,
                            end = 24.dp
                        )
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(start = 1.dp)
                            .weight(0.7f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Selamat Datang ",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = userProfile?.name ?: "",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )
                    }
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .size(60.dp)
                            .clip(CircleShape)
                            .clickable {
                                navController.navigate("profile_screen")
                            },
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 30.dp,
                            end = 30.dp
                        )
                        .shadow(
                            10.dp,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .background(
                            colorResource(id = R.color.kuning),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .constrainAs(profile) {
                            top.linkTo(topImg.bottom)
                            bottom.linkTo(topImg.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            if (userRole == "Konselor") {
                                navController.navigate(Screen.ChatSiswaScreen.route)
                            } else {
                                navController.navigate(Screen.Chat_History.route)
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .height(90.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.chat),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = if (userRole == "Konselor") "Chat Siswa" else "Chat Terapis",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Row {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .width(170.dp)
                        .padding(
                            top = 25.dp,
                        )
                        .background(
                            color = colorResource(id = R.color.kuning),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            navController.navigate(Screen.Kuesioner.route)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Kuesioner",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight(weight = 500)
                    )
                }

                Spacer(modifier = Modifier.width(25.dp))

                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .width(170.dp)
                        .padding(
                            top = 25.dp,
                        )
                        .background(
                            color = colorResource(id = R.color.kuning),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            if (userRole == "Konselor") {
                                navController.navigate(Screen.SiswaScreen.route)
                            } else {
                                navController.navigate(Screen.KonselorScreen.route)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (userRole == "Konselor") "Daftar Siswa" else "Daftar Terapis",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight(weight = 500)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EduTeen",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = PoppinsFontFamily,
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            bottom = 5.dp)
                )
                Text(
                    text = "Lainnya",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.EducateListScreen.route)
                        }
                )
            }

            // educateListItem
            educateListItem(
                educateList = educates,
                navigateToDetail = navigateToDetail,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun educateListItem(
    educateList: List<Educate>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow {
        itemsIndexed(educateList) { _, item ->
            key(item.id) {
                EducateItem(
                    id = item.id,
                    judul = item.judul,
                    imageUrl = item.imageUrl,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .animateItemPlacement(tween(durationMillis = 200))
                        .clickable {
                            navigateToDetail(item.id)
                        }
                )
            }
        }
    }
}

