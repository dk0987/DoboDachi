package com.devdk.socialmedia.core.presentation.images_screen

import com.devdk.socialmedia.core.domain.modal.Images

data class ImagesStates (
    val images : List<Images> = emptyList(),
    val isLoading : Boolean = false,
    val route : String? = null
)