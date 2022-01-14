package com.devdk.socialmedia.feature_chat.data.remote.util

import com.devdk.socialmedia.feature_chat.data.remote.dto.web_socket.WsClientMessage
import com.devdk.socialmedia.feature_chat.data.remote.dto.web_socket.WsServerMessage
import com.google.gson.Gson
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import java.lang.reflect.Type

class CustomGsonAdapter<T> private constructor(
    private val gson: Gson
) : MessageAdapter<T>{
    override fun fromMessage(message: Message): T {
        val payload = when(message){
            is Message.Text -> message.value
            is Message.Bytes -> ""
        }
        val delimiterIndex = payload.indexOf("#")
        if (delimiterIndex == -1){
            return Any() as T
        }
        val type = payload.substring(0 , delimiterIndex).toIntOrNull()
        if (type == null){
            println("Invalid Format")
            return Any() as T
        }
        val json = payload.substring(delimiterIndex + 1 , payload.length)
        val clazz = when(type){
            WebSocketObject.MESSAGE.ordinal -> WsServerMessage::class.java
            else -> Any :: class.java
        }
        return gson.fromJson(json , clazz) as T
    }

    override fun toMessage(data: T): Message {
        val clazz = when(data) {
            is WsClientMessage -> WsClientMessage::class.java
            else -> Any::class.java
        }
        val type = when(data) {
            is WsClientMessage -> WebSocketObject.MESSAGE.ordinal
            else -> -1
        }
        val socketString = "$type#${gson.toJson(data, clazz)}"
        return Message.Text(socketString)
    }

    class Factory(
        private val gson: Gson = DEFAULT_GSON
    ) : MessageAdapter.Factory {

        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            return CustomGsonAdapter<Any>(gson)
        }

        companion object {
            private val DEFAULT_GSON = Gson()
        }
    }
}