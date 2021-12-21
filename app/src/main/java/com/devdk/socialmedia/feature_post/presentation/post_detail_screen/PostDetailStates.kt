package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import com.devdk.socialmedia.feature_post.domain.modal.Post

data class PostDetailStates(
    val post : Post? = null,
    val isPostLoading : Boolean = false,
)
