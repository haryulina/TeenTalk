package com.user.teentalk.Screen.Kuisioner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Kuesioner",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Petunjuk Pengisian \n" + "\n" + "Kuesioner ini terdiri dari berbagai pernyataan yang mungkin sesuai dengan pengalaman Saudara dalam menghadapi situasi hidup sehari- hari. Terdapat empat pilihan jawaban yang disediakan untuk setiap pernyataan yaitu:\n" +
                                    "\n" +
                                    "0\t: Tidak sesuai dengan saya sama sekali, atau tidak pernah.\n" +
                                    "1\t: Sesuai dengan saya sampai tingkat tertentu, atau kadang-kadang.\n" +
                                    "2\t: Sesuai dengan saya sampai batas yang dapat dipertimbangkan, atau lumayan sering.\n" +
                                    "3\t: Sangat sesuai dengan saya, atau sering sekali.\n" +
                                    "\n" +
                                    "\n" +
                                    "Selanjutnya, Saudara diminta untuk menjawab dengan cara memilih salah satu point pada salah satu kolom yang paling sesuai dengan pengalaman Saudara selama satu minggu belakangan ini. Tidak ada jawaban yang benar ataupun salah, karena itu isilah sesuai dengan keadaan diri Saudara yang sesungguhnya, yaitu berdasarkan jawaban pertama yang terlintas dalam pikiran Saudara.\n",
                            textAlign = TextAlign.Center,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight(500),
                        )
                    }
                }
            }

            items(questions) { question ->
                QuestionItem(question = question) { answer ->
                    viewModel.updateAnswer(question.id, answer)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.calculateScores()
                        navController.navigate(Screen.Result.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Selesai")
                }
            }
        }
    }
}

@Composable
fun QuestionItem(question: Question, onAnswerSelected: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.text,
                textAlign = TextAlign.Center,
                fontFamily = PoppinsFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = Color.Black
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                (0..3).forEach { answer ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = question.answer == answer,
                            onClick = { onAnswerSelected(answer) }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "$answer")
                    }
                }
            }
        }
    }
}