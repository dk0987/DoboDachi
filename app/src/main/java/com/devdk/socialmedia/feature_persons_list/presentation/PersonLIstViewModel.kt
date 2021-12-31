package com.devdk.socialmedia.feature_persons_list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.FollowUseCase
import com.devdk.socialmedia.core.domain.use_case.GetFollowersUseCase
import com.devdk.socialmedia.core.domain.use_case.GetFollowingUseCase
import com.devdk.socialmedia.core.domain.use_case.GetLikesUseCase
import com.devdk.socialmedia.core.util.Const.FOLLOWERS_SCREEN
import com.devdk.socialmedia.core.util.Const.FOLLOWING_SCREEN
import com.devdk.socialmedia.core.util.Const.LIKED_SCREEN
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonLIstViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLikesUseCase: GetLikesUseCase,
    private val followUseCase: FollowUseCase,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase,
) : ViewModel(){

    private val _personListScreenStates = mutableStateOf(PersonListState())
    val personListScreenStates : State<PersonListState> = _personListScreenStates

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val screen = savedStateHandle.get<String>("personList")
        val parentId = savedStateHandle.get<String>("parentId")
        val userId = savedStateHandle.get<String>("userId")

        if (screen != null) {
            if (parentId?.isBlank() == false) {
                loadLists(screen , parentId)
            }
            if (userId ?.isBlank() == false){
                loadLists(screen , userId)
            }
        }
    }

   private fun loadLists(screen : String , listFor : String) {
       viewModelScope.launch {
           println("List for :$listFor")
           when(screen) {
               LIKED_SCREEN -> {
                   _personListScreenStates.value = personListScreenStates.value.copy(
                       screenName = screen ,
                   )
                   getLikes(listFor)
               }
               FOLLOWERS_SCREEN -> {
                   _personListScreenStates.value = personListScreenStates.value.copy(
                       screenName = screen ,
                   )
                   getFollowers(listFor)
               }
               FOLLOWING_SCREEN -> {
                   _personListScreenStates.value = personListScreenStates.value.copy(
                       screenName = screen ,
                   )
                   getFollowings(listFor)
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
                    people = result.data!!,
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
                people = personListScreenStates.value.people.map {
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


    private suspend fun getFollowers(listFor : String) {
        _personListScreenStates.value = personListScreenStates.value.copy(
            isLoading = true,
        )
        when(val result = getFollowersUseCase(listFor)) {
            is Resource.Success -> {
                _personListScreenStates.value = personListScreenStates.value.copy(
                    people = result.data!!,
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



    private suspend fun getFollowings(listFor : String) {
        _personListScreenStates.value = personListScreenStates.value.copy(
            isLoading = true,
        )
        when(val result = getFollowingUseCase(listFor)) {
            is Resource.Success -> {
                _personListScreenStates.value = personListScreenStates.value.copy(
                    people = result.data!!,
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

}