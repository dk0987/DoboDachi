package com.devdk.socialmedia.feature_chat.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.BaseUrl
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.feature_chat.data.remote.dto.response.ChatFollowingResponse
import com.devdk.socialmedia.feature_chat.data.remote.dto.response.ChatResponse
import com.devdk.socialmedia.feature_chat.data.remote.dto.response.MessageResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {

    @POST("api/chat/online")
    suspend fun userOnline() : BasicApiResponse<Unit>

    @DELETE("api/chat/offline")
    suspend fun userOffline() : BasicApiResponse<Unit>

    @GET("api/chat/getFollowingForChat")
    suspend fun getFollowingsForChat() : BasicApiResponse<List<ChatFollowingResponse>>

    @GET("api/chat/chats")
    suspend fun getChats(
        @Query("page") page : Int ,
        @Query("pageSize") pageSize : Int = Const.POST_PAGE_SIZE
    ) : BasicApiResponse<List<ChatResponse>>

    @GET("api/chat/messages")
    suspend fun getMessages(
        @Query("chatID") chatId : String ,
        @Query("page") page : Int ,
        @Query("pageSize") pageSize : Int = Const.POST_PAGE_SIZE
    ) : BasicApiResponse<List<MessageResponse>>

    companion object {
        const val BASE_URL = BaseUrl.CHAT_BASE_URL
    }

}