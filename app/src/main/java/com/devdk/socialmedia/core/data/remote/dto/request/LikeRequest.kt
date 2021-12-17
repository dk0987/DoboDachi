package com.devdk.socialmedia.core.data.remote.dto.request

data class LikeRequest(
    val parentId : String ,
    val parentType : Int = 0
)
