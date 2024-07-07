package com.user.teentalk.Screen.Educate

import android.telecom.Call.Details
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.user.teentalk.Data.di.Injection
import com.user.teentalk.R
import com.user.teentalk.ViewModel.DetailViewModel
import com.user.teentalk.ViewModel.ViewModelFactory
import com.user.teentalk.ui.state.UIState

@Composable
fun DetailScreen(
    educateId : Int,
    navigateBack: () -> Unit,
    viewModel : DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))

) {
    viewModel.uiState.collectAsState(initial = UIState.Loading).value.let {
            uiState ->
        when(uiState){
            is UIState.Loading -> {
                viewModel.getEducateById(educateId)
            }
            is UIState.Success -> {
                val data = uiState.data
                DetailInfo(
                    imageUrl = data.imageUrl,
                    id = data.id,
                    judul = data.judul,
                    content = data.content,
                    navigateBack = navigateBack,
                )

            }
            is UIState.Error -> {}
        }
    }
}

@Composable
fun DetailInfo(
    id : Int,
    judul : String,
    imageUrl : String,
    content : String,
    navigateBack: () -> Unit,
    ) {
    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        )
        {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = judul,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = judul,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(5.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = content,
                    modifier = Modifier.padding(start = 2.dp, end = 8.dp),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .background(colorResource(id = R.color.white))
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                tint = Color.Black
            )

        }

    }

}