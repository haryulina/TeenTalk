package com.user.teentalk.Data.Model.Kuesioner

import androidx.compose.ui.graphics.Color

data class Question(
    val id: Int,
    val text: String,
    val category: Category,
    var answer: Int = -1
) enum class Category(val color: Color) {
    STRESS(Color.Red),
    ANXIETY(Color.Blue),
    DEPRESSION(Color.Green)
}