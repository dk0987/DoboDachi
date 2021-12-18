package com.devdk.socialmedia.core.data.repository

import com.devdk.socialmedia.core.data.remote.LikeApi
import com.devdk.socialmedia.core.data.remote.dto.request.LikeRequest
import com.devdk.socialmedia.core.data.remote.dto.response.GetLikeResponse
import com.devdk.socialmedia.core.domain.modal.GetLikes
import com.devdk.socialmedia.core.domain.repository.LikeRepository
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.LikedOn
import com.devdk.socialmedia.core.util.Resource
import retrofit2.HttpException
import java.io.IOException

class LikeRepositoryImpl(
    private val likeApi: LikeApi
) : LikeRepository {

    override suspend fun like(parentId: String, likedOn : LikedOn): Resource<Unit> {
        return try {
            val response = likeApi.like(LikeRequest(
                parentId = parentId ,
                parentType = when (likedOn) {
                    is LikedOn.Post -> {
                        LikedOn.Post.likedOn
                    }
                    is LikedOn.Comment -> {
                        LikedOn.Comment.likedOn
                    }
                }
            ))
            if (response.successful){
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

    override suspend fun unLike(parentId: String): Resource<Unit> {
        return try {
            val response = likeApi.unlike(parentId)
            if (response.successful){
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

    override suspend fun getLikes(parentId: String): Resource<List<GetLikes>> {
        return try {
            val getLikes = arrayListOf<GetLikes>()
            val response = likeApi.getLikes(parentId)
            if (response.successful){
                response.data?.forEach { likes ->
                    getLikes.add(
                        GetLikes(
                            userId = likes.userID ,
                            userProfile = likes.userProfilePicUrl ,
                            isFollowing = likes.isFollowing,
                            username = likes.username,
                            isUser = likes.isUser
                        )
                    )
                }
                Resource.Success(getLikes)
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