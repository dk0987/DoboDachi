package com.devdk.socialmedia.feature_auth.presentation.util

import android.util.Patterns
import com.devdk.socialmedia.core.presentation.util.Error.FIELD_EMPTY
import com.devdk.socialmedia.core.presentation.util.Error.INVALID_EMAIL
import com.devdk.socialmedia.core.presentation.util.Error.SHORT_LENGTH
import com.devdk.socialmedia.core.presentation.util.Error.WEAK_PASSWORD

class Validation {

    fun validateUsername(username: String): String? {
        val trimmedUsername = username.trim()
        if (trimmedUsername.isEmpty()) {
            return "Username $FIELD_EMPTY"
        }
        if (trimmedUsername.length < 3) {
            return "Username $SHORT_LENGTH"
        }
        return null
    }

    fun validateEmail(email: String): String? {
        val trimmedEmail = email.trim()
        val emailPattern = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])\$"
        val emailMatcher = Regex(emailPattern)
        if (trimmedEmail.isEmpty()) {
            return "Email $FIELD_EMPTY"
        }
        if (emailMatcher.find(email) == null) {
            return INVALID_EMAIL
        }
        return null
    }

    fun validatePassword(password: String): String? {
        val trimmedPassword = password.trim()
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val passwordMatcher = Regex(passwordPattern)
        if (trimmedPassword.isEmpty()) {
            return "Password $FIELD_EMPTY"
        }
        if (trimmedPassword.length < 6) {
            return "Password $SHORT_LENGTH"
        }
        if (passwordMatcher.find(password) == null) {
            return WEAK_PASSWORD
        }
        return null
    }
}