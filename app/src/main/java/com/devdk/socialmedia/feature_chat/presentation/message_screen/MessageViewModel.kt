package com.devdk.socialmedia.feature_chat.presentation.message_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(

) : ViewModel() {

    private val _messageTextFieldState = mutableStateOf(TextFieldStates())
    val messageTextFieldState : State<TextFieldStates> = _messageTextFieldState

    fun onEvent(event : MessageEvents) {
        when (event) {
            is MessageEvents.Message -> {
                _messageTextFieldState.value = messageTextFieldState.value.copy(
                    text = event.message ,
                )
            }
        }
    }
}