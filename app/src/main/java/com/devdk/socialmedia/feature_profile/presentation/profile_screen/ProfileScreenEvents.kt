package com.devdk.socialmedia.feature_profile.presentation.profile_screen

sealed class ProfileScreenEvents {
    data class OnLike(
        val parentId: String,
        val isLiked: Boolean
    ) : ProfileScreenEvents()
    object OnShare : ProfileScreenEvents()
    data class Menu(val option : String , val postId : String) : ProfileScreenEvents()
}
