package com.devdk.socialmedia.feature_profile.presentation.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_profile.domain.useCases.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase ,
    stateHandle: SavedStateHandle
) :ViewModel() {
    private val _profileStates = mutableStateOf(ProfileStates())
    val profileStates : State<ProfileStates> = _profileStates

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        stateHandle.get<String>("userId")?.let {
            getProfile(it)
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


}