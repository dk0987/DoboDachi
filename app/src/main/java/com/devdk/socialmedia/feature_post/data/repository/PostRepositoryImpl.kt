package com.devdk.socialmedia.feature_post.data.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.data.remote.PostApi
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val postApi : PostApi
) : PostRepository {

    override suspend fun getPost(userId: String): Resource<List<Post>> {
        val posts = arrayListOf<Post>()
        return try {
            val response = postApi.getPosts(userId)
            if (response.successful) {
                response.data?.forEach { post ->
                    posts.add(
                        Post(
                            userId = post.userId,
                            timeStamp = post.timeStamp,
                            userName = post.userName,
                            userProfileUrl = post.userProfileUrl,
                            postImageUrl = post.postImageUrl,
                            description = post.description,
                            mode = post.mode,
                            postId = post.postId,
                            isUser = userId == post.userId,
                            liked = post.liked,
                            comment = post.comment,
                            isLiked = post.isPostLiked
                        )
                    )
                }
                Resource.Success(posts)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: IOException) {
            Resource.Error(Const.SOMETHING_WRONG)
        } catch (e: HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }
}