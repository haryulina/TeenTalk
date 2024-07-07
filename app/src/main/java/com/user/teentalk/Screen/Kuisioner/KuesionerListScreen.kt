package com.user.teentalk.Screen.Kuisioner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.user.teentalk.Data.Model.Kuesioner.Question
import com.user.teentalk.Navigation.Screen
import com.user.teentalk.ViewModel.KuesionerViewModel
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun KuesionerListScreen(
    navController: NavHostController,
    viewModel: KuesionerViewModel
) {
    val questions by viewModel.questions.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(questions) { question ->
            QuestionItem(question = question) { answer ->
                viewModel.updateAnswer(question.id, answer)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.calculateScores()
                navController.navigate(Screen.Result.route)
            }) {
                Text("Selesai")
            }
        }
    }
}


@Composable
fun QuestionItem(question: Question, onAnswerSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = question.text,
            fontSize = 18.sp,
            color = question.category.color,
            fontFamily = PoppinsFontFamily
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Row {
            (0..3).forEach { answer ->
                RadioButton(
                    selected = question.answer == answer,
                    onClick = { onAnswerSelected(answer) }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "$answer")
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}