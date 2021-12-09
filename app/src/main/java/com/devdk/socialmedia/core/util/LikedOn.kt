package com.devdk.socialmedia.core.util

sealed class LikedOn{
    data class Comment(val commentId : String) : LikedOn()
    data class Post(val postId : String) : LikedOn()
}
