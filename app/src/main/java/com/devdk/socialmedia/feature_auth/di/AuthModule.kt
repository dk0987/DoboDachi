package com.devdk.socialmedia.feature_auth.di

import android.content.SharedPreferences
import com.devdk.socialmedia.feature_auth.data.remote.AuthApi
import com.devdk.socialmedia.feature_auth.data.remote.AuthApi.Companion.BASE_URL
import com.devdk.socialmedia.feature_auth.data.repository.AuthRepositoryImpl
import com.devdk.socialmedia.feature_auth.domain.repository.AuthRepository
import com.devdk.socialmedia.feature_auth.domain.use_cases.AuthenticateUseCase
import com.devdk.socialmedia.feature_auth.domain.use_cases.LoginUseCase
import com.devdk.socialmedia.feature_auth.domain.use_cases.RegisterUseCase
import com.devdk.socialmedia.feature_auth.presentation.util.Validation
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
object AuthModule {

    @Provides
    @Singleton
    fun provideValidation() : Validation {
        return Validation()
    }

    @Provides
    @Singleton
    fun provideAuthApi(okHttpClient: OkHttpClient) : AuthApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi , sharedPreferences: SharedPreferences) : AuthRepository{
        return AuthRepositoryImpl(api , sharedPreferences)
    }
    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository , validation: Validation) : RegisterUseCase{
        return RegisterUseCase(authRepository , validation)
    }

    @Provides
    @Singleton
    fun provideAuthenticateUseCase(authRepository: AuthRepository ) : AuthenticateUseCase{
        return AuthenticateUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository , validation: Validation ) : LoginUseCase{
        return LoginUseCase(authRepository , validation)
    }
}
