package com.devdk.socialmedia.feature_activity.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.util.DefaultPagination
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_activity.domain.modal.Activity
import com.devdk.socialmedia.feature_activity.domain.use_case.GetActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivitiesUseCase: GetActivitiesUseCase
) : ViewModel(){
    private val _paginatedActivityState = mutableStateOf(ActivityStates())
    val paginatedActivityState : State<ActivityStates> = _paginatedActivityState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val pagination = DefaultPagination<Activity>(
        onLoadUpdate = { loading ->
            _paginatedActivityState.value = paginatedActivityState.value.copy(
                isLoading = loading
            )
        },
        onRequest = { page ->
            getActivitiesUseCase(page)
        },
        onSuccess = { activities ->
            _paginatedActivityState.value = paginatedActivityState.value.copy(
                activity = paginatedActivityState.value.activity + activities ,
                endReached = activities.isEmpty()
            )
        },
        onError = { error ->
            _eventFlow.emit(UiEvent.ShowSnackBar(error))
        }
    )

    init {
        loadActivities()
    }


    fun loadActivities() {
        viewModelScope.launch {
            pagination.loadItems()
        }
    }
}