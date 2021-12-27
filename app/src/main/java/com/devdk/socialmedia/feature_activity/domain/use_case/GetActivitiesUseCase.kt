package com.devdk.socialmedia.feature_activity.domain.use_case

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_activity.domain.modal.Activity
import com.devdk.socialmedia.feature_activity.domain.repository.ActivityRepository

class GetActivitiesUseCase(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(page : Int) : Resource<List<Activity>>{
        return when(val result = activityRepository.getActivities(page)){
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}