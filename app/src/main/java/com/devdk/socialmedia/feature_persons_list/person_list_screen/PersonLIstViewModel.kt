package com.devdk.socialmedia.feature_persons_list.person_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonLIstViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _personListScreenStates = mutableStateOf(PersonListState())
    val personListScreenStates : State<PersonListState> = _personListScreenStates

    init {
        savedStateHandle.get<String>("personList").let { screenName ->
            _personListScreenStates.value = screenName?.let {
                personListScreenStates.value.copy(
                    screenName = it
                )
            }!!
        }
    }
}