package com.devdk.socialmedia.feature_chat.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.BaseUrl
import com.devdk.socialmedia.feature_chat.data.remote.dto.response.ChatFollowingResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatApi {

    @POST("api/chat/online")
    suspend fun userOnline() : BasicApiResponse<Unit>

    @DELETE("api/chat/offline")
    suspend fun userOffline() : BasicApiResponse<Unit>

    @GET("api/chat/getFollowingForChat")
    suspend fun getFollowingsForChat() : BasicApiResponse<List<ChatFollowingResponse>>

    companion object {
        const val BASE_URL = BaseUrl.CHAT_BASE_URL
    }

}