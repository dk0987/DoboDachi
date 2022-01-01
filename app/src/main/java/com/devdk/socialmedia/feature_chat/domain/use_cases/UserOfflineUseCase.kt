package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository

class UserOfflineUseCase(
    private val chatRepository: ChatRepository
){
    suspend operator fun invoke() : String? {
        return when(val result = chatRepository.userOffline()){
            is Resource.Success -> null
            is Resource.Error -> {
                result.message
            }
        }
    }
}