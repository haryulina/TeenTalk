package com.user.teentalk.Data.Model

data class Chat(
    val id: String,
    val participants: List<String>, // Email
    val participantNames: List<String>,
    val participantIDs: List<String>,
    val lastMessage: String
)
