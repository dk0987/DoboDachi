package com.devdk.socialmedia.feature_chat.data.remote.dto.response

data class MessageResponse(
    val messageId : String ,
    val message : String ,
    val userId : String ,
    val timeStamp : Long
)