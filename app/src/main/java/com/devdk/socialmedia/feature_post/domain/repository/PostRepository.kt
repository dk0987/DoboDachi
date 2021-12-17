package com.devdk.socialmedia.feature_post.domain.repository

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.modal.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPost(userId : String , page : Int) : Resource<List<Post>>
}