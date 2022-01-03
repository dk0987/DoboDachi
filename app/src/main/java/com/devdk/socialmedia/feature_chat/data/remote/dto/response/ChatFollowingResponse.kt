package com.devdk.socialmedia.feature_chat.data.remote.dto.response

data class ChatFollowingResponse(
    val userId : String ,
    val userProfileUrl : String ,
    val username : String ,
    val isOnline : Boolean
)
