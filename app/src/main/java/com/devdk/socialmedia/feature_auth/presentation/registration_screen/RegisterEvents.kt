package com.devdk.socialmedia.feature_auth.presentation.registration_screen

sealed class RegisterEvents {
    data class Username(val username : String) : RegisterEvents()
    data class Email(val eMail : String) : RegisterEvents()
    data class Password(val password : String) : RegisterEvents()
    object Toggle : RegisterEvents()
    object Register : RegisterEvents()
}

