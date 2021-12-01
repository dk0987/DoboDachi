package com.devdk.socialmedia.feature_auth.di

import com.devdk.socialmedia.feature_auth.presentation.util.Validation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideValidation() : Validation {
        return Validation()
    }
}