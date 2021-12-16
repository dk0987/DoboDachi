package com.devdk.socialmedia.feature_post.data.remote.dto.response

import com.devdk.socialmedia.core.util.Const.PUBLIC

data class PostResponse(
    val userId: String = "",
    val timeStamp : Long = 0,
    val userName : String = "",
    val userProfileUrl : String? = null,
    val postImageUrl : String? = null,
    val description : String? = null,
    val mode : String = PUBLIC,
    val liked : Int = 0,
    val comment : Int = 0,
    val isPostLiked : Boolean = false,
    val postId : String = ""
)
