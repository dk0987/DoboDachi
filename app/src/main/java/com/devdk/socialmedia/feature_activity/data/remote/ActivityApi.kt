package com.devdk.socialmedia.feature_activity.data.remote

import com.devdk.socialmedia.core.data.remote.dto.response.BasicApiResponse
import com.devdk.socialmedia.core.util.BaseUrl
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.feature_activity.data.remote.dtp.response.ActivityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {

    @GET("api/activity/getActivity")
    suspend fun getActivities(
        @Query("page") page : Int = 0,
        @Query("pageSize") pageSize : Int = Const.POST_PAGE_SIZE
    ) : BasicApiResponse<List<ActivityResponse>>

    companion object {
        const val BASE_URL = BaseUrl.ACTIVITY_BASE_URL
    }
}