package com.user.teentalk.Screen.Result

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.user.teentalk.ViewModel.KuesionerViewModel
import com.user.teentalk.ViewModel.ResultViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun ResultScreen(viewModel: KuesionerViewModel, resultViewModel: ResultViewModel) {
    val scores by viewModel.scores.collectAsState()
    val results = viewModel.getResultCategory(scores)

    LaunchedEffect(scores) {
        if (scores.isNotEmpty()) {
            resultViewModel.saveResults(scores, results)
        }
    }

    Log.d("ResultScreen", "Scores: $scores")
    Log.d("ResultScreen", "Results: $results")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (scores.isNotEmpty()) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Results",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    scores.forEach { (category, score) ->
                        val result = results[category]
                        ResultItem(category.name, score, result ?: "", category.color)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
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

@Composable
fun ResultItem(categoryName: String, score: Int, result: String, color: Color) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = categoryName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Score: $score",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Result: $result",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}



