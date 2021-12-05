package com.devdk.socialmedia.feature_auth.presentation.registration_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.states.StandardTextFieldStates
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_auth.domain.modal.RegisterUser
import com.devdk.socialmedia.feature_auth.domain.use_cases.RegisterUseCase
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _usernameTextFieldState = mutableStateOf(StandardTextFieldStates())
    val usernameTextFieldState : State<StandardTextFieldStates> = _usernameTextFieldState

    private val _eMailTextFieldState = mutableStateOf(StandardTextFieldStates())
    val eMailTextFieldState : State<StandardTextFieldStates> = _eMailTextFieldState

    private val _passwordTextFieldState = mutableStateOf(StandardTextFieldStates())
    val passwordTextFieldState : State<StandardTextFieldStates> = _passwordTextFieldState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
        viewModelScope.launch {
            val userName = usernameTextFieldState.value.text
            val email = eMailTextFieldState.value.text
            val password = passwordTextFieldState.value.text
            val error = registerUseCase(
                RegisterUser(
                username = userName ,
                email = email ,
                password = password
            ))
            if (error == null){
                _eventFlow.emit(
                    UiEvent.Navigate(Routes.Feed.screen)
                )
            }
            else{
                if (error.userNameError != null){
                    _usernameTextFieldState.value = usernameTextFieldState.value.copy(
                        isError = true ,
                        error = error.userNameError!!
                    )
                }
                else{
                    _usernameTextFieldState.value = usernameTextFieldState.value.copy(
                        isError = false ,
                    )
                }
                if (error.emailError != null){
                    _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                        isError = true ,
                        error = error.emailError!!
                    )
                }
                else{
                    _eMailTextFieldState.value = eMailTextFieldState.value.copy(
                        isError = false ,
                    )
                }
                if (error.passwordError != null){
                    _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                        isError = true ,
                        error = error.passwordError!!
                    )
                }
                else{
                    _passwordTextFieldState.value = passwordTextFieldState.value.copy(
                        isError = false ,
                    )
                }
                if (error.responseError != null){
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(error.responseError)
                    )
                }
            }
        }
    }

}












