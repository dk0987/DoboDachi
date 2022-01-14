package com.devdk.socialmedia.feature_chat.presentation.message_screen

sealed class MessageEvents {
    data class Message (val message : String) : MessageEvents()
    object SendMessage : MessageEvents()
}