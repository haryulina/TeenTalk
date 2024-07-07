package com.user.teentalk.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.user.teentalk.Data.Model.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatSiswaViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> get() = _chats

    init {
        fetchChats()
    }

    private fun fetchChats() {
        viewModelScope.launch {
            try {
                val currentUserEmail = auth.currentUser?.email ?: return@launch

                // Query to get all chats where the current user is a participant
                val querySnapshot = firestore.collection("chats")
                    .whereArrayContains("participants", currentUserEmail)
                    .get()
                    .await()

                val chatList = querySnapshot.documents.mapNotNull { document ->
                    val chatId = document.id
                    val lastMessage = document.getString("lastMessage") ?: ""
                    val participants = document.get("participants") as List<String>

                    // Get names of the participants
                    val participantNames = participants.map { email ->
                        firestore.collection("users").document(email).get().await()
                            .getString("name") ?: email
                    }

                    Chat(
                        id = chatId,
                        participants = participants,
                        participantNames = participantNames,
                        lastMessage = lastMessage
                    )
                }

                _chats.value = chatList
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}


