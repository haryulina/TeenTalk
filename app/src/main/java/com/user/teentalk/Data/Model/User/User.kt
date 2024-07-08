package com.user.teentalk.Data.Model.User

data class User(
    val id: String,
    val name: String,
    val email : String,
    val role: String,
    val profileImageUrl: String? = null, // Add profileImageUrl
    val age: String = "", // Add age
    val address: String = "", // Add address
    val description: String = "" // Add description
)