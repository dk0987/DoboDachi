package com.devdk.socialmedia.feature_post.presentation.feed_screen

sealed class FeedScreenEvents {
    object OnLike : FeedScreenEvents()
    object OnShare : FeedScreenEvents()
    object Toggle : FeedScreenEvents()
    data class Menu(val option : String) : FeedScreenEvents()
    data class LikedBy(val postId : String) : FeedScreenEvents()
    data class Profile(val userId : String) : FeedScreenEvents()
    data class OnPost(val postId : String) : FeedScreenEvents()
}
