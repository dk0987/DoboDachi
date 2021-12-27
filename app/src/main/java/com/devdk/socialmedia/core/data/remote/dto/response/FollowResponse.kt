package com.devdk.socialmedia.core.data.remote.dto.response

data class FollowResponse(
    val userId : String ,
    val profilePicUrl : String? = null ,
    val username : String ,
    val isFollowing : Boolean = false,
    val isUser : Boolean = false
)