package com.devdk.socialmedia.feature_post.presentation.add_post_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.util.Const.PRIVATE
import com.devdk.socialmedia.core.util.Const.PUBLIC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(

) : ViewModel(){

    private val _descriptionTextField = mutableStateOf(TextFieldStates())
    val descriptionTextFieldStates : State<TextFieldStates> = _descriptionTextField

    private val _privateMode = mutableStateOf(false)
    val privateMode : State<Boolean> = _privateMode

    private val _publicMode = mutableStateOf(true)
    val publicMode : State<Boolean> = _publicMode

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
                
            }
        }
    }
}