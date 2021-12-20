package com.devdk.socialmedia.feature_post.domain.useCases

import android.net.Uri
import androidx.core.net.toFile
import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Const.PUBLIC
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import com.devdk.socialmedia.feature_post.util.Mode

class AddPostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(description : String , mode : Mode , image : Uri) : Resource<Unit> {
        if (image.toString().isBlank()){
            return Resource.Error("Image ${Error.FIELD_EMPTY}")
        }
         val result = postRepository.addPost(
             description = description.ifBlank { "" },
             mode = mode.mode ,
             image = image
         )
        return when(result) {
            is Resource.Success -> {
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error(result.message)
            }
        }
    }
}
