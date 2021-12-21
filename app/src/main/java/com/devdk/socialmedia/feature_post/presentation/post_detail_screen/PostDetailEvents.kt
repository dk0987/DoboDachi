package com.devdk.socialmedia.feature_post.presentation.post_detail_screen


sealed class PostDetailEvents {
    data class CommentTextField(val comment : String) : PostDetailEvents()
    object Comment : PostDetailEvents()
    data class Menu(val option : String , val commentId : String) : PostDetailEvents()
}
