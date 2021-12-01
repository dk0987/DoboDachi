package com.devdk.socialmedia.feature_auth.presentation.registration_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devdk.socialmedia.core.presentation.states.StandardTextFieldStates
import com.devdk.socialmedia.feature_auth.presentation.util.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validation: Validation
) : ViewModel() {

    private val _usernameTextFieldState = mutableStateOf(StandardTextFieldStates())
    val usernameTextFieldState : State<StandardTextFieldStates> = _usernameTextFieldState

    private val _eMailTextFieldState = mutableStateOf(StandardTextFieldStates())
    val eMailTextFieldState : State<StandardTextFieldStates> = _eMailTextFieldState

    private val _passwordTextFieldState = mutableStateOf(StandardTextFieldStates())
    val passwordTextFieldState : State<StandardTextFieldStates> = _passwordTextFieldState

    fun onEvent(events: RegisterEvents){
        when(events){
            is RegisterEvents.Username -> {
                _usernameTextFieldState.value = usernameTextFieldState.value.copy(
                    text = events.username ,
                )
            }
            is RegisterEvents.Email -> {
                _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                    text = events.eMail ,
                )
            }
            is RegisterEvents.Password -> {
                _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                    text = events.password ,
                )
            }
            is RegisterEvents.Toggle -> {
                _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                    isPasswordVisible = !passwordTextFieldState.value.isPasswordVisible ,
                )
            }
            is RegisterEvents.Register -> {
                register()
            }
        }
    }

    private fun register() {
        val usernameError = validation.validateUsername(usernameTextFieldState.value.text)
        val eMailError =validation.validateEmail(eMailTextFieldState.value.text)
        val passwordError =validation.validatePassword(passwordTextFieldState.value.text)
        if (usernameError != null){
            _usernameTextFieldState.value = usernameTextFieldState.value.copy(
                isError = true,
                error = usernameError
            )
        }
        else{
            _usernameTextFieldState.value = usernameTextFieldState.value.copy(
                isError = false,
            )
        }
        if (eMailError != null){
            _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                isError = true,
                error = eMailError
            )
        }
        else{
            _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                isError = false,
            )
        }
        if (passwordError != null){
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
        if (!eMailTextFieldState.value.isError && !passwordTextFieldState.value.isError && !usernameTextFieldState.value.isError){

        }
    }

}












