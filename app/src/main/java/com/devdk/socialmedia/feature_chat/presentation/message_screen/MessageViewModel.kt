package com.devdk.socialmedia.feature_chat.presentation.message_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.util.DefaultPagination
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_chat.domain.modal.Message
import com.devdk.socialmedia.feature_chat.domain.use_cases.ChatsUseCases
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatsUseCases: ChatsUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageTextFieldState = mutableStateOf(TextFieldStates())
    val messageTextFieldState : State<TextFieldStates> = _messageTextFieldState

    private val _messageState = mutableStateOf(MessageState())
    val messageState : State<MessageState> = _messageState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _messageUpdatedEvent = MutableSharedFlow<MessageUpdateEvent>(replay = 1)
    val messageReceived = _messageUpdatedEvent.asSharedFlow()

    val toUserID = savedStateHandle.get<String>("remoteUserId")!!


    private val pagination = DefaultPagination<Message>(
        onLoadUpdate = { isLoading ->
            _messageState.value = messageState.value.copy(
                isLoading = isLoading
            )
        } ,
        onRequest = { page ->
            savedStateHandle.get<String>("chatId")?.let { chatId ->
                println("ChatIds $chatId")
                chatsUseCases.getMessageUseCase(page = page , chatId = chatId )
            } ?: Resource.Error("")
        } ,
        onSuccess = { message ->
            _messageState.value = messageState.value.copy(
                messages = messageState.value.messages + message ,
                endReached = message.isEmpty()
            )
            viewModelScope.launch {
                _messageUpdatedEvent.emit(MessageUpdateEvent.MessagePageLoaded)
            }
        } ,
        onError = { error ->
            _eventFlow.emit(
                UiEvent.ShowSnackBar(error)
            )

        }
    )

    init {
        chatsUseCases.initializeRepositoryUseCase()
        loadNextMessages()
        observeChatEvents()
        observeChatMessage()
    }

    fun onEvent(event : MessageEvents) {
        when (event) {
            is MessageEvents.Message -> {
                _messageTextFieldState.value = messageTextFieldState.value.copy(
                    text = event.message ,
                )
            }
            is MessageEvents.SendMessage -> {
                sendMessage()
            }
        }
    }

    private fun observeChatMessage(){
        viewModelScope.launch {
            chatsUseCases.observeMessageUseCase()
                .collect { message ->
                    _messageState.value = messageState.value.copy(
                        messages = _messageState.value.messages + message
                    )
                    _messageUpdatedEvent.emit(MessageUpdateEvent.SingleMessageUpdate)
                }
        }
    }

    private fun observeChatEvents() {
        chatsUseCases.observeChatEventsUseCase()
            .onEach { event ->
                when (event) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        println("Connection was opened")
                    }
                    is WebSocket.Event.OnConnectionFailed -> {
                        println("Connection failed: ${event.throwable}")
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    fun loadNextMessages() {
        viewModelScope.launch {
            pagination.loadItems()
        }
    }


    private fun sendMessage() {
        if (messageTextFieldState.value.text.isBlank()) {
            return
        }
        val chatId = savedStateHandle.get<String>("chatId")
        chatsUseCases.sendMessageUseCase(toUserID, messageTextFieldState.value.text, chatId ?: "")
        _messageTextFieldState.value = TextFieldStates()
        viewModelScope.launch {
            _messageUpdatedEvent.emit(MessageUpdateEvent.MessageSent)
        }
    }


    sealed class MessageUpdateEvent {
        object SingleMessageUpdate: MessageUpdateEvent()
        object MessagePageLoaded: MessageUpdateEvent()
        object MessageSent: MessageUpdateEvent()
    }
}