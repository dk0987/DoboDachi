package com.devdk.socialmedia.feature_post.domain.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.modal.Comment

interface CommentRepository {

    suspend fun comment(postID : String , comment : String) : Resource<Unit>

    suspend fun getComments(postID: String ) : Resource<List<Comment>>

    suspend fun deleteComment(commentId : String) : Resource<Unit>
}