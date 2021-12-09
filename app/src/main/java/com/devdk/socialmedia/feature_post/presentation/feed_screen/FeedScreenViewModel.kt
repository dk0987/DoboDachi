package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(

): ViewModel() {

    private val _feedScreenStates = mutableStateOf(FeedScreenStates())
    val feedScreenStates : State<FeedScreenStates> = _feedScreenStates

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

}