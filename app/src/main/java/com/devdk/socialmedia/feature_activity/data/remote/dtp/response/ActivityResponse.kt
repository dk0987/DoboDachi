package com.devdk.socialmedia.feature_activity.data.remote.dtp.response

data class ActivityResponse(
    val userProfileUrl : String ,
    val username : String ,
    val actionPerformedOn : String ,
    val actionPerformedBy : String ,
    val action : Int ,
    val timeStamp : Long
)
