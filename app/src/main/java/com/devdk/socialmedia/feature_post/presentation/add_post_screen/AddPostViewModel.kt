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
import com.devdk.socialmedia.feature_post.util.Mode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addPostUseCase: AddPostUseCase
) : ViewModel(){


    private val _descriptionTextField = mutableStateOf(TextFieldStates())
    val descriptionTextFieldStates : State<TextFieldStates> = _descriptionTextField

    private val _privateMode = mutableStateOf(false)
    val privateMode : State<Boolean> = _privateMode

    private val _publicMode = mutableStateOf(true)
    val publicMode : State<Boolean> = _publicMode

    private val _contentUri = mutableStateOf("")
    val contentUri : State<String> = _contentUri

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
         savedStateHandle.get<String>("croppedImageUri")?.let { uri ->
            _contentUri.value = uri
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
                        _privateMode.value = true
                        _publicMode.value = false
                    }
                    PUBLIC -> {
                        _privateMode.value = false
                        _publicMode.value = true
                    }
                }
            }
            is AddPostDetailEvents.Post -> {
                if (privateMode.value){
                    addPost(Mode.Private)
                }
                else if (publicMode.value){
                    addPost(Mode.Public)
                }
                else Unit
            }
        }
    }

    private fun addPost(mode : Mode){
        val description = descriptionTextFieldStates.value.text
        val image = contentUri.value.toUri()
        viewModelScope.launch {
            when (val result = addPostUseCase(description ,mode , image)){
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(result.message)
                    )
                }
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Routes.Feed.screen , null)
                    )
                }
            }
        }

    }
}
