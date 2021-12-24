package com.devdk.socialmedia.feature_search.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.feature_search.data.remote.dto.response.SearchedUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("api/user/getUserBySearch")
    suspend fun searchUser(
        @Query("userId") query : String
    ) : BasicApiResponse<List<SearchedUserResponse>>

    companion object {
        const val BASE_URL = "http://192.168.129.141:8080/"
    }

}
