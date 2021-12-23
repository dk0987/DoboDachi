package com.devdk.socialmedia.feature_profile.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.feature_profile.data.remote.dto.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("api/user/getUserProfile")
    suspend fun getProfile(
        @Query("userId") userId : String
    ) : BasicApiResponse<UserResponse>


    companion object {
        const val BASE_URL = "http://192.168.67.141:8080/"
    }
}