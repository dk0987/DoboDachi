package com.devdk.socialmedia.feature_activity.presentation

import com.devdk.socialmedia.feature_activity.domain.modal.Activity

data class ActivityStates(
    val isLoading : Boolean = false ,
    val activity : List<Activity> = emptyList() ,
    val endReached : Boolean = false
)
