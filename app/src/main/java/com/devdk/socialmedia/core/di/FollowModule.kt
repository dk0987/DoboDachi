package com.devdk.socialmedia.core.di

import com.devdk.socialmedia.core.data.remote.FollowApi
import com.devdk.socialmedia.core.data.repository.FollowRepositoryImpl
import com.devdk.socialmedia.core.domain.repository.FollowRepository
import com.devdk.socialmedia.core.domain.use_case.FollowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FollowModule {

    @Provides
    @Singleton
    fun provideFollowApi(okHttpClient: OkHttpClient) : FollowApi {
        return Retrofit.Builder()
            .baseUrl(FollowApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FollowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFollowRepository(api: FollowApi) : FollowRepository {
        return FollowRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideFollowUserUseCase(followRepository: FollowRepository) : FollowUseCase {
        return FollowUseCase(followRepository)
    }

}