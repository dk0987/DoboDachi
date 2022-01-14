package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.domain.modal.Chat
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository

class GetChatsUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(page : Int ) : Resource<List<Chat>>{
        return when(val result = chatRepository.getChats(page)){
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}