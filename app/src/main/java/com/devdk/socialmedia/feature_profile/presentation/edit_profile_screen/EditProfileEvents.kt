package com.devdk.socialmedia.feature_profile.presentation.edit_profile_screen

sealed class EditProfileEvents {
    data class Username(val username : String) : EditProfileEvents()
    data class Bio(val bio : String) : EditProfileEvents()
    object Save : EditProfileEvents()
}
