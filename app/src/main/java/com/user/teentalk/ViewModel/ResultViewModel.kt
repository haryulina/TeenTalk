package com.user.teentalk.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.user.teentalk.Data.Model.Kuesioner.Category
import com.user.teentalk.Data.Model.ResultData.ResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _historyResults = MutableStateFlow<List<ResultData>>(emptyList())
    val historyResults: StateFlow<List<ResultData>> = _historyResults

    fun saveResults(scores: Map<Category, Int>, results: Map<Category, String>) {
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
                "timestamp" to timestamp
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
                            resultList.map { resultEntry ->
                                ResultData(
                                    category = resultEntry["category"] as String,
                                    score = (resultEntry["score"] as Long).toInt(),
                                    result = resultEntry["result"] as String,
                                    timestamp = timestamp
                                )
                            }
                        }.flatten()
                        _historyResults.value = results
                    }
                    .addOnFailureListener { e ->
                        Log.w("ResultViewModel", "Error getting documents", e)
                    }
            }
        }
    }
}
