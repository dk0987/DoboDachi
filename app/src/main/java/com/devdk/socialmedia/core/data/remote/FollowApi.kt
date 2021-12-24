package com.devdk.socialmedia.core.data.remote

import com.devdk.socialmedia.core.data.remote.dto.request.FollowRequest
import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface FollowApi {

    @POST("api/follow/follow")
    suspend fun follow(
        @Body followRequest: FollowRequest
    ) : BasicApiResponse<Unit>

    @DELETE("api/follow/unfollow")
    suspend fun unfollow(
        @Query("userId") unfollowRequest: String
    ) : BasicApiResponse<Unit>

    companion object {
        const val BASE_URL = "http://169.254.193.130:8080/"
    }

}