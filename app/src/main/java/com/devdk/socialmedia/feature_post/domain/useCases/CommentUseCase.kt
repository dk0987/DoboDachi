package com.devdk.socialmedia.feature_post.domain.useCases

import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.repository.CommentRepository

class CommentUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(postId : String , comment : String) : Resource<Unit> {
        if (comment.isBlank()){
            return Resource.Error("Comment ${Error.FIELD_EMPTY}" )
        }
        return when (val result = commentRepository.comment(postId , comment)) {
            is Resource.Success -> {
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}