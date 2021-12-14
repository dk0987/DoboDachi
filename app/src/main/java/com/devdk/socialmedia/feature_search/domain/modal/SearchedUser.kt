package com.devdk.socialmedia.feature_search.domain.modal

data class SearchedUser (
    val username : String = "" ,
    val userId : String = "",
    val profilePicUrl : String? = null,
    val description : String? = null ,
    val isFollowing : Boolean = false
)