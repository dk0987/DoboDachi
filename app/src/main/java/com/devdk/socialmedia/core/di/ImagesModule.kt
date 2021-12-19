package com.devdk.socialmedia.core.di

import android.content.Context
import com.devdk.socialmedia.core.data.local.GetImages
import com.devdk.socialmedia.core.data.remote.LikeApi
import com.devdk.socialmedia.core.data.repository.ImageRepositoryImpl
import com.devdk.socialmedia.core.data.repository.LikeRepositoryImpl
import com.devdk.socialmedia.core.domain.repository.ImageRepository
import com.devdk.socialmedia.core.domain.repository.LikeRepository
import com.devdk.socialmedia.core.domain.use_case.GetImagesUseCase
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
object ImagesModule {

    @Provides
    @Singleton
    fun provideGetImages() : GetImages {
        return GetImages()
    }

    @Provides
    @Singleton
    fun provideImageRepository(getImages: GetImages) : ImageRepository {
        return ImageRepositoryImpl(getImages)
    }
    @Provides
    @Singleton
    fun provideGetImageUseCase(imageRepository : ImageRepository) : GetImagesUseCase {
        return GetImagesUseCase(imageRepository)
    }

}