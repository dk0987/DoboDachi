package com.devdk.socialmedia.feature_post.domain.modal

import com.devdk.socialmedia.core.util.Const

data class Post(
    val userId: String = "",
    val timeStamp : Long = 0,
    val userName : String = "",
    val userProfileUrl : String? = null,
    val postImageUrl : String? = null,
    val description : String? = null,
    val mode : String = Const.PUBLIC,
    val liked : Int = 0,
    val comment : Int = 0,
    val postId : String = "",
    var isLiked : Boolean = false,
    val isUser : Boolean = false
)
