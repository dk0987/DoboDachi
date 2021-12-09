package com.devdk.socialmedia.feature_post.presentation.add_post_screen

sealed class AddPostDetailEvents {
    data class Description(val description : String) : AddPostDetailEvents()
    data class ViewMode(val mode : String) : AddPostDetailEvents()
    object Post : AddPostDetailEvents()
}
