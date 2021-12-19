package com.devdk.socialmedia.feature_post.domain.useCases

import android.content.SharedPreferences
import com.devdk.socialmedia.core.util.AuthConst
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository

class GetPostUseCase(
    private val postRepository: PostRepository ,
    private val sharedPreferences: SharedPreferences
) {
     suspend operator fun invoke(userId : String? , page : Int) : Resource<List<Post>> {
        val ownerId = sharedPreferences.getString(AuthConst.userId , "")!!
         return postRepository.getPost(userId ?: ownerId , page)
    }

}
