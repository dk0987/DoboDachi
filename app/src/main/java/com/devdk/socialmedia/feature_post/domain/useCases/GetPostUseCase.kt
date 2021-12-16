package com.devdk.socialmedia.feature_post.domain.useCases

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devdk.socialmedia.core.util.AuthConst
import com.devdk.socialmedia.feature_post.data.post_source.PostSource
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostUseCase(
    private val postRepository: PostRepository ,
    private val sharedPreferences: SharedPreferences
) {
     operator fun invoke(userId : String?) : Flow<PagingData<Post>> {
        val ownerId = sharedPreferences.getString(AuthConst.userId , "")
        return Pager(PagingConfig(10)){
                PostSource(postRepository , userId ?: ownerId)
        }.flow
    }

}
