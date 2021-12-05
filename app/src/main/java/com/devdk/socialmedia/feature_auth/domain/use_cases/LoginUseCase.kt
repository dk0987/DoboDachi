package com.devdk.socialmedia.feature_auth.domain.use_cases

import com.devdk.socialmedia.core.presentation.util.Error.Error
import com.devdk.socialmedia.core.presentation.util.Error.WEAK_PASSWORD
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_auth.domain.repository.AuthRepository
import com.devdk.socialmedia.feature_auth.presentation.util.Validation

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val validation: Validation
) {
    suspend operator fun invoke(email : String , password : String) : Error? {
        val emailError = validation.validateEmail(email)
        val passwordError = validation.validatePassword(password)
        val loginError = Error()
        if (emailError != null) {
            loginError.emailError = emailError
        }
        if (passwordError != null &&  !passwordError.contains(WEAK_PASSWORD)) {
            loginError.passwordError = passwordError
        }
        if (emailError == null && passwordError == null) {
            return when (val result = authRepository.loginUser(email, password)) {
                is Resource.Success -> {
                    Resource.Success(Unit)
                    null
                }
                is Resource.Error -> {
                    loginError.responseError = Resource.Error(result.message , Unit).toString()
                    loginError
                }
            }
        }
        return loginError
    }
}