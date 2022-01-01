package com.devdk.socialmedia.feature_chat.domain.repository

import com.devdk.socialmedia.core.util.Resource

interface ChatRepository {

    suspend fun userOnline() : Resource<Unit>

    suspend fun userOffline() : Resource<Unit>
}