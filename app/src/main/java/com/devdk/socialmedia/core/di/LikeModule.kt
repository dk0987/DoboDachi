package com.devdk.socialmedia.core.di

import com.devdk.socialmedia.core.data.remote.LikeApi
import com.devdk.socialmedia.core.data.repository.LikeRepositoryImpl
import com.devdk.socialmedia.core.domain.repository.LikeRepository
import com.devdk.socialmedia.core.domain.use_case.GetLikesUseCase
import com.devdk.socialmedia.core.domain.use_case.LikeUseCase
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
object LikeModule {

    @Provides
    @Singleton
    fun provideLikeApi(okHttpClient: OkHttpClient) : LikeApi {
        return Retrofit.Builder()
            .baseUrl(LikeApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LikeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLikeRepository(api: LikeApi) : LikeRepository {
        return LikeRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideLikeUserUseCase(likeRepository: LikeRepository) : LikeUseCase {
        return LikeUseCase(likeRepository)
    }

    @Provides
    @Singleton
    fun provideGetLikesUseCase(likeRepository: LikeRepository) : GetLikesUseCase {
        return GetLikesUseCase(likeRepository)
    }
}