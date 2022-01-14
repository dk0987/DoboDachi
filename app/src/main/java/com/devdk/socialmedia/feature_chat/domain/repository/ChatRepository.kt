package com.devdk.socialmedia.feature_chat.domain.repository

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.domain.modal.Chat
import com.devdk.socialmedia.feature_chat.domain.modal.ChatFollowings
import com.devdk.socialmedia.feature_chat.domain.modal.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun userOnline() : Resource<Unit>

    suspend fun userOffline() : Resource<Unit>

    suspend fun getFollowingsForChat() : Resource<List<ChatFollowings>>

    suspend fun getChats(page : Int) : Resource<List<Chat>>

    suspend fun getMessages(chatId : String , page : Int ) : Resource<List<Message>>

    fun initialize()

    fun observeChatEvents() : Flow<WebSocket.Event>

    fun observeMessages() : Flow<Message>

    fun sendMessage(toId : String , text : String , chatId : String)
}