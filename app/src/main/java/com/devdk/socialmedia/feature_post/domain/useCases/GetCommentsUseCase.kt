package com.devdk.socialmedia.feature_post.domain.useCases

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.modal.Comment
import com.devdk.socialmedia.feature_post.domain.repository.CommentRepository

class GetCommentsUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(postId : String , page : Int) : Resource<List<Comment>> {
        return when(val result = commentRepository.getComments(postId , page)){
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}