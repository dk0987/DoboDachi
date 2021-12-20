package com.devdk.socialmedia.core.presentation.util

object Error {

    const val POST_ID_NOT_FOUND = "Post not found"
    const val  FIELD_EMPTY = "is Empty"
    const val  SHORT_LENGTH = "is too short"
    const val  INVALID_EMAIL = "Please enter valid email"
    const val  WEAK_PASSWORD = "Strong password required (ex.123@Dk)"

    data class Error(
        var userNameError : String? = null,
        var emailError : String? = null,
        var passwordError : String? = null,
        var responseError : String? = null
    )
}