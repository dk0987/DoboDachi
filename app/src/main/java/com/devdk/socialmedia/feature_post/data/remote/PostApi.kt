package com.devdk.socialmedia.feature_post.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.BaseUrl
import com.devdk.socialmedia.core.util.Const.POST_PAGE_SIZE
import com.devdk.socialmedia.feature_post.data.remote.dto.request.AddPostRequest
import com.devdk.socialmedia.feature_post.data.remote.dto.response.FeedScreenProfile
import com.devdk.socialmedia.feature_post.data.remote.dto.response.PostResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostApi {

    @GET("api/post/getPost")
    suspend fun getPosts(
        @Query("userId") userId : String,
        @Query("page") page : Int = 0 ,
        @Query("pageSize") pageSize : Int = POST_PAGE_SIZE
    ) : BasicApiResponse<List<PostResponse>>

    @Multipart
    @POST("api/post/addPost")
    suspend fun addPost(
        @Part addPostRequest : MultipartBody.Part ,
        @Part postImage : MultipartBody.Part
    ) : BasicApiResponse<Unit>

    @DELETE("api/post/deletePost")
    suspend fun deletePost(
        @Query("postId") parentId : String
    ) : BasicApiResponse<Unit>

    @GET("api/post/getPostById")
    suspend fun getPostById(
        @Query("postId") postId : String
    ) : BasicApiResponse<PostResponse>

    @GET("api/post/getPostsForUser")
    suspend fun getPostsForUser(
        @Query("userId") userId : String,
        @Query("page") page : Int = 0 ,
        @Query("pageSize") pageSize : Int = POST_PAGE_SIZE
    ) : BasicApiResponse<List<PostResponse>>


    @GET("api/user/getUserProfilePic")
    suspend fun getProfilePic() : BasicApiResponse<FeedScreenProfile>

    companion object {
        const val BASE_URL = BaseUrl.POST_BASE_URL
    }
}