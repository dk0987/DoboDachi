package com.devdk.socialmedia.feature_post.presentation.feed_screen

sealed class FeedScreenEvents {
    data class OnLike(
        val parentId: String,
        val isLiked: Boolean
    ) : FeedScreenEvents()
    data class OnShare(val postId : String) : FeedScreenEvents()
    data class Menu(val option : String , val postId : String) : FeedScreenEvents()
}
