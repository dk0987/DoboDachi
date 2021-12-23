package com.devdk.socialmedia.feature_profile.domain.modal

data class User(
    val userId : String ,
    val username : String ,
    val bannerUrl : String ,
    val profileUrl : String ,
    val isUser : Boolean ,
    val isFollowing : Boolean ,
    val bio : String ,
    val followers : Int,
    val following : Int ,
    val posts : Int ,
)
