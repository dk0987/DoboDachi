package com.devdk.socialmedia.feature_profile.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_profile.data.remote.ProfileApi
import com.devdk.socialmedia.feature_profile.data.remote.dto.request.UpdateUserRequest
import com.devdk.socialmedia.feature_profile.domain.modal.UpdateUser
import com.devdk.socialmedia.feature_profile.domain.modal.User
import com.devdk.socialmedia.feature_profile.domain.repository.ProfileRepository
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi ,
    private val gson: Gson
) : ProfileRepository {

    override suspend fun getProfile(userId: String , ownerId : String): Resource<User> {
        val response = profileApi.getProfile(userId)
        var user : User? = null
        return try {
            if (response.successful) {
                response.data?.let { userResponse ->
                    user = User(
                        userId = userResponse.userId,
                        username = userResponse.username,
                        bannerUrl = userResponse.bannerUrl,
                        profileUrl = userResponse.profileUrl,
                        isUser = ownerId == userResponse.userId,
                        isFollowing = userResponse.isFollowing,
                        bio = userResponse.bio,
                        followers = userResponse.followers,
                        following = userResponse.following,
                        posts = userResponse.posts
                    )
                }
                Resource.Success(user)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun updateUser(
        updateUser: UpdateUser,
        profileUrl: Uri?,
        bannerUrl: Uri?
    ): Resource<Unit> {
        val updateProfileRequest = UpdateUserRequest(
            username = updateUser.username,
            bio = updateUser.bio,
        )
        val profile = profileUrl?.toFile()
        val banner = bannerUrl?.toFile()
        return try {
            val response = profileApi.editProfile(
                updateProfileRequest = MultipartBody.Part.createFormData(
                    name = "update_Profile",
                    value = gson.toJson(updateProfileRequest)
                ),
                updateProfileUrl = profile?.let {
                  MultipartBody.Part.createFormData(
                        name = "update_profile_pic",
                        filename = it.name,
                        body = it.asRequestBody()
                    )
                },
                updateBannerUrl = banner?.let {
                    MultipartBody.Part.createFormData(
                        name = "update_banner",
                        filename = it.name,
                        body = it.asRequestBody()
                    )
                }
            )
            if (response.successful){
                Resource.Success(Unit)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }
}