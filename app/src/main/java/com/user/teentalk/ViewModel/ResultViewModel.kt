package com.user.teentalk.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.user.teentalk.Data.Model.Kuesioner.Category
import com.user.teentalk.Data.Model.ResultData.ResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ResultViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _historyResults = MutableStateFlow<List<ResultData>>(emptyList())
    val historyResults: StateFlow<List<ResultData>> = _historyResults

    fun saveResults(scores: Map<Category, Int>, results: Map<Category, String>, studentName: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val timestamp = System.currentTimeMillis()
            val resultData = scores.map { (category, score) ->
                mapOf(
                    "category" to category.name,
                    "score" to score,
                    "result" to results[category]
                )
            }
            val dataToSave = mapOf(
                "results" to resultData,
                "timestamp" to timestamp,
                "studentName" to studentName
            )

            firestore.collection("users")
                .document(userId)
                .collection("results")
                .add(dataToSave)
                .addOnSuccessListener { documentReference ->
                    Log.d("ResultViewModel", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("ResultViewModel", "Error adding document", e)
                }
        }
    }

    fun fetchHistoryResults() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            viewModelScope.launch {
                firestore.collection("users")
                    .document(userId)
                    .collection("results")
                    .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener { result ->
                        val results = result.map { document ->
                            val data = document.data
                            val timestamp = data["timestamp"] as Long
                            val resultList = data["results"] as List<Map<String, Any>>
                            val studentName = data["studentName"] as? String // Get studentName
                            resultList.map { resultEntry ->
                                ResultData(
                                    category = resultEntry["category"] as String,
                                    score = (resultEntry["score"] as Long).toInt(),
                                    result = resultEntry["result"] as String,
                                    timestamp = timestamp,
                                    studentName = studentName // Set studentName
                                )
                            }
                        }.flatten()
                        Log.d("ResultViewModel", "Fetched History Results: $results") // Add this line
                        _historyResults.value = results
                    }
                    .addOnFailureListener { e ->
                        Log.w("ResultViewModel", "Error getting documents", e)
                    }
            }
        }
    }

    fun fetchAllStudentsHistoryResults() {
        viewModelScope.launch {
            try {
                val result = firestore.collection("users").get().await()
                val allResults = result.documents.flatMap { document ->
                    val userId = document.id
                    val studentName = document.getString("name") ?: "Unknown"
                    val results = firestore.collection("users")
                        .document(userId)
                        .collection("results")
                        .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                        .get()
                        .await()
                    results.map { document ->
                        val data = document.data
                        val timestamp = data["timestamp"] as Long
                        val resultList = data["results"] as List<Map<String, Any>>
                        resultList.map { resultEntry ->
                            ResultData(
                                category = resultEntry["category"] as String,
                                score = (resultEntry["score"] as Long).toInt(),
                                result = resultEntry["result"] as String,
                                timestamp = timestamp,
                                studentName = studentName // Set studentName
                            )
                        }
                    }.flatten()
                }
                Log.d("ResultViewModel", "Fetched All Students History Results: $allResults") // Add this line
                _historyResults.value = allResults
            } catch (e: Exception) {
                Log.w("ResultViewModel", "Error getting all students' documents", e)
            }
        }
    }
}



