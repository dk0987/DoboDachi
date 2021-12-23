package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import com.devdk.socialmedia.core.util.LikedOn

sealed class PostDetailEvents {
    data class CommentTextField(val comment : String) : PostDetailEvents()
    object Comment : PostDetailEvents()
    data class CommentMenu(val option : String, val commentId : String) : PostDetailEvents()
    data class PostMenu(val option : String, val postId : String) : PostDetailEvents()
    data class OnLike(
        val parentId: String,
        val isLiked: Boolean,
        val parentType : LikedOn
    ) : PostDetailEvents()

}
