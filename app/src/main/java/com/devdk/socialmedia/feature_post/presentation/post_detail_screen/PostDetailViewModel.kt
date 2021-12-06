package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(

) : ViewModel(){

    private val _commentTextField = mutableStateOf(TextFieldStates())
    val commentTextFieldStates : State<TextFieldStates> = _commentTextField

    fun onEvent(events: PostDetailEvents){
        when(events) {
            is PostDetailEvents.Comment-> {
                _commentTextField.value = commentTextFieldStates.value.copy(
                    text = events.comment,
                )
            }
        }
    }
}