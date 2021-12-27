package com.devdk.socialmedia.feature_profile.domain.useCases

import android.content.SharedPreferences
import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.util.AuthConst
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_profile.domain.modal.User
import com.devdk.socialmedia.feature_profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(userId : String) : Resource<User> {
        if (userId.isBlank()){
            return Resource.Error(Error.USER_NOT_FOUND)
        }
        return when (val result = profileRepository.getProfile(userId )) {
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}