package com.user.teentalk.Data.Model

data class Chat(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val participantNames: List<String> = emptyList(),
    val lastMessage: String = ""
)