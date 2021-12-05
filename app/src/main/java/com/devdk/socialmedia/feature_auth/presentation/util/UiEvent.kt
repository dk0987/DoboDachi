package com.devdk.socialmedia.feature_auth.presentation.util

sealed class UiEvent{
        data class ShowSnackBar(val message : String?) : UiEvent()
        data class Navigate(val route : String) : UiEvent()
    }