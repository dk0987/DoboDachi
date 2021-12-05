package com.devdk.socialmedia.feature_auth.domain.use_cases

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : Boolean{
        return when(authRepository.authenticate()){
            is Resource.Success -> {
                true
            }
            is Resource.Error -> {
                false
            }
        }
    }
}