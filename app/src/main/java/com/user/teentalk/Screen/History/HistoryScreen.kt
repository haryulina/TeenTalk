package com.user.teentalk.Screen.History

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
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import com.user.teentalk.Data.Model.ResultData.ResultData
import com.user.teentalk.ViewModel.ResultViewModel

@Composable
fun HistoryScreen(resultViewModel: ResultViewModel) {
    val historyResults by resultViewModel.historyResults.collectAsState()

    LaunchedEffect(Unit) {
        resultViewModel.fetchHistoryResults()
    }

    val groupedResults = historyResults.groupBy { it.formattedDate }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (groupedResults.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                groupedResults.forEach { (date, results) ->
                    item {
                        HistoryCard(date, results)
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
fun HistoryCard(date: String, results: List<ResultData>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Date: $date",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            results.forEach { result ->
                HistoryItem(result)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun HistoryItem(result: ResultData) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = result.category,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Score: ${result.score}",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Result: ${result.result}",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

