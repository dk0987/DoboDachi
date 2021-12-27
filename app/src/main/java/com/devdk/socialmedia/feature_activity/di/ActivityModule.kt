package com.devdk.socialmedia.feature_activity.di

import com.devdk.socialmedia.feature_activity.data.remote.ActivityApi
import com.devdk.socialmedia.feature_activity.data.repository.ActivityRepositoryImpl
import com.devdk.socialmedia.feature_activity.domain.repository.ActivityRepository
import com.devdk.socialmedia.feature_activity.domain.use_case.GetActivitiesUseCase
import com.devdk.socialmedia.feature_post.data.remote.CommentApi
import com.devdk.socialmedia.feature_post.data.repository.CommentRepositoryImpl
import com.devdk.socialmedia.feature_post.domain.repository.CommentRepository
import com.devdk.socialmedia.feature_post.domain.useCases.CommentUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.DeleteCommentUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.GetCommentsUseCase
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
object ActivityModule {

    @Provides
    @Singleton
    fun provideActivityApi(okHttpClient: OkHttpClient) : ActivityApi {
        return Retrofit.Builder()
            .baseUrl(ActivityApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideActivityRepository(api: ActivityApi) : ActivityRepository {
        return ActivityRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetActivitiesUseCase(activityRepository: ActivityRepository) : GetActivitiesUseCase {
        return GetActivitiesUseCase(activityRepository)
    }
}