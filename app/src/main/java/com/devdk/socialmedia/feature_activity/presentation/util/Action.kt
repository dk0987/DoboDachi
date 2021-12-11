package com.devdk.socialmedia.feature_activity.presentation.util

const val POST = "Post"
const val COMMENT = "Comment"

sealed class Action(val actionText : String){
    object Followed : Action(" followed you")
    object Comment : Action(" commented on your post")
    data class Liked( val likedOn: String) : Action(
        when(likedOn){
            POST -> {
                " liked on your $POST"
            }
            COMMENT ->{
                " liked on your $COMMENT"
            }
            else -> {
                ""
            }
        }
    )
}
