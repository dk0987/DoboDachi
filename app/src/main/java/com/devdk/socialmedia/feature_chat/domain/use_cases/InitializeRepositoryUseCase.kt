package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository

class InitializeRepositoryUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke() {
        chatRepository.initialize()
    }
}