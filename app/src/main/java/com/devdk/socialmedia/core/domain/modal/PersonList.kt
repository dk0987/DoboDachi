package com.devdk.socialmedia.core.domain.modal

data class PersonList(
    val userId : String = "" ,
    val userProfile : String? = null ,
    val username : String = "" ,
    val isFollowing : Boolean = false,
    val isUser : Boolean = false
)
