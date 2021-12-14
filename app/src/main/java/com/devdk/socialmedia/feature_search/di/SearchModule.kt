package com.devdk.socialmedia.feature_search.di

import android.content.SharedPreferences
import com.devdk.socialmedia.feature_auth.data.remote.AuthApi
import com.devdk.socialmedia.feature_auth.data.repository.AuthRepositoryImpl
import com.devdk.socialmedia.feature_auth.domain.repository.AuthRepository
import com.devdk.socialmedia.feature_auth.domain.use_cases.RegisterUseCase
import com.devdk.socialmedia.feature_auth.presentation.util.Validation
import com.devdk.socialmedia.feature_search.data.remote.SearchApi
import com.devdk.socialmedia.feature_search.data.repository.SearchRepositoryImpl
import com.devdk.socialmedia.feature_search.domain.repository.SearchRepository
import com.devdk.socialmedia.feature_search.domain.use_case.SearchUserUseCase
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
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchApi(okHttpClient: OkHttpClient) : SearchApi {
        return Retrofit.Builder()
            .baseUrl(SearchApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(api: SearchApi) : SearchRepository {
        return SearchRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideSearchUserUseCase(searchRepository: SearchRepository) : SearchUserUseCase {
        return SearchUserUseCase(searchRepository)
    }
}