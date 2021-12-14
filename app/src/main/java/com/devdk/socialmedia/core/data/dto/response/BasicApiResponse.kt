package com.devdk.socialmedia.core.data.dto.response

data class BasicApiResponse<T>(
    val successful : Boolean ,
    val data : T? = null ,
    val message : String? = null
)
