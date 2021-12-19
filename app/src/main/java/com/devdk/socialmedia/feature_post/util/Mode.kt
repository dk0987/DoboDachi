package com.devdk.socialmedia.feature_post.util

import com.devdk.socialmedia.core.util.Const

sealed class Mode(val mode : String) {
    object Public : Mode(Const.PUBLIC)
    object Private : Mode(Const.PRIVATE)
}