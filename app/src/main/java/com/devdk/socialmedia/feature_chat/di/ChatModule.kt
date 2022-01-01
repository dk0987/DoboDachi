package com.devdk.socialmedia.feature_chat.di

import com.devdk.socialmedia.feature_chat.data.remote.ChatApi
import com.devdk.socialmedia.feature_chat.data.repository.ChatRepositoryImpl
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository
import com.devdk.socialmedia.feature_chat.domain.use_cases.UserOfflineUseCase
import com.devdk.socialmedia.feature_chat.domain.use_cases.UserOnlineUseCase
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
    fun provideChatRepository(api: ChatApi) : ChatRepository {
        return ChatRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserOnlineUseCase(chatRepository: ChatRepository) : UserOnlineUseCase {
        return UserOnlineUseCase(chatRepository)
    }

    @Provides
    @Singleton
    fun provideUserOfflineUseCase(chatRepository: ChatRepository) : UserOfflineUseCase {
        return UserOfflineUseCase(chatRepository)
    }

}