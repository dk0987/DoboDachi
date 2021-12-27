package com.devdk.socialmedia.core.data.remote

import com.devdk.socialmedia.core.data.remote.dto.request.FollowRequest
import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.data.remote.dto.response.FollowResponse
import com.devdk.socialmedia.core.util.BaseUrl.FOLLOW_BASE_URL
import retrofit2.http.*

interface FollowApi {

    @POST("api/follow/follow")
    suspend fun follow(
        @Body followRequest: FollowRequest
    ) : BasicApiResponse<Unit>

    @DELETE("api/follow/unfollow")
    suspend fun unfollow(
        @Query("userId") unfollowRequest: String
    ) : BasicApiResponse<Unit>

    @GET("api/follow/getFollowers")
    suspend fun getFollowers(
        @Query("userId") getFollowers : String
    ) : BasicApiResponse<List<FollowResponse>>

    @GET("api/follow/getFollowings")
    suspend fun getFollowings(
        @Query("userId") getFollowing : String
    ) : BasicApiResponse<List<FollowResponse>>


    companion object {
        const val BASE_URL = FOLLOW_BASE_URL
    }

}