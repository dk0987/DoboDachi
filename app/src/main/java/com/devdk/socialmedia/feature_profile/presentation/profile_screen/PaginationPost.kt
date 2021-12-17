package com.devdk.socialmedia.feature_profile.presentation.profile_screen

import com.devdk.socialmedia.feature_post.domain.modal.Post

data class PaginationPost(
    val items : List<Post> = emptyList() ,
    val isLoading : Boolean = false ,
    val endReached :  Boolean = false
)
