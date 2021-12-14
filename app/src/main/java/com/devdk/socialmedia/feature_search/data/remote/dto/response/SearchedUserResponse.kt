package com.devdk.socialmedia.feature_search.data.remote.dto.response

data class SearchedUserResponse(
    val userId :String ,
    val profileUrl : String? ,
    val isFollowing : Boolean = false ,
    val username : String ,
    val description : String?
)
