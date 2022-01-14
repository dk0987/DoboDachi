package com.devdk.socialmedia.feature_chat.presentation.message_screen

import com.devdk.socialmedia.feature_chat.domain.modal.Message

data class MessageState(
    val messages : List<Message> = emptyList(),
    val isLoading : Boolean = false,
    val endReached : Boolean = false,
)
