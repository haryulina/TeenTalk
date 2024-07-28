package com.user.teentalk.Data.Model.ResultData

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ResultData(
    val category: String,
    val score: Int,
    val result: String,
    val timestamp: Long,
    val studentName: String? = null
) {
    val formattedDate: String
        get() {
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
}
