package com.devdk.socialmedia.core.domain.use_case

import com.devdk.socialmedia.core.domain.repository.FollowRepository
import com.devdk.socialmedia.core.util.Resource

class FollowUseCase(
    private val followRepository: FollowRepository
) {
    suspend operator fun invoke(userId : String , isFollowed : Boolean) : String? {
        return when (val result = if (isFollowed)followRepository.unfollow(userId) else followRepository.follow(userId)) {
            is Resource.Success -> {
                null
            }
            is Resource.Error -> {
                result.message
            }
        }
    }
}