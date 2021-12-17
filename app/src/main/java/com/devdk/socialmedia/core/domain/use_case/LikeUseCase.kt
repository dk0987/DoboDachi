package com.devdk.socialmedia.core.domain.use_case

import com.devdk.socialmedia.core.domain.repository.LikeRepository
import com.devdk.socialmedia.core.util.LikedOn
import com.devdk.socialmedia.core.util.Resource

class LikeUseCase(
    private val likeRepository: LikeRepository
) {
    suspend operator fun invoke(parentId : String , likedOn: LikedOn , isLiked : Boolean) : Resource<Unit> {
        return if (isLiked) {
            when(val result = likeRepository.unLike(parentId)) {
                is Resource.Success -> {
                    Resource.Success(Unit)
                }
                is Resource.Error -> {
                    Resource.Error(result.message)
                }
            }
        }
        else {
            when(val result = likeRepository.like(parentId , likedOn)) {
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