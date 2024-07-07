package com.user.teentalk.Screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.user.teentalk.ViewModel.KuesionerViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun ResultScreen(viewModel: KuesionerViewModel) {
    val scores by viewModel.scores.collectAsState()
    val results = viewModel.getResultCategory(scores)

    Log.d("ResultScreen", "Scores: $scores")
    Log.d("ResultScreen", "Results: $results")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (scores.isNotEmpty()) {
            scores.forEach { (category, score) ->
                val result = results[category]
                Text(
                    text = "${category.name}: $score ($result)",
                    fontSize = 24.sp,
                    color = category.color,
                    fontFamily = PoppinsFontFamily
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            Text(
                text = "No data available",
                fontSize = 24.sp
            )
        }
    }
}
