package com.devdk.socialmedia.feature_post.di

import android.content.SharedPreferences
import com.devdk.socialmedia.core.data.remote.FollowApi
import com.devdk.socialmedia.core.data.repository.FollowRepositoryImpl
import com.devdk.socialmedia.core.domain.repository.FollowRepository
import com.devdk.socialmedia.core.domain.use_case.FollowUseCase
import com.devdk.socialmedia.feature_post.data.remote.PostApi
import com.devdk.socialmedia.feature_post.data.repository.PostRepositoryImpl
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import com.devdk.socialmedia.feature_post.domain.useCases.GetPostUseCase
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
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(okHttpClient: OkHttpClient) : PostApi {
        return Retrofit.Builder()
            .baseUrl(PostApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostApi) : PostRepository {
        return PostRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideGetPostUseCase(postRepository: PostRepository , sharedPreferences: SharedPreferences) : GetPostUseCase {
        return GetPostUseCase(postRepository , sharedPreferences)
    }
}