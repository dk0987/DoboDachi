package com.devdk.socialmedia.feature_chat.data.remote.dto.response

data class ChatResponse(
    val chatIds : List<String> ,
    val remoteUserUsername : String ,
    val remoteUserUserProfileUrl : String,
    val lastMessage : String ,
    val remoteUserId : String
)
