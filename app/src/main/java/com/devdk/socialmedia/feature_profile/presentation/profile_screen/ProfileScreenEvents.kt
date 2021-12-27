package com.devdk.socialmedia.feature_profile.presentation.profile_screen

import com.devdk.socialmedia.feature_search.presentation.SearchEvents

sealed class ProfileScreenEvents {
    data class OnLike(
        val parentId: String,
        val isLiked: Boolean
    ) : ProfileScreenEvents()
    object OnShare : ProfileScreenEvents()
    data class Follow(val userId : String , val isFollowed : Boolean) : ProfileScreenEvents()
    data class Menu(val option : String , val postId : String) : ProfileScreenEvents()
}
