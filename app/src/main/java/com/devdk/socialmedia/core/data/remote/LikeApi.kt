package com.devdk.socialmedia.core.data.remote

import com.devdk.socialmedia.core.data.remote.dto.request.LikeRequest
import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface LikeApi {

    @POST("api/like/like")
    suspend fun like(
        @Body likeRequest: LikeRequest
    ) : BasicApiResponse<Unit>

    @DELETE("api/like/unlike")
    suspend fun unlike(
        @Query("parentId") parentId : String
    ) : BasicApiResponse<Unit>


    companion object {
        const val BASE_URL = "http://192.168.96.141:8080/"
    }
}