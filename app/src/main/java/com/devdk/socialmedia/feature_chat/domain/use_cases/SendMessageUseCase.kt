package com.devdk.socialmedia.feature_chat.domain.use_cases

import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository

class SendMessageUseCase(
    private val chatRepository: ChatRepository
) {
   operator fun invoke(toId : String , message : String , chatId : String) {
       if (message.isBlank()){
           return
       }
       chatRepository.sendMessage(toId , message , chatId)
   }
}