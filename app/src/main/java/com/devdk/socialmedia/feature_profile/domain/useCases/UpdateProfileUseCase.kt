package com.devdk.socialmedia.feature_profile.domain.useCases

import android.net.Uri
import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_profile.data.remote.ProfileApi
import com.devdk.socialmedia.feature_profile.domain.modal.UpdateUser
import com.devdk.socialmedia.feature_profile.domain.repository.ProfileRepository

class UpdateProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke (updateUser: UpdateUser , profileUrl : Uri? , bannerUrl : Uri?) : Resource<Unit>{
        if (updateUser.username.isBlank()){
            return Resource.Error("Username ${Error.FIELD_EMPTY}")
        }
        if (profileUrl.toString().isBlank()){
            return Resource.Error("Profile ${Error.FIELD_EMPTY}")
        }
        if (bannerUrl.toString().isBlank()){
            return Resource.Error("Banner ${Error.FIELD_EMPTY}")
        }
        else {
            val result = profileRepository.updateUser(
                updateUser = updateUser ,
                profileUrl = profileUrl,
                bannerUrl = bannerUrl
            )
            return when(result) {
                is Resource.Success -> {
                    Resource.Success(Unit)
                }
                is Resource.Error -> {
                    Resource.Error(result.message)
                }
            }
        }

    }
}