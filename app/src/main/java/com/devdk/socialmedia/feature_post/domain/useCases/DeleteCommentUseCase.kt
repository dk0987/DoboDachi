package com.devdk.socialmedia.feature_post.domain.useCases

import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.repository.CommentRepository

class DeleteCommentUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(commentId : String) : Resource<Unit> {
        return if (commentId.isBlank()) Resource.Error(Error.COMMENT_ID_NOT_FOUND)
        else {
            when(val result = commentRepository.deleteComment(commentId)){
                is Resource.Success -> {
                    Resource.Success(Unit)
                }
                is Resource.Error -> {
                    Resource.Error(result.message)
                }
            }
        }
    }
}