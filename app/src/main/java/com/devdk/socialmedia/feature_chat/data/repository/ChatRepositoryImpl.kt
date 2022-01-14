package com.devdk.socialmedia.feature_chat.data.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.data.remote.ChatApi
import com.devdk.socialmedia.feature_chat.data.remote.ChatService
import com.devdk.socialmedia.feature_chat.data.remote.dto.response.MessageResponse
import com.devdk.socialmedia.feature_chat.data.remote.dto.web_socket.WsClientMessage
import com.devdk.socialmedia.feature_chat.di.ScarletInstance
import com.devdk.socialmedia.feature_chat.domain.modal.Chat
import com.devdk.socialmedia.feature_chat.domain.modal.ChatFollowings
import com.devdk.socialmedia.feature_chat.domain.modal.Message
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.OkHttpClient
import retrofit2.HttpException
import java.io.IOException

class ChatRepositoryImpl(
    private val chatApi: ChatApi ,
    private val okHttpClient : OkHttpClient
) : ChatRepository {

    private var chatService : ChatService? = null

    override fun initialize() {
        chatService = ScarletInstance.getNewInstance(okHttpClient)
    }

    override suspend fun userOnline(): Resource<Unit> {
        val response = chatApi.userOnline()
        return try {
            if (response.successful) {
                Resource.Success(Unit)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun userOffline(): Resource<Unit> {
        val response = chatApi.userOffline()
        return try {
            if (response.successful) {
                Resource.Success(Unit)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun getFollowingsForChat(): Resource<List<ChatFollowings>> {
        val response = chatApi.getFollowingsForChat()
        return try {
            if (response.successful) {
                val followings = response.data?.map { chatFollowingResponse ->
                    ChatFollowings(
                        userId = chatFollowingResponse.userId,
                        userProfileUrl = chatFollowingResponse.userProfileUrl,
                        username = chatFollowingResponse.username,
                        isOnline = chatFollowingResponse.isOnline
                    )
                }
                Resource.Success(followings)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun getChats(page: Int): Resource<List<Chat>> {
        val response = chatApi.getChats(page)
        return try {
            if (response.successful) {
                val followings = response.data?.map { chatResponse ->
                    Chat(
                        chatIds = chatResponse.chatIds ,
                        remoteUserUsername = chatResponse.remoteUserUsername,
                        remoteUserUserProfileUrl = chatResponse.remoteUserUserProfileUrl,
                        lastMessage = chatResponse.lastMessage,
                        remoteUserId = chatResponse.remoteUserId
                    )
                }
                Resource.Success(followings)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun getMessages(chatId: String, page: Int) : Resource<List<Message>> {
        val response = chatApi.getMessages(chatId , page)
        return try {
            if (response.successful) {
                val followings = response.data?.map { messageResponse ->
                    Message(
                        messageId = messageResponse.messageId,
                        message = messageResponse.message,
                        userId = messageResponse.userId,
                        timeStamp = messageResponse.timeStamp,
                    )
                }
                Resource.Success(followings)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override fun observeChatEvents(): Flow<WebSocket.Event> {
        return chatService?.observeEvents()
            ?.receiveAsFlow() ?: emptyFlow()
    }

    override fun observeMessages(): Flow<Message> {
        return chatService?.observeMessages()
            ?.receiveAsFlow()
            ?.map { it.toMessage() } ?: emptyFlow()
    }

    override fun sendMessage(toId: String, text: String, chatId: String) {
        chatService?.sendMessage(
            WsClientMessage(
                toId = toId ,
                message = text ,
                chatId = chatId
            )
        )
    }
}

