package com.devdk.socialmedia.feature_activity.domain.repository

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_activity.domain.modal.Activity


interface ActivityRepository {

    suspend fun getActivities(page : Int) : Resource<List<Activity>>

}