package com.devdk.socialmedia.feature_search.presentation

import android.widget.SearchView
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.FollowUseCase
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import com.devdk.socialmedia.feature_search.domain.use_case.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val followUseCase: FollowUseCase ,
): ViewModel() , SearchView.OnQueryTextListener {

    private val _searchTextFieldStates = mutableStateOf(TextFieldStates())
    val searchTextFieldState : State<TextFieldStates> = _searchTextFieldStates

    private val _searchState = mutableStateOf(SearchState())
    val searchState : State<SearchState> = _searchState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFLow = _eventFlow.asSharedFlow()

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    fun onEvent(event : SearchEvents) {
        when(event) {
            is SearchEvents.Follow -> {
                follow(event.userId , event.isFollowed)
            }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModelScope.launch {
            _searchState.value = searchState.value.copy(
                isLoading = true
            )
            newText?.let { query ->
                _searchTextFieldStates.value = searchTextFieldState.value.copy(
                    text = query
                )
                val username = searchTextFieldState.value.text.trim()
                if (username.isNotEmpty()){
                    when(val result = searchUserUseCase(username)){
                        is Resource.Success-> {
                            _searchState.value = searchState.value.copy(
                                searchedResult = result.data!!,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(result.message),
                            )
                            _searchState.value = searchState.value.copy(
                                isLoading = false
                            )
                        }
                    }
                }
                else{
                    _searchState.value = searchState.value.copy(
                        isLoading = false
                    )
                }

            }
        }
        return false
    }

    private fun follow(userId : String , isFollowed : Boolean)  {
        viewModelScope.launch {
            val error = followUseCase(userId , isFollowed)

            _searchState.value = searchState.value.copy(
                searchedResult = searchState.value.searchedResult.map {
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