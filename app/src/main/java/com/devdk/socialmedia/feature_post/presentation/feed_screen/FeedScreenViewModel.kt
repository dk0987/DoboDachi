package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.devdk.socialmedia.core.util.DefaultPagination
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.useCases.GetPostUseCase
import com.devdk.socialmedia.feature_profile.presentation.profile_screen.PaginationPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
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
            getPostUseCase(userId = null , page = page)
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

            }
            is FeedScreenEvents.OnShare -> {

            }
            is FeedScreenEvents.OnPost -> {

            }
            is FeedScreenEvents.Profile -> {

            }
            is FeedScreenEvents.Menu -> {
                _feedScreenStates.value = feedScreenStates.value.copy(
                    selectedOption = event.option
                )
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
}