package com.devdk.socialmedia.feature_profile.presentation.edit_profile_screen

import android.net.Uri
import com.devdk.socialmedia.feature_profile.domain.modal.User

data class EditProfileStates(
    val isLoading : Boolean = false,
    val isUpdating : Boolean = false,
    val isError : Boolean = false,
    val profileUrl : String = "",
    val bannerUrl : String = "" ,
    val updateProfileUrl : Uri? = null,
    val updateBannerUrl : Uri? = null,
)
