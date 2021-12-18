package com.devdk.socialmedia.core.data.remote

import com.devdk.socialmedia.core.data.remote.dto.request.LikeRequest
import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.data.remote.dto.response.GetLikeResponse
import retrofit2.http.*

interface LikeApi {

    @POST("api/like/like")
    suspend fun like(
        @Body likeRequest: LikeRequest
    ) : BasicApiResponse<Unit>

    @DELETE("api/like/unlike")
    suspend fun unlike(
        @Query("parentId") parentId : String
    ) : BasicApiResponse<Unit>

    @GET("api/like/getLikes")
    suspend fun getLikes(
        @Query("parentId") parentId: String
    ) : BasicApiResponse<List<GetLikeResponse>>

    companion object {
        const val BASE_URL = "http://192.168.7.141:8080/"
    }
}