package com.devdk.socialmedia.feature_chat.di

import com.devdk.socialmedia.feature_chat.data.remote.ChatApi
import com.devdk.socialmedia.feature_chat.data.repository.ChatRepositoryImpl
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository
import com.devdk.socialmedia.feature_chat.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object ChatModule {

    @Provides
    @Singleton
    fun provideChatApi(okHttpClient: OkHttpClient) : ChatApi {
        return Retrofit.Builder()
            .baseUrl(ChatApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatRepository(api: ChatApi , okHttpClient: OkHttpClient) : ChatRepository {
        return ChatRepositoryImpl(api , okHttpClient)
    }

    @Provides
    @Singleton
    fun provideChatUseCase(chatRepository: ChatRepository) : ChatsUseCases {
        return ChatsUseCases(
            getChatsUseCase = GetChatsUseCase(chatRepository),
            getFollowingsForChatUseCase = GetFollowingsForChatUseCase(chatRepository),
            getMessageUseCase = GetMessageUseCase(chatRepository),
            initializeRepositoryUseCase = InitializeRepositoryUseCase(chatRepository),
            observeChatEventsUseCase = ObserveChatEventsUseCase(chatRepository),
            observeMessageUseCase = ObserveMessageUseCase(chatRepository),
            sendMessageUseCase = SendMessageUseCase(chatRepository),
            userOnlineUseCase = UserOnlineUseCase(chatRepository),
            userOfflineUseCase = UserOfflineUseCase(chatRepository)
        )
    }

}