package com.devdk.socialmedia.core.domain.repository

import com.devdk.socialmedia.core.util.Resource

interface FollowRepository {

    suspend fun follow(followingId : String) : Resource<Unit>

    suspend fun unfollow(followingId : String) : Resource<Unit>

}