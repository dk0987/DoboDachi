package com.devdk.socialmedia.feature_post.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.feature_post.data.remote.dto.request.CommentRequest
import com.devdk.socialmedia.feature_post.data.remote.dto.response.CommentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentApi {

    @POST("api/comment/comment")
    suspend fun comment(
        @Body commentRequest: CommentRequest
    ) : BasicApiResponse<Unit>

    @GET("api/comment/getComment")
    suspend fun getComments(
        @Query("postId") postId : String ,
    ) : BasicApiResponse<List<CommentResponse>>


    companion object {
        const val BASE_URL = "http://192.168.67.141:8080/"
    }
}