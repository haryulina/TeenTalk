package com.user.teentalk.Screen.Result

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.ViewModel.KuesionerViewModel
import com.user.teentalk.ViewModel.ResultViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    viewModel: KuesionerViewModel,
    resultViewModel: ResultViewModel,
    navController: NavController,
    studentName: String
) {
    val scores by viewModel.scores.collectAsState()
    val results = viewModel.getResultCategory(scores)

    LaunchedEffect(scores) {
        if (scores.isNotEmpty()) {
            resultViewModel.saveResults(scores, results, studentName)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hasil", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Dashboard.route) }) {
                        Icon(Icons.Default.Close, modifier = Modifier.size(24.dp), contentDescription = "Close")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (scores.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    scores.forEach { (category, score) ->
                        val result = results[category]
                        ResultCard(category.name, score, result ?: "")
                    }
                }
            } else {
                Text(
                    text = "No data available",
                    fontSize = 24.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun ResultCard(categoryName: String, score: Int, result: String) {
    val color = when (result) {
        "Normal" -> colorResource(id = com.user.teentalk.R.color.putih)
        "Ringan" -> colorResource(id = com.user.teentalk.R.color.biru)
        "Sedang" -> colorResource(id = com.user.teentalk.R.color.hijau)
        "Berat" -> colorResource(id = com.user.teentalk.R.color.kuning_muda)
        "Sangat Berat" -> colorResource(id = com.user.teentalk.R.color.merah)
        else -> Color.Gray
    }
    Card (
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = categoryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Score: $score",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Hasil: $result",
                fontSize = 16.sp,
            )
        }
    }
}



