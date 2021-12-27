package com.devdk.socialmedia.core.data.repository

import com.devdk.socialmedia.core.data.remote.FollowApi
import com.devdk.socialmedia.core.data.remote.dto.request.FollowRequest
import com.devdk.socialmedia.core.domain.modal.PersonList
import com.devdk.socialmedia.core.domain.repository.FollowRepository
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import retrofit2.HttpException
import java.io.IOException

class FollowRepositoryImpl(
    private val followApi: FollowApi
) : FollowRepository{

    override suspend fun follow(followingId: String): Resource<Unit> {
        return try {
            val response = followApi.follow(FollowRequest(
                userId = followingId
            ))
            if (response.successful){
                Resource.Success(Unit)
            }
            else{
                Resource.Error(response.message)
            }
        }
        catch (e : IOException){
            Resource.Error(Const.SOMETHING_WRONG)
        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }

    }

    override suspend fun unfollow(followingId: String): Resource<Unit> {
        return try {
            val response = followApi.unfollow(followingId)
            if (response.successful){
                Resource.Success(Unit)
            }
            else{
                Resource.Error(response.message)
            }
        }
        catch (e : IOException){
            Resource.Error(Const.SOMETHING_WRONG)
        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun getFollowers(userId: String): Resource<List<PersonList>> {
        return try {
            val response = followApi.getFollowers(userId)
            if (response.successful){
                val followers = response.data?.map { followResponse ->
                    PersonList(
                        userId = followResponse.userId,
                        userProfile = followResponse.profilePicUrl,
                        username = followResponse.username,
                        isFollowing = followResponse.isFollowing,
                        isUser = followResponse.isUser,
                    )
                }
                Resource.Success(followers)
            }
            else{
                Resource.Error(response.message)
            }
        }
        catch (e : IOException){
            Resource.Error(Const.SOMETHING_WRONG)
        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }

    override suspend fun getFollowings(userId: String): Resource<List<PersonList>> {
        return try {
            val response = followApi.getFollowings(userId)
            if (response.successful){
                val followings = response.data?.map { followResponse ->
                    PersonList(
                        userId = followResponse.userId,
                        userProfile = followResponse.profilePicUrl,
                        username = followResponse.username,
                        isFollowing = followResponse.isFollowing,
                        isUser = followResponse.isUser,
                    )
                }
                Resource.Success(followings)
            }
            else{
                Resource.Error(response.message)
            }
        }
        catch (e : IOException){
            Resource.Error(Const.SOMETHING_WRONG)
        }
        catch (e : HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }
}