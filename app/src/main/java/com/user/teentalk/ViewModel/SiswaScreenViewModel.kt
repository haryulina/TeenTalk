package com.user.teentalk.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.user.teentalk.Data.Model.User.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SiswaScreenViewModel : ViewModel() {


    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _siswa = MutableStateFlow<List<User>>(emptyList())
    val siswa: StateFlow<List<User>> get() = _siswa

    init {
        fetchSiswa()
    }

    private fun fetchSiswa() {
        viewModelScope.launch {
            try {
                val querySnapshot = firestore.collection("users")
                    .whereEqualTo("role", "Siswa")
                    .get()
                    .await()

                val siswaList = querySnapshot.documents.map { document ->
                    User(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        email = document.getString("email")?:"",
                        role = document.getString("role") ?: ""
                    )
                }

                _siswa.value = siswaList
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }
}