package com.devdk.socialmedia.feature_post.data.post_source

import android.text.TextUtils.isEmpty
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.domain.repository.PostRepository

class PostSource(
    private val postRepository: PostRepository,
    private val userId : String?
) : PagingSource<Int, Post>() {

    var currentPage = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: currentPage
        println("Pages : ${params.key}")
        return try{
            val response = postRepository.getPost(userId!!)
            LoadResult.Page(
                data = response.data!!,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        }
        catch (e : Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }
}