package com.user.teentalk.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.user.teentalk.Data.Model.Kuesioner.Category
import com.user.teentalk.Data.Model.Kuesioner.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KuesionerViewModel : ViewModel() {
    private val _questions = MutableStateFlow(
        listOf(
            Question(1, "Saya merasa bahwa diri saya menjadi marah karena hal-hal sepele.", Category.STRESS),
            Question(2, "Saya merasa bibir saya sering kering.", Category.ANXIETY),
            Question(3,"Saya sama sekali tidak dapat merasakan perasaan positif.", Category.DEPRESSION),
            Question(4,"Saya mengalami kesulitan bernafas (misalnya: seringkali terengah-engah atau tidak dapat bernafas padahal tidak melakukan aktivitas fisik sebelumnya",Category.ANXIETY),
            Question(5,"Saya sepertinya tidak kuat lagi untuk melakukan suatu kegiatan.",Category.DEPRESSION),
            Question(6,"Saya cenderung bereaksi berlebihan terhadap suatu situasi.",Category.STRESS),
            Question(7,"Saya merasa goyah (misalnya, kaki terasa mau ’copot’).",Category.ANXIETY),
            Question(8,"Saya merasa sulit untuk bersantai.",Category.STRESS),
            Question(9,"Saya menemukan diri saya berada dalam situasi yang membuat saya merasa sangat cemas dan saya akan merasa sangat lega jika semua ini berakhir.",Category.ANXIETY),
            Question(10,"Saya merasa tidak ada hal yang dapat diharapkan di masa depan.",Category.DEPRESSION),
            Question(11,"Saya menemukan diri saya mudah merasa kesal.",Category.STRESS),
            Question(12,"Saya merasa telah menghabiskan banyak energi untuk merasa cemas.",Category.STRESS),
            Question(13,"Saya merasasedih dan tertekan.",Category.DEPRESSION),
            Question(14,"Saya menemukan diri saya menjadi tidak sabar ketika mengalami penundaan (misalnya: kemacetanlalulintas, menunggusesuatu).",Category.STRESS),
            Question(15,"Saya merasa lemas seperti mau pingsan.",Category.ANXIETY),
            Question(16,"Saya merasa saya kehilangan minat akan segala hal.",Category.DEPRESSION),
            Question(17,"Saya merasa bahwa saya tidak berharga sebagai seorang manusia.",Category.DEPRESSION),
            Question(18,"Saya merasa bahwa saya mudah tersinggung.",Category.STRESS),
            Question(19,"Saya berkeringat secara berlebihan (misalnya: tangan berkeringat),padahal temperatur tidak panas atau tidak melakukan aktivitas fisik sebelumnya.",Category.ANXIETY),
            Question(20,"Saya merasa takut tanpa alasan yang jelas.",Category.ANXIETY),
            Question(21,"Saya merasa bahwa hidup tidak bermanfaat.",Category.DEPRESSION),
            Question(22,"Saya merasa sulit untuk beristirahat.",Category.STRESS),
            Question(23,"Saya mengalami kesulitan dalam menelan.",Category.ANXIETY),
            Question(24,"Saya tidak dapat merasakan kenikmatan dari berbagai hal yang saya lakukan.",Category.DEPRESSION),
            Question(25,"Saya menyadari kegiatan jantung, walaupun saya tidak sehabis melakukan aktivitas fisik (misalnya: merasa detak jantung meningkat atau melemah).",Category.ANXIETY),
            Question(26,"Saya merasaputus asa dan sedih.",Category.DEPRESSION),
            Question(27,"Saya merasa bahwa saya sangat mudah marah.",Category.STRESS),
            Question(28,"Saya merasa saya hampir panik.",Category.ANXIETY),
            Question(29,"Saya merasa sulit untuk tenang setelah sesuatu membuat saya kesal.",Category.STRESS),
            Question(30,"Saya takut bahwa saya akan ‘terhambat’ oleh tugas-tugas sepele yang tidak biasa saya lakukan.",Category.ANXIETY),
            Question(31,"Saya tidak merasa antusias dalam hal apapun.",Category.DEPRESSION),
            Question(32,"Saya sulit untuk sabar dalam menghadapi gangguan terhadap hal yang sedang saya lakukan.",Category.STRESS),
            Question(33,"Saya sedang merasa gelisah.",Category.STRESS),
            Question(34,"Saya merasa bahwa saya tidak berharga.",Category.DEPRESSION),
            Question(35,"Saya tidak dapat memaklumi hal apapun yang menghalangi saya untuk menyelesaikan hal yang sedang saya lakukan.",Category.STRESS),
            Question(36,"Saya merasa sangat ketakutan.",Category.ANXIETY),
            Question(37,"Saya melihat tidak ada harapan untuk masa depan.",Category.DEPRESSION),
            Question(38,"Saya merasa bahwa hidup tidak berarti.",Category.DEPRESSION),
            Question(39,"Saya menemukan diri saya mudah gelisah.",Category.STRESS),
            Question(40,"Saya merasa khawatir dengan situasi dimana saya mungkin menjadi panik dan mempermalukan diri sendiri.",Category.ANXIETY),
            Question(41,"Saya merasa gemetar (misalnya: pada tangan).",Category.ANXIETY),
            Question(42,"Saya merasa sulit untuk meningkatkan inisiatif dalam melakukan sesuatu.",Category.DEPRESSION),

        )
    )
    val questions: StateFlow<List<Question>> = _questions

    private val _scores = MutableStateFlow(mapOf<Category, Int>())
    val scores: StateFlow<Map<Category, Int>> get() = _scores

    fun updateAnswer(questionId: Int, answer: Int) {
        _questions.value = _questions.value.map {
            if (it.id == questionId) it.copy(answer = answer) else it
        }
    }

    fun calculateScores() {
        val scores = mutableMapOf<Category, Int>().withDefault { 0 }
        _questions.value.forEach { question ->
            if (question.answer != -1) {
                scores[question.category] = scores.getValue(question.category) + question.answer
            }
        }
        _scores.value = scores
        Log.d("KuesionerViewModel", "Scores calculated: $scores")
    }

    fun getResultCategory(scores: Map<Category, Int>): Map<Category, String> {
        val results = scores.mapValues { (category, score) ->
            when (category) {
                Category.DEPRESSION -> when {
                    score <= 9 -> "Normal"
                    score in 10..13 -> "Ringan"
                    score in 14..20 -> "Sedang"
                    score in 21..27 -> "Berat"
                    else -> "Sangat Berat"
                }
                Category.ANXIETY -> when {
                    score <= 7 -> "Normal"
                    score in 8..9 -> "Ringan"
                    score in 10..14 -> "Sedang"
                    score in 15..19 -> "Berat"
                    else -> "Sangat Berat"
                }
                Category.STRESS -> when {
                    score <= 14 -> "Normal"
                    score in 15..18 -> "Ringan"
                    score in 19..25 -> "Sedang"
                    score in 26..33 -> "Berat"
                    else -> "Sangat Berat"
                }
            }
        }
        Log.d("KuesionerViewModel", "Results calculated: $results")
        return results
    }
}