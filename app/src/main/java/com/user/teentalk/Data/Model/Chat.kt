package com.user.teentalk.Data.Model

data class Chat(
    val id: String,
    val participants: List<String>,
    val participantNames: List<String>,
    val lastMessage: String
)
