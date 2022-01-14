package com.devdk.socialmedia.feature_chat.data.remote

import com.devdk.socialmedia.feature_chat.data.remote.dto.web_socket.WsClientMessage
import com.devdk.socialmedia.feature_chat.data.remote.dto.web_socket.WsServerMessage
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

interface ChatService {

    @Receive
    fun observeEvents() : ReceiveChannel<WebSocket.Event>

    @Send
    fun sendMessage(wsClientMessage: WsClientMessage)

    @Receive
    fun observeMessages() : ReceiveChannel<WsServerMessage>
}