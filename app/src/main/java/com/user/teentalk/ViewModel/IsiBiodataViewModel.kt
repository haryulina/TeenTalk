package com.user.teentalk.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IsiBiodataViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> get() = _age

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onAgeChange(newAge: String) {
        _age.value = newAge
    }

    fun onAddressChange(newAddress: String) {
        _address.value = newAddress
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun saveBiodata(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val biodata = mapOf(
            "name" to _name.value,
            "age" to _age.value,
            "address" to _address.value,
            "description" to _description.value
        )

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            FirebaseFirestore.getInstance().collection("biodata")
                .document(it.uid)
                .set(biodata)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Unknown error")
                }
        }
    }

    fun fetchBiodata() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            FirebaseFirestore.getInstance().collection("biodata")
                .document(it.uid)
                .get()
                .addOnSuccessListener { document ->
                    _name.value = document.getString("name") ?: ""
                    _age.value = document.getString("age") ?: ""
                    _address.value = document.getString("address") ?: ""
                    _description.value = document.getString("description") ?: ""
                }
                .addOnFailureListener {
                    // Handle the error
                }
        }
    }
}

