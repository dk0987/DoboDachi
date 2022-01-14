package com.devdk.socialmedia.feature_activity.presentation.util

const val POST = "Post"
const val COMMENT = "Comment"
const val FOLLOWED_YOU = " followed you"
const val COMMENTED_ON_POST = " commented on your "

const val ACTION_FOLLOWED = 0
const val ACTION_COMMENTED = 1
const val ACTION_LIKED_ON_POST = 2
const val ACTION_LIKED_ON_COMMENT = 3

sealed class Action(val actionText : String){
    object Followed : Action(FOLLOWED_YOU)
    object Comment : Action(COMMENTED_ON_POST)
    data class Liked( val likedOn: String) : Action(
        when(likedOn){
            POST -> {
                " liked on your $POST"
            }
            COMMENT ->{
                " liked on your $COMMENT"
            }
            else -> ""
        }
    )
    companion object{
        fun toAction(action : Int) : Action? {
            return when(action) {
                ACTION_FOLLOWED -> {
                    Followed
                }
                ACTION_COMMENTED -> {
                    Comment
                }
                ACTION_LIKED_ON_POST -> {
                    Liked(POST)
                }
                ACTION_LIKED_ON_COMMENT -> {
                    Liked(COMMENT)
                }
                else -> null
            }
        }


    }
}


