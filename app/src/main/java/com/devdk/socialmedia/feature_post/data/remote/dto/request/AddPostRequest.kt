package com.devdk.socialmedia.feature_post.data.remote.dto.request

import com.devdk.socialmedia.core.util.Const.PRIVATE
import com.devdk.socialmedia.core.util.Const.PUBLIC

data class AddPostRequest(
    val description : String? = null ,
    val mode : String = PUBLIC,
)

