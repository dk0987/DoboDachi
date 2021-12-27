package com.devdk.socialmedia.feature_activity.domain.modal

import com.devdk.socialmedia.feature_activity.presentation.util.Action

data class Activity(
    val userProfileUrl : String ,
    val username : String ,
    val actionPerformedOn : String ,
    val actionPerformedBy : String ,
    val action : Action? ,
    val timeStamp : Long
)
