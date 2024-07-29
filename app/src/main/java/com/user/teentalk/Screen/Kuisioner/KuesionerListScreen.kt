package com.user.teentalk.Screen.Kuisioner

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val itemsPerPage = 10
    var currentPage by remember { mutableStateOf(0) }
    val currentPageQuestions = questions.drop(currentPage * itemsPerPage).take(itemsPerPage)

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
                            text = "Petunjuk Pengisian",
                            textAlign = TextAlign.Justify,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Kuesioner ini terdiri dari berbagai pernyataan yang mungkin sesuai dengan pengalaman Saudara dalam menghadapi situasi hidup sehari-hari. Terdapat empat pilihan jawaban yang disediakan untuk setiap pernyataan yaitu:",
                            textAlign = TextAlign.Justify,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LikertScaleTable()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Selanjutnya, Saudara diminta untuk menjawab dengan cara memilih salah satu point pada salah satu kolom yang paling sesuai dengan pengalaman Saudara selama satu minggu belakangan ini. Tidak ada jawaban yang benar ataupun salah, karena itu isilah sesuai dengan keadaan diri Saudara yang sesungguhnya, yaitu berdasarkan jawaban pertama yang terlintas dalam pikiran Saudara.",
                            textAlign = TextAlign.Justify,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                        )
                    }
                }
            }

            items(currentPageQuestions) { question ->
                QuestionItem(question = question) { answer ->
                    viewModel.updateAnswer(question.id, answer)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                if (currentPage == (questions.size / itemsPerPage)) {
                    Button(
                        onClick = {
                            val allQuestionsAnswered = questions.all { it.answer != -1 }
                            if (allQuestionsAnswered) {
                                viewModel.calculateScores()
                                viewModel.resetAnswers()
                                navController.navigate(Screen.Result.route)
                            } else {
                                errorMessage = "Jawaban harus di isi semua atau tidak boleh kosong"
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Selesai")
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentPage > 0) {
                        Button(onClick = { currentPage-- }) {
                            Text("Previous")
                        }
                    }
                    val pageNotFilled = currentPageQuestions.any { it.answer == -1 }
                    if ((currentPage + 1) * itemsPerPage < questions.size) {
                        Button(
                            onClick = { currentPage++ },
                            enabled = !pageNotFilled
                        ) {
                            Text("Next")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LikertScaleTable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
    ) {
        TableRow("0", "Tidak sesuai dengan saya sama sekali, atau tidak pernah.", Color(0xFFF0F0F0))
        TableRow("1", "Sesuai dengan saya sampai tingkat tertentu, atau kadang-kadang.", Color.White)
        TableRow("2", "Sesuai dengan saya sampai batas yang dapat dipertimbangkan, atau lumayan sering.", Color(0xFFF0F0F0))
        TableRow("3", "Sangat sesuai dengan saya, atau sering sekali.", Color.White)
    }
}

@Composable
fun TableRow(value: String, description: String, backgroundColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .border(1.dp, Color.Black)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        Text(
            text = description,
            modifier = Modifier
                .weight(4f)
                .padding(start = 8.dp)
        )
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
                fontWeight = FontWeight(500),
                color = Color.Black
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                (0..3).forEach { answer ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        RadioButton(
                            selected = question.answer == answer,
                            onClick = { onAnswerSelected(answer) }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$answer",
                            fontSize = 18.sp,
                            fontFamily = PoppinsFontFamily,
                        )
                    }
                }
            }
        }
    }
}

