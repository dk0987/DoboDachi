package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.feature_chat.domain.modal.Message
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveMessageUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke() : Flow<Message>{
        return chatRepository.observeMessages()
    }
}