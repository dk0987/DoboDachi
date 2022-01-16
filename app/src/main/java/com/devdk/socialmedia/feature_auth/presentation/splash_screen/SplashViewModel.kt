package com.devdk.socialmedia.feature_auth.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_auth.domain.use_cases.AuthenticateUseCase
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_chat.domain.use_cases.ChatsUseCases
import com.devdk.socialmedia.feature_chat.domain.use_cases.UserOnlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase ,
    private val chatsUseCases: ChatsUseCases
) : ViewModel()
{
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        authenticate()
    }

    private fun authenticate(){
        viewModelScope.launch {
            val authenticated = authenticateUseCase()
            if (authenticated){
                chatsUseCases.userOnlineUseCase()
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