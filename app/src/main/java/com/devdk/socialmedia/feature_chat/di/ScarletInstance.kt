package com.devdk.socialmedia.feature_chat.di

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.feature_chat.data.remote.ChatService
import com.devdk.socialmedia.feature_chat.data.remote.util.CustomGsonAdapter
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import okhttp3.OkHttpClient


object ScarletInstance {

    var current: ChatService? = null

    fun getNewInstance(client: OkHttpClient): ChatService {
        return Scarlet.Builder()
            .addMessageAdapterFactory(CustomGsonAdapter.Factory(Gson()))
            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
            .webSocketFactory(
                client.newWebSocketFactory("ws://192.168.197.141:8080/api/chat/webSocket")
            )
            .backoffStrategy(LinearBackoffStrategy(Const.RECONNECT_INTERVAL))
            .build()
            .create(ChatService::class.java)
            .also {
                current = it
            }
    }
}

