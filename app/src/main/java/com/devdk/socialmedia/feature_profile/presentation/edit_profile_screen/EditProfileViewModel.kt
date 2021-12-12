package com.devdk.socialmedia.feature_profile.presentation.edit_profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.util.Const.PRIVATE
import com.devdk.socialmedia.core.util.Const.PUBLIC
import com.devdk.socialmedia.feature_post.presentation.add_post_screen.AddPostDetailEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(

) : ViewModel(){

    private val _userNameTextFieldState = mutableStateOf(TextFieldStates())
    val userNameTextFieldState : State<TextFieldStates> = _userNameTextFieldState

    private val _bioTextFieldState = mutableStateOf(TextFieldStates())
    val bioTextFieldState : State<TextFieldStates> = _bioTextFieldState

    fun onEvent(event : EditProfileEvents) {
        when(event){
            is EditProfileEvents.Username -> {
                _userNameTextFieldState.value = userNameTextFieldState.value.copy(
                    text = event.username
                )
            }
            is EditProfileEvents.Bio -> {
                _bioTextFieldState.value = bioTextFieldState.value.copy(
                    text = event.bio
                )
            }
            is EditProfileEvents.Save -> {

            }
        }
    }

}