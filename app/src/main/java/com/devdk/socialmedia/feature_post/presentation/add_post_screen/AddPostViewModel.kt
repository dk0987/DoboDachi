package com.devdk.socialmedia.feature_post.presentation.add_post_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.util.Const.PRIVATE
import com.devdk.socialmedia.core.util.Const.PUBLIC
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.useCases.AddPostUseCase
import com.devdk.socialmedia.feature_post.domain.useCases.PostUseCases
import com.devdk.socialmedia.feature_post.util.Mode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postUseCases: PostUseCases
) : ViewModel(){

    private val _descriptionTextField = mutableStateOf(TextFieldStates())
    val descriptionTextFieldStates : State<TextFieldStates> = _descriptionTextField

    private val _addPostScreenStates = mutableStateOf(AddPostScreenStates())
    val addPostScreenStates : State<AddPostScreenStates> = _addPostScreenStates

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
         savedStateHandle.get<String>("croppedImageUri")?.let { uri ->
           _addPostScreenStates.value = addPostScreenStates.value.copy(
               contentUri = uri
           )
         }
    }

    fun onEvent(event : AddPostDetailEvents) {
        when(event){
            is AddPostDetailEvents.Description -> {
                _descriptionTextField.value = descriptionTextFieldStates.value.copy(
                    text = event.description
                )
            }
            is AddPostDetailEvents.ViewMode -> {
                when(event.mode){
                    PRIVATE -> {
                        _addPostScreenStates.value = addPostScreenStates.value.copy(
                            private = true ,
                            public = false
                        )
                    }
                    PUBLIC -> {
                        _addPostScreenStates.value = addPostScreenStates.value.copy(
                            private = false ,
                            public = true
                        )
                    }
                    else -> {
                        _addPostScreenStates.value = addPostScreenStates.value.copy(
                            private = false ,
                            public = true
                        )
                    }
                }
            }
            is AddPostDetailEvents.Post -> {
                if (addPostScreenStates.value.private){
                    addPost(Mode.Private)
                }
                else if (addPostScreenStates.value.public){
                    addPost(Mode.Public)
                }
                else Unit
            }
        }
    }

    private fun addPost(mode : Mode){
        val description = descriptionTextFieldStates.value.text
        val image = addPostScreenStates.value.contentUri.toUri()
        viewModelScope.launch {
            _addPostScreenStates.value = addPostScreenStates.value.copy(
                isLoading = true
            )
            when (val result = postUseCases.addPostUseCase(description ,mode , image)){
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(result.message)
                    )
                    _addPostScreenStates.value = addPostScreenStates.value.copy(
                        isLoading = false
                    )
                }
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Routes.Feed.screen , null)
                    )
                    _addPostScreenStates.value = addPostScreenStates.value.copy(
                        isLoading = false
                    )
                }
            }
        }

    }
}
