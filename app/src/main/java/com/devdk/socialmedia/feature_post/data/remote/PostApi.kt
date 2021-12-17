package com.devdk.socialmedia.feature_post.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.Const.POST_PAGE_SIZE
import com.devdk.socialmedia.feature_post.data.remote.dto.response.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("api/post/getPost")
    suspend fun getPosts(
        @Query("userId") userId : String,
        @Query("page") page : Int = 0 ,
        @Query("pageSize") pageSize : Int = POST_PAGE_SIZE
    ) : BasicApiResponse<List<PostResponse>>


    companion object {
        const val BASE_URL = "http://192.168.96.141:8080/"
    }
}