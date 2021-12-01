package com.devdk.socialmedia.feature_auth.presentation.login_screen

sealed class LoginEvents {
    data class Email(val email : String) : LoginEvents()
    data class Password(val password : String) : LoginEvents()
    object Toggle : LoginEvents()
    object Login : LoginEvents()
}
