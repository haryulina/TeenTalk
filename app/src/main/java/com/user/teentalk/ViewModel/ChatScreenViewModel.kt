package com.user.teentalk.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.user.teentalk.Data.Model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatScreenViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private var chatId: String = ""
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    fun initializeChat(otherUserEmail: String) {
        viewModelScope.launch {
            val currentUserEmail = auth.currentUser?.email ?: return@launch
            chatId = generateChatId(currentUserEmail, otherUserEmail)
            val currentUserDocument = firestore.collection("users").document(currentUserEmail).get().await()
            val otherUserDocument = firestore.collection("users").document(otherUserEmail).get().await()
            val currentUserName = currentUserDocument.getString("name") ?: currentUserEmail
            val otherUserName = otherUserDocument.getString("name") ?: otherUserEmail

            // Update chat document with names
            firestore.collection("chats").document(chatId)
                .set(mapOf(
                    "participants" to listOf(currentUserEmail, otherUserEmail),
                    "participantNames" to listOf(currentUserName, otherUserName)
                ), SetOptions.merge())

            listenForMessages(chatId)
        }
    }

    private fun generateChatId(userEmail1: String, userEmail2: String): String {
        return if (userEmail1 < userEmail2) {
            "${userEmail1}_$userEmail2"
        } else {
            "${userEmail2}_$userEmail1"
        }
    }

    fun sendMessage(message: Message, otherUserEmail: String) {
        viewModelScope.launch {
            try {
                firestore.collection("chats").document(chatId).collection("messages")
                    .add(message)

                // Get names
                val currentUserEmail = auth.currentUser?.email ?: return@launch
                val currentUserDocument = firestore.collection("users").document(currentUserEmail).get().await()
                val otherUserDocument = firestore.collection("users").document(otherUserEmail).get().await()
                val currentUserName = currentUserDocument.getString("name") ?: currentUserEmail
                val otherUserName = otherUserDocument.getString("name") ?: otherUserEmail

                // Update last message and names in chat document
                firestore.collection("chats").document(chatId)
                    .set(mapOf(
                        "lastMessage" to message.text,
                        "participants" to listOf(currentUserEmail, otherUserEmail),
                        "participantNames" to listOf(currentUserName, otherUserName)
                    ), SetOptions.merge())
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun listenForMessages(chatId: String) {
        firestore.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _messages.value = emptyList()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messagesList = snapshot.documents.mapNotNull { it.toObject(Message::class.java) }
                    _messages.value = messagesList
                } else {
                    _messages.value = emptyList()
                }
            }
    }
}



