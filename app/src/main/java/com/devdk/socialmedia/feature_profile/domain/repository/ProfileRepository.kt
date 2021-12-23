package com.devdk.socialmedia.feature_profile.domain.repository

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_profile.domain.modal.User

interface ProfileRepository {
    suspend fun getProfile(userId : String) : Resource<User>
}