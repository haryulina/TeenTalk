package com.user.teentalk.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.user.teentalk.Data.Model.Educate.Educate
import com.user.teentalk.Data.Model.Educate.EducateData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DashboardViewModel : ViewModel() {

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    private val _educates = mutableListOf<Educate>()
    val educates: List<Educate>
        get() = _educates

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> get() = _userRole

    init {
        _educates.addAll(EducateData.educates)
        fetchUserRole()
    }

    fun checkForActiveSession() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d(TAG, "valid session")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "user is not logged in")
            isUserLoggedIn.value = false
        }
    }

    private fun fetchUserRole() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            FirebaseFirestore.getInstance().collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    _userRole.value = document.getString("role")
                }
        }
    }
}
