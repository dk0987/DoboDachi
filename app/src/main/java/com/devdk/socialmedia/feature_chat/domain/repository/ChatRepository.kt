package com.devdk.socialmedia.feature_chat.domain.repository

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.domain.modal.ChatFollowings

interface ChatRepository {

    suspend fun userOnline() : Resource<Unit>

    suspend fun userOffline() : Resource<Unit>

    suspend fun getFollowingsForChat() : Resource<List<ChatFollowings>>
}