package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.domain.modal.ChatFollowings
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository

class GetFollowingsForChatUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke() : Resource<List<ChatFollowings>> {
        return when(val result = chatRepository.getFollowingsForChat()){
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}