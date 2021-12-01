package com.devdk.socialmedia.feature_auth.presentation.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.presentation.states.StandardTextFieldStates
import com.devdk.socialmedia.feature_auth.presentation.util.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validation: Validation
) : ViewModel() {

    private val _eMailTextFieldState = mutableStateOf(StandardTextFieldStates())
    val eMailTextFieldState : State<StandardTextFieldStates> = _eMailTextFieldState

    private val _passwordTextFieldState = mutableStateOf(StandardTextFieldStates())
    val passwordTextFieldState : State<StandardTextFieldStates> = _passwordTextFieldState

    fun onEvent(events: LoginEvents){
        when(events){
            is LoginEvents.Email -> {
                _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                    text = events.email ,
                )
            }
            is LoginEvents.Password -> {
                _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                    text = events.password ,
                )
            }
            is LoginEvents.Toggle -> {
                _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                    isPasswordVisible = !passwordTextFieldState.value.isPasswordVisible ,
                )
            }
            is LoginEvents.Login -> {
                login()
            }
        }
    }

    private fun login() {
        val emailError = validation.validateEmail(eMailTextFieldState.value.text)
        val passwordError = validation.validatePassword(passwordTextFieldState.value.text)
        if (emailError != null){
            _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                isError = true,
                error = emailError
            )
        }
        else {
            _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                isError = false,
            )
        }
        if (passwordError?.contains(Error.FIELD_EMPTY) == true || passwordError?.contains(Error.SHORT_LENGTH) == true){
            _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                isError = true,
                error = passwordError
            )
        }
        else{
            _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                isError = false,
            )
        }
        if (!eMailTextFieldState.value.isError && !passwordTextFieldState.value.isError){

        }
    }

}












