package com.user.teentalk.Screen.Konselor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.user.teentalk.Data.Model.User.User
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.R
import com.user.teentalk.ViewModel.KonselorViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KonselorScreen(
    onBackClicked: () -> Unit,
    viewModel: KonselorViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {
    val counselors by viewModel.counselors.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Konselor",
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
            items(counselors) { counselor ->
                CounselorItem(counselor) { email ->
                    navController.navigate("detail_konselor/${counselor.id}")
                }
            }
        }
    }
}
@Composable
fun CounselorItem(counselor: User, onCounselorClicked: (String) -> Unit) {
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
                onCounselorClicked(counselor.email)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = counselor.profileImageUrl),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray), // Placeholder background
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = counselor.name,
                    fontSize = 20.sp,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = counselor.role,
                    fontSize = 16.sp,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White
                )
            }
        }
    }
}