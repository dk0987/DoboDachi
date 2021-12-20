package com.devdk.socialmedia.feature_post.domain.useCases

import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository

class DeletePostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId : String) :Resource<Unit> {
        return if (postId.isBlank()) Resource.Error(Error.POST_ID_NOT_FOUND)
        else {
            when(val result = postRepository.deletePost(postId)){
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