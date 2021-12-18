package com.devdk.socialmedia.feature_persons_list.person_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.FollowUseCase
import com.devdk.socialmedia.core.domain.use_case.GetLikesUseCase
import com.devdk.socialmedia.core.util.Const.FOLLOWERS_SCREEN
import com.devdk.socialmedia.core.util.Const.FOLLOWING_SCREEN
import com.devdk.socialmedia.core.util.Const.LIKED_SCREEN
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonLIstViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLikesUseCase: GetLikesUseCase,
    private val followUseCase: FollowUseCase
) : ViewModel(){

    private val _personListScreenStates = mutableStateOf(PersonListState())
    val personListScreenStates : State<PersonListState> = _personListScreenStates

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val screen = savedStateHandle.get<String>("personList")
        val parentId = savedStateHandle.get<String>("parentId")

        if (screen != null) {
            if (parentId != null) {
                loadLists(screen , parentId)
            }
        }
    }

   private fun loadLists(screen : String , listFor : String) {
       viewModelScope.launch {
           when(screen) {
               LIKED_SCREEN -> {
                   _personListScreenStates.value = personListScreenStates.value.copy(
                       screenName = LIKED_SCREEN ,
                   )
                   getLikes(listFor)
               }
               FOLLOWERS_SCREEN -> {

               }
               FOLLOWING_SCREEN -> {

               }
               else -> Unit
           }
       }
   }

    private suspend fun getLikes(listFor : String) {
        _personListScreenStates.value = personListScreenStates.value.copy(
            isLoading = true,
        )
        when(val result = getLikesUseCase(listFor)) {
            is Resource.Success -> {
                _personListScreenStates.value = personListScreenStates.value.copy(
                    persons = result.data!!,
                    isLoading = false
                )
            }
            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.ShowSnackBar(result.message)
                )
                _personListScreenStates.value = personListScreenStates.value.copy(
                    isLoading = false,
                )
            }
        }
    }

     fun follow(userId : String , isFollowed : Boolean)  {
        viewModelScope.launch {
            val error = followUseCase(userId , isFollowed)
            _personListScreenStates.value = personListScreenStates.value.copy(
                persons = personListScreenStates.value.persons.map {
                    if(it.userId == userId) {
                        it.copy(isFollowing = !it.isFollowing)
                    } else it
                }
            )
            error?.let {
                _eventFlow.emit(
                    UiEvent.ShowSnackBar(error),
                )
            }
        }
    }


}