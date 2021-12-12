package com.devdk.socialmedia.feature_profile.presentation.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

) :ViewModel() {
    private val _profileStates = mutableStateOf(ProfileStates())
    val profileStates : State<ProfileStates> = _profileStates


}