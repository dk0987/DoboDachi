package com.devdk.socialmedia.feature_profile.presentation.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.LikeUseCase
import com.devdk.socialmedia.core.presentation.util.MenuItems
import com.devdk.socialmedia.core.util.DefaultPagination
import com.devdk.socialmedia.core.util.LikedOn
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.modal.Post
import com.devdk.socialmedia.feature_post.domain.useCases.DeletePostUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.PostUseCases
import com.devdk.socialmedia.feature_post.presentation.feed_screen.FeedScreenEvents
import com.devdk.socialmedia.feature_profile.domain.useCases.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase ,
    private val postUseCases: PostUseCases,
    private val likeUseCases: LikeUseCase,
    stateHandle: SavedStateHandle
) :ViewModel() {
    private val _profileStates = mutableStateOf(ProfileStates())
    val profileStates : State<ProfileStates> = _profileStates

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var userId : String = stateHandle.get<String>("userId").toString()

    private val pagination = DefaultPagination<Post>(
        onLoadUpdate = { loading ->
            _profileStates.value = profileStates.value.copy(
                isLoading = loading
            )
        } ,
        onRequest = { page ->
            postUseCases.getPostForUserUseCase(userId , page)
        },
        onSuccess = { posts ->
            _profileStates.value = profileStates.value.copy(
                posts = profileStates.value.posts + posts ,
                endReached = posts.isEmpty()
            )
        },
        onError = { error ->
            _eventFlow.emit(
                UiEvent.ShowSnackBar(error)
            )
        }
    )

    init {
        getProfile(userId)
        loadPosts()
    }

    fun onEvent(event : ProfileScreenEvents) {
        when(event){
            is ProfileScreenEvents.OnLike -> {
                _profileStates.value = profileStates.value.copy(
                    posts = profileStates.value.posts.map { post ->
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
            is ProfileScreenEvents.OnShare -> {

            }
            is ProfileScreenEvents.Menu -> {
                println("Delete${event.option}")
                when(event.option) {
                    MenuItems.dropDown[0] -> {

                    }
                    MenuItems.dropDown[1] -> {
                        deletePost(event.postId)
                    }
                }
            }
        }
    }

    private fun getProfile(userId : String) {
        viewModelScope.launch {
            when(val result = getProfileUseCase(userId)) {
                is Resource.Success -> {
                    _profileStates.value = profileStates.value.copy(
                        user = result.data
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    fun loadPosts() {
        viewModelScope.launch {
            pagination.loadItems()
        }
    }

    private fun toggleLike(parentId : String , likedOn: LikedOn , isLiked : Boolean)  {
        viewModelScope.launch {
            when(val liked = likeUseCases(parentId, likedOn, isLiked)) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    _profileStates.value = profileStates.value.copy(
                        posts = profileStates.value.posts.map { post ->
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
                    profileStates.value.posts.find { it.postId == postId }?.let { post ->
                        _profileStates.value = profileStates.value.copy(
                            posts = profileStates.value.posts.minus(post),
                            user = profileStates.value.user?.posts?.minus(1)?.let {
                                profileStates.value.user?.copy(
                                    posts = it
                                )
                            }
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