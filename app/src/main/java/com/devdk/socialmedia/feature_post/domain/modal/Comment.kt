package com.devdk.socialmedia.feature_post.domain.modal

data class Comment (
    val username :String ,
    val comment : String ,
    val userProfileUrl : String ,
    val likes : Int ,
    val isLiked : Boolean ,
    val commentId : String ,
    val postID : String ,
    val isUser : Boolean ,
    val timeStamp : Long
)