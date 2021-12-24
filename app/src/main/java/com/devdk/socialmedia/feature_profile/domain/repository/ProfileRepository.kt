package com.devdk.socialmedia.feature_profile.domain.repository

import android.net.Uri
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_profile.domain.modal.UpdateUser
import com.devdk.socialmedia.feature_profile.domain.modal.User

interface ProfileRepository {
    suspend fun getProfile(userId : String) : Resource<User>

    suspend fun updateUser(updateUser: UpdateUser, profileUrl : Uri, bannerUrl : Uri) : Resource<Unit>
}