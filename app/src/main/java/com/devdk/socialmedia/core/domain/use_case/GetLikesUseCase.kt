package com.devdk.socialmedia.core.domain.use_case

import com.devdk.socialmedia.core.domain.modal.GetLikes
import com.devdk.socialmedia.core.domain.repository.LikeRepository
import com.devdk.socialmedia.core.util.Resource

class GetLikesUseCase (
    private val likeRepository: LikeRepository
) {
    suspend operator fun invoke(parentId : String) : Resource<List<GetLikes>> {
        return when (val result = likeRepository.getLikes(parentId)) {
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}