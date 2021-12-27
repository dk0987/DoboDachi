package com.devdk.socialmedia.core.domain.repository

import com.devdk.socialmedia.core.domain.modal.PersonList
import com.devdk.socialmedia.core.util.Resource

interface FollowRepository {

    suspend fun follow(followingId : String) : Resource<Unit>

    suspend fun unfollow(followingId : String) : Resource<Unit>

    suspend fun getFollowers(userId : String) : Resource<List<PersonList>>

    suspend fun getFollowings(userId : String) : Resource<List<PersonList>>

}