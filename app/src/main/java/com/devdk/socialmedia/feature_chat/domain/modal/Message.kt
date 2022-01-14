package com.devdk.socialmedia.feature_chat.domain.modal

data class Message(
    val messageId : String ,
    val message : String ,
    val userId : String ,
    val timeStamp : Long
)
