package com.devdk.socialmedia.feature_chat.data.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_chat.data.remote.ChatApi
import com.devdk.socialmedia.feature_chat.domain.repository.ChatRepository
import retrofit2.HttpException
import java.io.IOException

class ChatRepositoryImpl(
    private val chatApi: ChatApi
) : ChatRepository {

    override suspend fun userOnline(): Resource<Unit> {
        val response = chatApi.userOnline()
        return try {
            if (response.successful) {
                Resource.Success(Unit)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun userOffline(): Resource<Unit> {
        val response = chatApi.userOffline()
        return try {
            if (response.successful) {
                Resource.Success(Unit)
            }
            else {
                Resource.Error(response.message)
            }
        }
        catch (e : IOException) {
            Resource.Error(Const.SOMETHING_WRONG)

        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }
}