package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.presentation.util.MenuItems
import com.devdk.socialmedia.core.util.DefaultPagination
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.modal.Comment
import com.devdk.socialmedia.feature_post.domain.useCases.CommentUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.GetCommentsUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val getCommentsUseCase: GetCommentsUseCase ,
    private val commentUseCase: CommentUseCase,
    stateHandle: SavedStateHandle
) : ViewModel(){

    private val _commentTextField = mutableStateOf(TextFieldStates())
    val commentTextFieldStates : State<TextFieldStates> = _commentTextField

    private val _postDetailStates = mutableStateOf(PostDetailStates())
    val postDetailStates : State<PostDetailStates> = _postDetailStates

    private val _commentStates = mutableStateOf(CommentStates())
    val commentStates : State<CommentStates> = _commentStates

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val postId = stateHandle.get<String>("postId") ?: ""

    private val pagination = DefaultPagination(
        onLoadUpdate = { loading ->
            _commentStates.value = commentStates.value.copy(
                isCommentLoading = loading
            )
        } ,
        onRequest = { page ->
            getCommentsUseCase(postId , page)
        },
        onSuccess = { comments ->
            _commentStates.value = commentStates.value.copy(
                comments = commentStates.value.comments + comments,
                endReached = comments.isEmpty(),
            )
        },
        onError = { error ->
            _eventFlow.emit(UiEvent.ShowSnackBar(error))
        }
    )

    init {
        loadPostById(postId)
        loadComments()
    }

    fun onEvent(events: PostDetailEvents){
        when(events) {
            is PostDetailEvents.CommentTextField-> {
                _commentTextField.value = commentTextFieldStates.value.copy(
                    text = events.comment,
                )
            }
            is PostDetailEvents.Comment ->{
                comment(postId)
                loadComments()
            }
            is PostDetailEvents.Menu -> {
            when(events.option) {
                MenuItems.dropDown[0] -> {

                }
                MenuItems.dropDown[1] -> {
                }
            }
        }
        }
    }

    private fun loadPostById(postId : String) {
        viewModelScope.launch {
            when(val post = postUseCases.getPostByIdUseCase(postId)){
                is Resource.Success -> {
                    _postDetailStates.value = postDetailStates.value.copy(
                        post = post.data
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(post.message))
                }
            }
        }
    }

    fun loadComments(){
        viewModelScope.launch {
            pagination.loadItems()
        }
    }

    private fun comment(postId : String ) {
        viewModelScope.launch {
            val comment = commentTextFieldStates.value.text
            when(val result = commentUseCase(postId, comment)) {
                is Resource.Success -> {
                    _commentStates.value = commentStates.value.copy(
                        isCommentLoading = false
                    )
                    _commentTextField.value = commentTextFieldStates.value.copy(
                        text = "" ,
                    )
                    loadComments()
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }
}