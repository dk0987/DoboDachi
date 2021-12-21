package com.devdk.socialmedia.feature_post.domain.useCases

import android.content.SharedPreferences
import com.devdk.socialmedia.core.util.AuthConst
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository

class GetPostByIdUseCase (
    private val postRepository: PostRepository ,
    private val sharedPreferences: SharedPreferences
){
    suspend operator fun invoke(postId : String) : Resource<Post>{
        val ownerId = sharedPreferences.getString(AuthConst.userId , "") ?: ""
        return when (val result = postRepository.getPostById(postId , ownerId)) {
            is Resource.Success -> {
                Resource.Success(result.data)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }

}