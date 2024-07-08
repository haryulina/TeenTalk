package com.user.teentalk.Screen.Chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.user.teentalk.Data.Model.Message
import com.user.teentalk.ViewModel.ChatScreenViewModel
import kotlinx.coroutines.tasks.await


@Composable
fun ChatScreen(
    chatScreenViewModel: ChatScreenViewModel = viewModel(),
    otherUserID: String
) {
    var messageText by remember { mutableStateOf("") }

    val firestore = FirebaseFirestore.getInstance()
    var otherUserName by remember { mutableStateOf("Loading...") }

    LaunchedEffect(otherUserID) {
        try {
            val documentSnapshot = firestore.collection("users").document(otherUserID).get().await()
            val name = documentSnapshot.getString("name") ?: "Unknown"
            otherUserName = name
        } catch (e: Exception) {
            Log.e("ChatScreen", "Error fetching user name", e)
        }
    }
    val messages by chatScreenViewModel.messages.collectAsState()

    Column(Modifier.fillMaxSize()) {
        MessagesList(messages, Modifier.weight(1f))
        Spacer(modifier = Modifier.height(8.dp))
        InputField(
            messageText = messageText,
            onMessageChange = { messageText = it },
            onMessageSend = { text ->
                val message = Message(
                    sender = FirebaseAuth.getInstance().currentUser?.email ?: "unknown",
                    text = text
                )
                chatScreenViewModel.sendMessage(message, otherUserID)
                messageText = ""
            }
        )
    }
}

@Composable
fun MessagesList(messages: List<Message>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier
            .fillMaxHeight()
            .padding(bottom = 8.dp)
    ) {
        items(messages) { message ->
            val isCurrentUser = message.sender == currentUserEmail
            MessageItem(message, isCurrentUser)
        }
    }
}

@Composable
fun MessageItem(message: Message, isCurrentUser: Boolean) {
    val alignment = if (isCurrentUser) Alignment.End else Alignment.Start
    val backgroundColor = if (isCurrentUser) Color(0xFFDCF8C6) else Color(0xFFFFFFFF)
    val textColor = Color.Black

    Row(
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onMessageSend: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            placeholder = { Text("Type a message") }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                if (messageText.isNotBlank()) {
                    onMessageSend(messageText)
                }
            }
        ) {
            Text("Send")
        }
    }
}

