package com.devdk.socialmedia.feature_chat.data.remote.dto.web_socket

import com.devdk.socialmedia.feature_chat.domain.modal.Message


data class WsServerMessage(
    val fromId : String ,
    val toId : String ,
    val message : String ,
    val timeStamp : Long ,
    val chatId : String
){
    fun toMessage(): Message {
        return Message(
            userId = fromId,
            message = message,
            timeStamp = timeStamp,
            messageId = ""
        )
    }
}
