package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.domain.modal.Chat
import com.devdk.socialmedia.feature_chat.domain.modal.Message
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository

class GetMessageUseCase(
    private val chatRepository: ChatRepository
){
    suspend operator fun invoke(chatId : String , page : Int) : Resource<List<Message>> {
        return when(val result = chatRepository.getMessages(chatId , page)){
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}