package com.devdk.socialmedia.core.presentation.states

data class StandardTextFieldStates (
    val text : String = "",
    val isPasswordVisible : Boolean = false ,
    val isError : Boolean = false ,
    val error : String = ""
)