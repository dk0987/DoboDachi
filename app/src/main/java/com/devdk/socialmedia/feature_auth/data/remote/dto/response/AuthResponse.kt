package com.devdk.socialmedia.feature_auth.data.remote.dto.response

data class AuthResponse(
    val successful: Boolean ,
    val message : String? = null,
    val token : String? = null,
    val userId : String? = null
)