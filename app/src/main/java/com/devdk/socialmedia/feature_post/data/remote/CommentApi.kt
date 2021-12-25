package com.devdk.socialmedia.feature_post.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.BaseUrl
import com.devdk.socialmedia.feature_post.data.remote.dto.request.CommentRequest
import com.devdk.socialmedia.feature_post.data.remote.dto.response.CommentResponse
import retrofit2.http.*

interface CommentApi {

    @POST("api/comment/comment")
    suspend fun comment(
        @Body commentRequest: CommentRequest
    ) : BasicApiResponse<Unit>

    @GET("api/comment/getComment")
    suspend fun getComments(
        @Query("postId") postId : String ,
    ) : BasicApiResponse<List<CommentResponse>>

    @DELETE("api/comment/delete")
    suspend fun deleteComment(
        @Query("commentId")commentId : String
    ) : BasicApiResponse<Unit>

    companion object {
        const val BASE_URL = BaseUrl.COMMENT_BASE_URL
    }
}