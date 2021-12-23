package com.devdk.socialmedia.feature_post.di

import com.devdk.socialmedia.feature_post.data.remote.CommentApi
import com.devdk.socialmedia.feature_post.data.repository.CommentRepositoryImpl
import com.devdk.socialmedia.feature_post.domain.repository.CommentRepository
import com.devdk.socialmedia.feature_post.domain.useCases.*
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
object CommentModule {

    @Provides
    @Singleton
    fun provideCommentApi(okHttpClient: OkHttpClient) : CommentApi {
        return Retrofit.Builder()
            .baseUrl(CommentApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentRepository(api: CommentApi) : CommentRepository {
        return CommentRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetCommentUseCase(commentRepository: CommentRepository) : GetCommentsUseCase {
        return GetCommentsUseCase(commentRepository)
    }

    @Provides
    @Singleton
    fun provideCommentUseCase(commentRepository: CommentRepository) : CommentUseCase {
        return CommentUseCase(commentRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteCommentUseCase(commentRepository: CommentRepository) : DeleteCommentUseCase {
        return DeleteCommentUseCase(commentRepository)
    }
}