package com.devdk.socialmedia.feature_chat.domain.modal

data class ChatFollowings(
    val userId : String ,
    val userProfileUrl : String ,
    val username : String ,
    val isOnline : Boolean
)
