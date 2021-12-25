package com.devdk.socialmedia.feature_profile.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.BaseUrl
import com.devdk.socialmedia.feature_profile.data.remote.dto.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {

    @GET("api/user/getUserProfile")
    suspend fun getProfile(
        @Query("userId") userId : String
    ) : BasicApiResponse<UserResponse>

    @PUT("api/user/updateProfile")
    @Multipart
    suspend fun editProfile(
        @Part updateProfileRequest : MultipartBody.Part ,
        @Part updateProfileUrl : MultipartBody.Part?,
        @Part updateBannerUrl : MultipartBody.Part?
    ) : BasicApiResponse<Unit>


    companion object {
        const val BASE_URL = BaseUrl.PROFILE_BASE_URL
    }
}