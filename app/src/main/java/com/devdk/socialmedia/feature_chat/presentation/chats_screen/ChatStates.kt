package com.devdk.socialmedia.feature_chat.presentation.chats_screen

import com.devdk.socialmedia.feature_chat.domain.modal.Chat
import com.devdk.socialmedia.feature_chat.domain.modal.ChatFollowings
import com.devdk.socialmedia.feature_chat.domain.use_cases.GetFollowingsForChatUseCase

data class ChatStates(
    val followingsForChat : List<ChatFollowings> = emptyList() ,
    val chats : List<Chat> = emptyList() ,
    val isLoading : Boolean = false ,
    val endReached : Boolean = false
)
