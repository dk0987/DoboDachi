package com.devdk.socialmedia.feature_post.di

import android.content.SharedPreferences
import com.devdk.socialmedia.feature_post.data.remote.PostApi
import com.devdk.socialmedia.feature_post.data.repository.PostRepositoryImpl
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import com.devdk.socialmedia.feature_post.domain.useCases.*
import com.google.gson.Gson
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
    fun providePostRepository(api: PostApi , gson: Gson) : PostRepository {
        return PostRepositoryImpl(api , gson)
    }
    @Provides
    @Singleton
    fun provideGetPostUseCase(postRepository: PostRepository , sharedPreferences: SharedPreferences) : PostUseCases {
        return PostUseCases(
            getPostUseCase = GetPostUseCase(postRepository, sharedPreferences) ,
            addPostUseCase = AddPostUseCase(postRepository),
            deletePostUseCase = DeletePostUseCase(postRepository),
            getPostByIdUseCase = GetPostByIdUseCase(postRepository , sharedPreferences),
            getProfilePic = GetProfilePic(postRepository),
            getPostForUserUseCase = GetPostForUserUseCase(postRepository)
        )
    }

}