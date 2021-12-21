package com.devdk.socialmedia.feature_post.data.remote.dto.response

data class CommentResponse(
    val commentId : String ,
    val comment : String ,
    val likes : Int ,
    val isUser : Boolean ,
    val userProfileUrl : String? ,
    val isLiked : Boolean,
    val userName : String,
    val postId : String,
    val timeStamp : Long
)
