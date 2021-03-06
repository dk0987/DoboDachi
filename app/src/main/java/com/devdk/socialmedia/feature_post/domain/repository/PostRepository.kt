package com.devdk.socialmedia.feature_post.domain.repository

import android.net.Uri
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.util.Mode
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPost(userId : String , page : Int) : Resource<List<Post>>

    suspend fun addPost(description : String , mode : String , image : Uri) : Resource<Unit>

    suspend fun deletePost(postId : String) : Resource<Unit>

    suspend fun getPostById(postId: String , userId: String) : Resource<Post>

    suspend fun getProfilePic() : Resource<String>

    suspend fun getPostForUser(userId : String , page : Int , ownerId : String) : Resource<List<Post>>

}