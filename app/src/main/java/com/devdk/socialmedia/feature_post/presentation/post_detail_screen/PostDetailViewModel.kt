package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.LikeUseCase
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.presentation.util.MenuItems
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.util.LikedOn
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.useCases.CommentUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.DeleteCommentUseCase
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
    private val likeUseCase: LikeUseCase,
    private val deleteCommentUseCase : DeleteCommentUseCase,
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
            is PostDetailEvents.CommentMenu -> {
            when(events.option) {
                MenuItems.dropDown[0] -> {

                }
                MenuItems.dropDown[1] -> {
                    deleteComment(events.commentId)
                }
            }
        }
            is PostDetailEvents.PostMenu -> {
                when(events.option){
                    MenuItems.dropDown[0] -> {

                    }
                    MenuItems.dropDown[1] -> {
                        deletePost(events.postId)
                        commentStates.value.comments.forEach { comment ->
                            deleteComment(comment.commentId)
                        }
                    }
                }
            }
            is PostDetailEvents.OnLike -> {
                when(events.parentType) {
                    is LikedOn.Post -> {
                        _postDetailStates.value = postDetailStates.value.copy(
                            post = postDetailStates.value.post?.let {
                                it.copy(
                                    isLiked = !postDetailStates.value.post!!.isLiked ,
                                    liked = if (it.isLiked) it.liked - 1 else it.liked + 1
                                )
                            }
                        )
                    }
                    is LikedOn.Comment -> {
                        _commentStates.value = commentStates.value.copy(
                            comments = commentStates.value.comments.map { comment ->
                                if (comment.commentId == events.parentId) {
                                    comment.copy(
                                        isLiked = !comment.isLiked,
                                        likes = if (comment.isLiked) comment.likes - 1 else comment.likes + 1
                                    )
                                } else comment
                            }
                        )
                    }
                }
                toggleLike(events.parentId , events.parentType , events.isLiked)
            }
        }
    }

    private fun loadPostById(postId : String) {
        viewModelScope.launch {
            _postDetailStates.value = postDetailStates.value.copy(
                isPostLoading = true
            )
            when(val post = postUseCases.getPostByIdUseCase(postId)){
                is Resource.Success -> {
                    _postDetailStates.value = postDetailStates.value.copy(
                        post = post.data ,
                        isPostLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(post.message))
                    _postDetailStates.value = postDetailStates.value.copy(
                        isPostLoading = true
                    )
                }
            }
        }
    }

    private fun loadComments(){
        viewModelScope.launch {
            _commentStates.value = commentStates.value.copy(
                isCommentLoading = true
            )
            when(val result = getCommentsUseCase(postId)) {
                is Resource.Success -> {
                    _commentStates.value = commentStates.value.copy(
                        comments = result.data!!,
                        isCommentLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                    _commentStates.value = commentStates.value.copy(
                        isCommentLoading = false
                    )
                }
            }
        }
    }

    private fun comment(postId : String ) {
        viewModelScope.launch {
            val comment = commentTextFieldStates.value.text
            when(val result = commentUseCase(postId, comment)) {
                is Resource.Success -> {
                    _commentTextField.value = commentTextFieldStates.value.copy(
                        text = "" ,
                    )
                    loadComments()
                    _postDetailStates.value = postDetailStates.value.copy(
                        post = postDetailStates.value.post?.copy(
                            comment = postDetailStates.value.post?.comment?.plus(1) ?: 0
                        )
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                    _postDetailStates.value = postDetailStates.value.copy(
                        post = postDetailStates.value.post?.copy(
                            comment = postDetailStates.value.post?.comment?.minus(1) ?: 0
                        )
                    )
                }

            }
        }
    }


    private fun toggleLike(parentId : String, likedOn: LikedOn, isLiked : Boolean)  {
        viewModelScope.launch {
            when(val liked = likeUseCase(parentId, likedOn, isLiked)) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    when(likedOn) {
                        is LikedOn.Post -> {
                            _postDetailStates.value = postDetailStates.value.copy(
                                post = postDetailStates.value.post?.let {
                                    it.copy(
                                        isLiked =  !postDetailStates.value.post!!.isLiked ,
                                        liked = if (it.isLiked) it.liked - 1 else it.liked + 1
                                    )
                                }
                            )
                        }
                        is LikedOn.Comment -> {
                            _commentStates.value = commentStates.value.copy(
                                comments = commentStates.value.comments.map { comment ->
                                    if (comment.commentId == comment.commentId) {
                                        comment.copy(
                                            isLiked = !comment.isLiked,
                                            likes = if (comment.isLiked) comment.likes - 1 else comment.likes + 1
                                        )
                                    } else comment
                                }
                            )
                        }
                    }
                    _eventFlow.emit(UiEvent.ShowSnackBar(liked.message))
                }
            }
        }
    }


    private fun deletePost(postId : String) {
        viewModelScope.launch {
            when (val result = postUseCases.deletePostUseCase(postId)) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.Navigate(Routes.Feed.screen , null))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun deleteComment(commentId : String) {
        viewModelScope.launch {
            when (val result = deleteCommentUseCase(commentId)) {
                is Resource.Success -> {
                    commentStates.value.comments.find { it.commentId == commentId }?.let { comment ->
                        _commentStates.value = commentStates.value.copy(
                            comments = commentStates.value.comments.minus(comment)
                        )
                    }
                    _postDetailStates.value = postDetailStates.value.copy(
                        post = postDetailStates.value.post?.copy(
                            comment = postDetailStates.value.post?.comment?.minus(1) ?: 0
                        )
                    )
                }
                is Resource.Error -> {
                    _postDetailStates.value = postDetailStates.value.copy(
                        post = postDetailStates.value.post?.copy(
                            comment = postDetailStates.value.post?.comment?.plus(1) ?: 0
                        )
                    )
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

}