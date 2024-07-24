package com.user.teentalk.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.user.teentalk.Data.Model.User.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class KonselorViewModel : ViewModel(){

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _counselors = MutableStateFlow<List<User>>(emptyList())
    val counselors: StateFlow<List<User>> get() = _counselors

    init {
        fetchCounselors()
    }

    private fun fetchCounselors() {
        viewModelScope.launch {
            try {
                val querySnapshot = firestore.collection("users")
                    .whereEqualTo("role", "Konselor")
                    .get()
                    .await()

                val counselorList = querySnapshot.documents.map { document ->
                    User(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        email = document.getString("email")?:"",
                        role = document.getString("role") ?: "",
                        profileImageUrl = document.getString("profileImageUrl")
                    )
                }

                _counselors.value = counselorList
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }
}

