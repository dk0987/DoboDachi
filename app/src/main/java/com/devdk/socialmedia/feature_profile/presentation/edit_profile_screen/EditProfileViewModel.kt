package com.devdk.socialmedia.feature_profile.presentation.edit_profile_screen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.presentation.util.Error
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_profile.domain.modal.UpdateUser
import com.devdk.socialmedia.feature_profile.domain.useCases.GetProfileUseCase
import com.devdk.socialmedia.feature_profile.domain.useCases.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    stateHandle: SavedStateHandle
) : ViewModel(){

    private val _userNameTextFieldState = mutableStateOf(TextFieldStates())
    val userNameTextFieldState : State<TextFieldStates> = _userNameTextFieldState

    private val _bioTextFieldState = mutableStateOf(TextFieldStates())
    val bioTextFieldState : State<TextFieldStates> = _bioTextFieldState

    private val _editProfileStates = mutableStateOf(EditProfileStates())
    val editProfileStates : State<EditProfileStates> = _editProfileStates

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var userId : String = stateHandle.get<String>("userId").toString()

    init {
        stateHandle.get<String>("croppedImageUri")?.let { uri ->
           stateHandle.get<String>("imageType")?.let { imageType ->
               when (imageType) {
                   "Profile" -> {
                       _editProfileStates.value = editProfileStates.value.copy(
                           updateProfileUrl = uri.toUri(),
                           profileUrl = uri
                       )
                   }
                   "Banner" -> {
                       _editProfileStates.value = editProfileStates.value.copy(
                           updateProfileUrl = uri.toUri(),
                           bannerUrl = uri
                       )
                   }
               }
           }
        }
        getProfile(userId)
    }

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
                _editProfileStates.value = editProfileStates.value.copy(
                    isUpdating = true
                )
                updateUser(
                    updateUser = UpdateUser(
                        username = userNameTextFieldState.value.text,
                        bio = bioTextFieldState.value.text
                    ),
                    profileUrl = editProfileStates.value.updateProfileUrl ?: Uri.EMPTY ,
                    bannerUrl = editProfileStates.value.updateBannerUrl ?: Uri.EMPTY
                )
            }
        }
    }

    private fun getProfile(userId : String) {
        viewModelScope.launch {
            when(val result = getProfileUseCase(userId)) {
                is Resource.Success -> {
                    result.data?.let {
                       _userNameTextFieldState.value = userNameTextFieldState.value.copy(
                           text = it.username
                       )
                        _bioTextFieldState.value = bioTextFieldState.value.copy(
                            text = it.bio
                        )
                        _editProfileStates.value = editProfileStates.value.copy(
                            isError = false ,
                            profileUrl = it.profileUrl,
                            bannerUrl = it.bannerUrl
                        )
                    }

                }
                is Resource.Error -> {
                    if (result.message?.contains(Error.FIELD_EMPTY) == true){
                        _editProfileStates.value = editProfileStates.value.copy(
                            isError = true
                        )
                    }
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }


    private fun updateUser(updateUser: UpdateUser , profileUrl : Uri ,bannerUrl : Uri) {
        viewModelScope.launch {
            when(val result = updateProfileUseCase(updateUser , profileUrl, bannerUrl)) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.Navigate(Routes.Profile.screen , null))
                    _editProfileStates.value = editProfileStates.value.copy(
                        isUpdating = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.message))
                    _editProfileStates.value = editProfileStates.value.copy(
                        isUpdating = false
                    )
                }
            }
        }

    }

}