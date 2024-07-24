package com.user.teentalk.Screen.detailKonselor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.R
import com.user.teentalk.ViewModel.DetailKonselorViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKonselorScreen(
    counselorId: String,
    onBackClicked: () -> Unit,
    navController: NavController,
    viewModel: DetailKonselorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    LaunchedEffect(counselorId) {
        viewModel.fetchCounselorDetails(counselorId)
    }

    val counselor by viewModel.counselor.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detail Konselor",
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
        counselor?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Center the content vertically
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center) // Center the card horizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (it.profileImageUrl != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = it.profileImageUrl),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(128.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_person),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(128.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = it.name,
                            fontSize = 24.sp,
                            color = Color.Black,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center // Center the text
                        )

                        Text(
                            text = "Konselor",
                            fontSize = 20.sp,
                            color = Color.Gray,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center // Center the text
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = it.email,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center // Center the text
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Age: ${it.age}",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center // Center the text
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Address: ${it.address}",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center // Center the text
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Description:\n${it.description}",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center // Center the text
                        )

                        Button(
                            onClick = {
                                navController.navigate(Screen.Chat.createRoute(it.email)) // Perbarui rute navigasi
                            },
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.dongker)
                            ),
                            modifier = Modifier
                                .height(75.dp)
                                .width(300.dp)
                                .padding(top = 25.dp, start = 20.dp, end = 20.dp)
                        ) {
                            Text(
                                text = "Chat Sekarang",
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
}
