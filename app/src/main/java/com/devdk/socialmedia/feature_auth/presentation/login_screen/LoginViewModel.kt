package com.devdk.socialmedia.feature_auth.presentation.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_auth.domain.use_cases.LoginUseCase
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _eMailTextFieldState = mutableStateOf(TextFieldStates())
    val eMailTextFieldState : State<TextFieldStates> = _eMailTextFieldState

    private val _passwordTextFieldState = mutableStateOf(TextFieldStates())
    val passwordTextFieldState : State<TextFieldStates> = _passwordTextFieldState

    private val _isLoading = mutableStateOf(false)
    val isLoading : State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
        viewModelScope.launch {
            _isLoading.value = true
            val email = eMailTextFieldState.value.text
            val password = passwordTextFieldState.value.text
            val error = loginUseCase(email, password)
            _isLoading.value = false
            if (error == null){
                _eventFlow.emit(
                    UiEvent.Navigate(Routes.Feed.screen , "LoggedIn Successfully")
                )
            }
            else{
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












