package com.devdk.socialmedia.core.util

sealed class UiEvent{
        data class ShowSnackBar(val message : String?) : UiEvent()
        data class Navigate(val route : String , val message: String?) : UiEvent()
    }