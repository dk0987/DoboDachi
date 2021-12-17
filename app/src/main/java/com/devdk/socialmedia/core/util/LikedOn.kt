package com.devdk.socialmedia.core.util

sealed class LikedOn (val likedOn : Int){
    object Post : LikedOn(0)
    object Comment : LikedOn(1)
}