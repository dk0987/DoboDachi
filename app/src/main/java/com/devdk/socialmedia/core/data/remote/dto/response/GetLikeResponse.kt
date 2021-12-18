package com.devdk.socialmedia.core.data.remote.dto.response

data class GetLikeResponse(
    val userProfilePicUrl : String? = null ,
    val username : String = "" ,
    val isFollowing : Boolean = false ,
    val userID : String = "",
    val isUser : Boolean = false
)
