package com.devdk.socialmedia.core.presentation.states

data class TextFieldStates (
    val text : String = "",
    val isPasswordVisible : Boolean = false ,
    val isError : Boolean = false ,
    val error : String = ""
)