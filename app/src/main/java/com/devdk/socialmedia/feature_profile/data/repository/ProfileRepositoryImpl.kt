package com.devdk.socialmedia.feature_profile.data.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_profile.data.remote.ProfileApi
import com.devdk.socialmedia.feature_profile.domain.modal.User
import com.devdk.socialmedia.feature_profile.domain.repository.ProfileRepository
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Resource<User> {
        val response = profileApi.getProfile(userId)
        var user : User? = null
        return try {
            if (response.successful) {
                response.data?.let { userResponse ->
                    user = User(
                        userId = userResponse.userId,
                        username = userResponse.username,
                        bannerUrl = userResponse.bannerUrl,
                        profileUrl = userResponse.profileUrl,
                        isUser = userResponse.isUser,
                        isFollowing = userResponse.isFollowing,
                        bio = userResponse.bio,
                        followers = userResponse.followers,
                        following = userResponse.following,
                        posts = userResponse.posts
                    )
                }
                Resource.Success(user)
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