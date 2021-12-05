package com.devdk.socialmedia.feature_auth.domain.use_cases

import com.devdk.socialmedia.core.presentation.util.Error.Error
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_auth.domain.modal.RegisterUser
import com.devdk.socialmedia.feature_auth.domain.repository.AuthRepository
import com.devdk.socialmedia.feature_auth.presentation.util.Validation

class RegisterUseCase(
    private val authRepository: AuthRepository,
    private val validation: Validation
) {
    suspend operator fun invoke(registerUser: RegisterUser): Error? {
        val userNameError = validation.validateUsername(registerUser.username)
        val emailError = validation.validateEmail(registerUser.email)
        val passwordError = validation.validatePassword(registerUser.password)
        val registerError  = Error()
        if (userNameError != null) {
            registerError.userNameError = userNameError
        }
        if (emailError != null) {
            registerError.emailError = emailError
        }
        if (passwordError != null) {
            registerError.passwordError = passwordError
        }
        if (userNameError == null && emailError == null && passwordError == null) {
            return when (val result = authRepository.registerUser(registerUser)) {
                is Resource.Success -> {
                    Resource.Success(Unit)
                    null
                }
                is Resource.Error -> {
                    registerError.responseError = result.message
                    registerError
                }
            }
        }
        return registerError
    }

}