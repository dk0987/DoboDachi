package com.devdk.socialmedia.feature_auth.presentation.util

import androidx.annotation.StringRes

sealed class UiEvent{
        data class ShowSnackBar(val message : String?) : UiEvent()
        data class Navigate(val route : String , val message: String?) : UiEvent()
    }