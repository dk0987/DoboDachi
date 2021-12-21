package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import com.devdk.socialmedia.feature_post.domain.modal.Comment

data class CommentStates(
    val comments : List<Comment> = emptyList(),
    val isCommentLoading : Boolean = false,
    val endReached : Boolean = false ,

)