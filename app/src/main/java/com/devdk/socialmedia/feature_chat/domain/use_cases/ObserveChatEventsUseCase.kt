package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

class ObserveChatEventsUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke() : Flow<WebSocket.Event> {
        return chatRepository.observeChatEvents()
    }
}