package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.LikeUseCase
import com.devdk.socialmedia.core.presentation.util.MenuItems
import com.devdk.socialmedia.core.util.DefaultPagination
import com.devdk.socialmedia.core.util.LikedOn
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.useCases.DeletePostUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.GetPostUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.PostUseCases
import com.devdk.socialmedia.feature_profile.presentation.profile_screen.PaginationPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val likeUseCase: LikeUseCase,
    private val postUseCases: PostUseCases
): ViewModel() {

    private val _feedScreenStates = mutableStateOf(FeedScreenStates())
    val feedScreenStates : State<FeedScreenStates> = _feedScreenStates

    private val _paginatedPost = mutableStateOf(PaginationPost())
    val paginatedPost : State<PaginationPost> = _paginatedPost

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val pagination = DefaultPagination(
        onLoadUpdate = { isLoading ->
            _paginatedPost.value = paginatedPost.value.copy(
                isLoading = isLoading
            )
        } ,
        onRequest = { page ->
            postUseCases.getPostUseCase(userId = null , page = page)
        } ,
        onSuccess = { posts ->
            _paginatedPost.value = paginatedPost.value.copy(
                items = paginatedPost.value.items + posts ,
                isLoading = false,
                endReached = posts.isEmpty()
            )
        } ,
        onError = { error ->
            _eventFlow.emit(
                UiEvent.ShowSnackBar(error)
            )
        }
    )

    init {
        loadMorePost()
    }

    fun onEvent(event : FeedScreenEvents) {
        when(event){
            is FeedScreenEvents.OnLike -> {
                _paginatedPost.value = paginatedPost.value.copy(
                    items = paginatedPost.value.items.map { post ->
                        if (post.postId == event.parentId) {
                            post.copy(
                                isLiked = !post.isLiked,
                                liked = if (post.isLiked) post.liked - 1 else post.liked + 1
                            )
                        } else post
                    }
                )
                toggleLike(event.parentId , LikedOn.Post , event.isLiked)
            }
            is FeedScreenEvents.OnShare -> {

            }
            is FeedScreenEvents.OnPost -> {

            }
            is FeedScreenEvents.Profile -> {

            }
            is FeedScreenEvents.Menu -> {
                println("Delete${event.option}")
                when(event.option) {
                    MenuItems.dropDown[0] -> {

                    }
                    MenuItems.dropDown[1] -> {
                        deletePost(event.postId)
                    }
                }
            }
            is FeedScreenEvents.LikedBy-> {

            }
            is FeedScreenEvents.Toggle -> {
                _feedScreenStates.value = feedScreenStates.value.copy(
                    menuExpanded = !feedScreenStates.value.menuExpanded
                )
            }
        }
    }


     fun loadMorePost() {
        viewModelScope.launch {
            pagination.loadItems()
        }
     }

    private fun toggleLike(parentId : String , likedOn: LikedOn , isLiked : Boolean)  {
        viewModelScope.launch {
             when(val liked = likeUseCase(parentId, likedOn, isLiked)) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    _paginatedPost.value = paginatedPost.value.copy(
                        items = paginatedPost.value.items.map { post ->
                            if (post.postId == parentId) {
                                post.copy(
                                    isLiked = !post.isLiked,
                                    liked = if (post.isLiked) post.liked - 1 else post.liked + 1
                                )
                            } else post
                        }
                    )
                    _eventFlow.emit(UiEvent.ShowSnackBar(liked.message))
                }
            }
        }
    }

    private fun deletePost(postId : String) {
        viewModelScope.launch {
            when (val result = postUseCases.deletePostUseCase(postId)) {
                is Resource.Success -> {
                    paginatedPost.value.items.find { it.postId == postId }?.let { post ->
                        _paginatedPost.value = paginatedPost.value.copy(
                            items = paginatedPost.value.items.minus(post)
                        )
                    }

                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

}