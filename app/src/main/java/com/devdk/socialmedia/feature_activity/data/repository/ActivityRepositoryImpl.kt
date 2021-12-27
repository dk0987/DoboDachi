package com.devdk.socialmedia.feature_activity.data.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_activity.data.remote.ActivityApi
import com.devdk.socialmedia.feature_activity.domain.modal.Activity
import com.devdk.socialmedia.feature_activity.domain.repository.ActivityRepository
import com.devdk.socialmedia.feature_activity.presentation.util.Action
import retrofit2.HttpException
import java.io.IOException

class ActivityRepositoryImpl(
    private val activityApi: ActivityApi
) : ActivityRepository {

    override suspend fun getActivities(page: Int): Resource<List<Activity>> {
        return try {
            val response = activityApi.getActivities(page)
            if (response.successful) {
                val activities = response.data?.map { activity ->
                   Activity(
                       userProfileUrl = activity.userProfileUrl ,
                       username = activity.username ,
                       actionPerformedOn = activity.actionPerformedOn,
                       actionPerformedBy = activity.actionPerformedBy,
                       action = Action.toAction(activity.action) ,
                       timeStamp = activity.timeStamp
                   )
                }
                Resource.Success(activities)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: IOException) {
            Resource.Error(Const.SOMETHING_WRONG)
        } catch (e: HttpException) {
            Resource.Error(Const.SOMETHING_WRONG)
        }
    }
}