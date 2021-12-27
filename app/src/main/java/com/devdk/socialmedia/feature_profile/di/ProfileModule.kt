package com.devdk.socialmedia.feature_profile.di

import android.content.SharedPreferences
import com.devdk.socialmedia.feature_post.data.remote.PostApi
import com.devdk.socialmedia.feature_post.data.repository.PostRepositoryImpl
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import com.devdk.socialmedia.feature_post.domain.useCases.*
import com.devdk.socialmedia.feature_profile.data.remote.ProfileApi
import com.devdk.socialmedia.feature_profile.data.repository.ProfileRepositoryImpl
import com.devdk.socialmedia.feature_profile.domain.repository.ProfileRepository
import com.devdk.socialmedia.feature_profile.domain.useCases.GetProfileUseCase
import com.devdk.socialmedia.feature_profile.domain.useCases.UpdateProfileUseCase
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
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(okHttpClient: OkHttpClient) : ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi , gson: Gson ) : ProfileRepository {
        return ProfileRepositoryImpl(api , gson)
    }

    @Provides
    @Singleton
    fun provideGetProfileUseCase(profileRepository: ProfileRepository , sharedPreferences: SharedPreferences) : GetProfileUseCase {
        return GetProfileUseCase(profileRepository )
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(profileRepository: ProfileRepository) : UpdateProfileUseCase {
        return UpdateProfileUseCase(profileRepository)
    }

}