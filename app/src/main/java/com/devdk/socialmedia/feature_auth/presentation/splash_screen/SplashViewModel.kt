package com.devdk.socialmedia.feature_auth.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_auth.domain.use_cases.AuthenticateUseCase
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase
) : ViewModel()
{
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        authenticate()
    }

    private fun authenticate(){
        viewModelScope.launch {
            val authenticated = authenticateUseCase.invoke()
            if (authenticated){
                _eventFlow.emit(
                    UiEvent.Navigate(Routes.Feed.screen , null)
                )
            }
            else
                _eventFlow.emit(
                    UiEvent.Navigate(Routes.Login.screen , null)
                )
        }
    }
}