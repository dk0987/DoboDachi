package com.devdk.socialmedia.feature_post.presentation.add_post_screen

data class AddPostScreenStates (
     val public : Boolean = true ,
     val private : Boolean = false ,
     val isLoading : Boolean = false ,
     val contentUri : String = "" ,
)