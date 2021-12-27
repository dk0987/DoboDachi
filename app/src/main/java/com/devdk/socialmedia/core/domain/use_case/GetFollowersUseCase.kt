package com.devdk.socialmedia.core.domain.use_case

import com.devdk.socialmedia.core.domain.modal.PersonList
import com.devdk.socialmedia.core.domain.repository.FollowRepository
import com.devdk.socialmedia.core.util.Resource

class GetFollowersUseCase(
    private val followRepository: FollowRepository
) {
    suspend operator fun invoke(userId : String) : Resource<List<PersonList>> {
        return when (val result = followRepository.getFollowers(userId)) {
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}