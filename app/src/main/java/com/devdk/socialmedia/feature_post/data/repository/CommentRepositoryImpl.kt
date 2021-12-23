package com.devdk.socialmedia.feature_post.data.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.data.remote.CommentApi
import com.devdk.socialmedia.feature_post.data.remote.dto.request.CommentRequest
import com.devdk.socialmedia.feature_post.domain.modal.Comment
import com.devdk.socialmedia.feature_post.domain.repository.CommentRepository
import retrofit2.HttpException
import java.io.IOException

class CommentRepositoryImpl(
    private val commentApi: CommentApi
) : CommentRepository {

    override suspend fun comment(postID: String, comment: String): Resource<Unit> {
        val response = commentApi.comment(CommentRequest(
            postId = postID ,
            comment = comment
        ))
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

    override suspend fun getComments(
        postID: String,
    ): Resource<List<Comment>> {
        val comments = arrayListOf<Comment>()
        return try {
            val response = commentApi.getComments(postID)
            if (response.successful) {
                response.data?.forEach { comment ->
                    comments.add(
                        Comment(
                          comment = comment.comment ,
                            userProfileUrl = comment.userProfileUrl ?: "",
                            likes = comment.likes ,
                            isLiked = comment.isLiked ,
                            commentId = comment.commentId ,
                            postID = comment.postId ,
                            isUser = comment.isUser,
                            username = comment.userName ,
                            timeStamp = comment.timeStamp
                        )
                    )
                }
                Resource.Success(comments)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: IOException) {
            Resource.Error(Const.SOMETHING_WRONG)
        } catch (e: HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }


    override suspend fun deleteComment(commentId: String): Resource<Unit> {
        val response = commentApi.deleteComment(commentId)
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