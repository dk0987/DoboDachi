package com.devdk.socialmedia.feature_profile.presentation.profile_screen

import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_profile.domain.modal.User

data class ProfileStates (
    val user : User? = null,
    val endReached : Boolean = false ,
    val isLoading : Boolean = false ,
    val posts : List<Post> = emptyList()
)