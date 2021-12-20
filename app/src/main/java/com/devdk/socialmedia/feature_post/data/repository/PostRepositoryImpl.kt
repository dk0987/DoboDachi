package com.devdk.socialmedia.feature_post.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.data.remote.PostApi
import com.devdk.socialmedia.feature_post.data.remote.dto.request.AddPostRequest
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import com.devdk.socialmedia.feature_post.util.Mode
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val postApi : PostApi ,
    private val gson: Gson
) : PostRepository {

    override suspend fun getPost(userId: String , page : Int): Resource<List<Post>> {
        val posts = arrayListOf<Post>()
        return try {
            val response = postApi.getPosts(userId , page)
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

    override suspend fun addPost(description: String, mode: String , image : Uri): Resource<Unit> {
        val request = AddPostRequest(
            description = description ,
            mode = mode
        )
        val file = image.toFile()
        return try {
            val response = postApi.addPost(
                addPostRequest = MultipartBody.Part
                    .createFormData(
                        name = "add_post" ,
                        value = gson.toJson(request)
                    ) ,
                postImage = MultipartBody.Part
                    .createFormData(
                        name = "post_image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            if (response.successful) {
                Resource.Success(Unit)
            }
            else{
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


    override suspend fun deletePost(postId: String): Resource<Unit> {
        val response = postApi.deletePost(postId)
        return try {
            if (response.successful){
                Resource.Success(Unit)
            }
            else{
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