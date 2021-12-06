package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

sealed class PostDetailEvents {
    data class Comment(val comment : String) : PostDetailEvents()
}
