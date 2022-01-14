package com.devdk.socialmedia.feature_chat.data.remote.dto.web_socket

data class WsClientMessage(
    val toId : String ,
    val message : String ,
    val chatId : String
)