package com.user.teentalk.ViewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.user.teentalk.Data.Model.User.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.ByteArrayOutputStream

class ProfileViewModel : ViewModel()  {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> get() = _userProfile

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> get() = _profileImageUrl

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> get() = _email

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val userProfile = document.toObject(UserProfile::class.java)
                _userProfile.value = userProfile
                userProfile?.profileImageUrl?.let {
                    _profileImageUrl.value = it
                }
                _email.value = document.getString("email") ?: "user@example.com"
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileViewModel", "Error fetching user profile", exception)
            }
    }

    fun uploadProfileImage(imageBitmap: Bitmap) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val storageRef = storage.reference.child("profile_images/$userId.jpg")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        storageRef.putBytes(data)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val profileImageUrl = uri.toString()
                    firestore.collection("users").document(userId).update("profileImageUrl", profileImageUrl)
                    _profileImageUrl.value = profileImageUrl
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileViewModel", "Error uploading profile image", exception)
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}