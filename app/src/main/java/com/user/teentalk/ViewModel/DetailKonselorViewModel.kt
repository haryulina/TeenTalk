package com.user.teentalk.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.user.teentalk.Data.Model.User.User
import com.user.teentalk.Data.Model.User.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetailKonselorViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _counselor = MutableStateFlow<User?>(null)
    val counselor: StateFlow<User?> get() = _counselor

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> get() = _profileImageUrl

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> get() = _email

    fun fetchCounselorDetails(counselorId: String) {
        viewModelScope.launch {
            try {
                val userProfile = firestore.collection("users")
                    .document(counselorId)
                    .get()
                    .await()
                    .toObject(UserProfile::class.java)

                val biodataSnapshot = firestore.collection("biodata")
                    .document(counselorId)
                    .get()
                    .await()

                val biodata = biodataSnapshot.data

                val counselor = User(
                    id = counselorId,
                    name = userProfile?.name ?: "",
                    email = userProfile?.email ?: "",
                    role = "Konselor",
                    profileImageUrl = userProfile?.profileImageUrl,
                    age = biodata?.get("age") as? String ?: "",
                    address = biodata?.get("address") as? String ?: "",
                    description = biodata?.get("description") as? String ?: ""
                )

                _counselor.value = counselor
                _profileImageUrl.value = userProfile?.profileImageUrl
                _email.value = userProfile?.email
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }
}

