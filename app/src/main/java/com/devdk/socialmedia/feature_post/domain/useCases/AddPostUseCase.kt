package com.devdk.socialmedia.feature_post.domain.useCases

import android.net.Uri
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Const.PUBLIC
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import com.devdk.socialmedia.feature_post.util.Mode

class AddPostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(description : String , mode : Mode , image : Uri) : Resource<Unit> {
        return when(mode){
            is Mode.Private -> {
                val result = postRepository.addPost(
                    description = description,
                    mode = mode.mode ,
                    image = image
                )
                 when(result){
                    is Resource.Success -> {
                        Resource.Success(Unit)
                    }
                    is Resource.Error -> {
                        Resource.Error(result.message)
                    }
                }
            }
            is Mode.Public -> {
                val result = postRepository.addPost(
                    description = description,
                    mode = mode.mode,
                    image = image
                )
                 when(result){
                    is Resource.Success -> {
                        Resource.Success(Unit)
                    }
                    is Resource.Error -> {
                        Resource.Error(result.message)
                    }
                }
            }
        }
    }
}