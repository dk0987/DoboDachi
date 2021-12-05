package com.devdk.socialmedia.feature_auth.data.request

data class RegisterUserRequest(
    val userName : String ,
    val eMail : String ,
    val password : String
)