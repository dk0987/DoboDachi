package com.devdk.socialmedia.feature_chat.presentation.chats_screen

import android.widget.SearchView
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.FollowUseCase
import com.devdk.socialmedia.core.domain.use_case.GetFollowingUseCase
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.util.DefaultPagination
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_chat.domain.modal.Chat
import com.devdk.socialmedia.feature_chat.domain.modal.Message
import com.devdk.socialmedia.feature_chat.domain.use_cases.ChatsUseCases
import com.devdk.socialmedia.feature_chat.domain.use_cases.GetFollowingsForChatUseCase
import com.devdk.socialmedia.feature_chat.presentation.message_screen.MessageViewModel
import com.devdk.socialmedia.feature_search.domain.use_case.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatsUseCases: ChatsUseCases
): ViewModel() , SearchView.OnQueryTextListener {

    private val _chatSearchTextFieldState = mutableStateOf(TextFieldStates())
    val chatSearchTextFieldState : State<TextFieldStates> = _chatSearchTextFieldState

    private val _chatState = mutableStateOf(ChatStates())
    val chatState : State<ChatStates> = _chatState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFLow = _eventFlow.asSharedFlow()

    private val pagination = DefaultPagination<Chat>(
        onLoadUpdate = { isLoading ->
            _chatState.value = chatState.value.copy(
                isLoading = isLoading
            )
        } ,
        onRequest = { page ->
            chatsUseCases.getChatsUseCase(page = page )
        } ,
        onSuccess = { chat ->
            _chatState.value = chatState.value.copy(
                chats = _chatState.value.chats + chat ,
                endReached = chat.isEmpty()
            )
        } ,
        onError = { error ->
            _eventFlow.emit(
                UiEvent.ShowSnackBar(error)
            )

        }
    )

    init {
        getFollowings()
        loadNextChats()
    }

    fun onEvent(event : ChatEvents) {

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModelScope.launch {
            newText?.let { query ->
                _chatSearchTextFieldState.value = chatSearchTextFieldState.value.copy(
                    text = query
                )
            }
        }
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    private fun getFollowings() {
        viewModelScope.launch {
            when(val result = chatsUseCases.getFollowingsForChatUseCase()) {
                is Resource.Success -> {
                    _chatState.value = chatState.value.copy(
                        followingsForChat = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    fun loadNextChats(){
        viewModelScope.launch {
            pagination.loadItems()
        }
    }


}