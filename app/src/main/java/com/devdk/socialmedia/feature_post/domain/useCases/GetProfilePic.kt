package com.devdk.socialmedia.feature_post.domain.useCases

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository

class GetProfilePic (
    private val postRepository: PostRepository
) {
    suspend operator fun invoke() : String? {
        return when(val result = postRepository.getProfilePic()){
            is Resource.Success -> {
                result.data
            }
            is Resource.Error -> {
                result.message
            }
        }
    }
}