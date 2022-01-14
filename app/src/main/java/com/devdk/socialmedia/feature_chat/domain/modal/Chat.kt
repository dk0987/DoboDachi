package com.devdk.socialmedia.feature_chat.domain.modal

data class Chat(
    val chatIds : List<String> ,
    val remoteUserUsername : String ,
    val remoteUserUserProfileUrl : String,
    val lastMessage : String ,
    val remoteUserId : String
)
