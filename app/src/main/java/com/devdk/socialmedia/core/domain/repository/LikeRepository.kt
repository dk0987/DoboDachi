package com.devdk.socialmedia.core.domain.repository

import com.devdk.socialmedia.core.data.remote.dto.response.GetLikeResponse
import com.devdk.socialmedia.core.domain.modal.GetLikes
import com.devdk.socialmedia.core.util.LikedOn
import com.devdk.socialmedia.core.util.Resource

interface LikeRepository {

    suspend fun like(parentId : String , likedOn: LikedOn) : Resource<Unit>

    suspend fun unLike(parentId: String) : Resource<Unit>

    suspend fun getLikes(parentId : String) : Resource<List<GetLikes>>
}