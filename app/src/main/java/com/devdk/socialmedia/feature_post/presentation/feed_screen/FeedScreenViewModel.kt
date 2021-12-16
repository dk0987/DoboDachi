package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.useCases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
): ViewModel() {

    private val _feedScreenStates = mutableStateOf(FeedScreenStates())
    val feedScreenStates : State<FeedScreenStates> = _feedScreenStates

    init {
        getPost(null)
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


    private fun getPost(userId : String?) {
           _feedScreenStates.value = feedScreenStates.value.copy(
               isLoading = true
           )
           val posts = getPostUseCase(userId).cachedIn(viewModelScope)
           _feedScreenStates.value = feedScreenStates.value.copy(
               posts = posts,
               isLoading = false
           )
   }
}