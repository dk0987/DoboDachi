package com.devdk.socialmedia.core.presentation.images_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _images = mutableStateOf(ImagesStates())
    val images : State<ImagesStates> = _images

    init {
        savedStateHandle.get<String>("route")?.let { route->
            _images.value = images.value.copy(
                route = route
            )
        }
        savedStateHandle.get<String>("imageType")?.let { imageType ->
            _images.value = images.value.copy(
                imageType = imageType
            )
        }
        savedStateHandle.get<String>("userId")?.let { userID ->
            _images.value = images.value.copy(
                userId = userID
            )
            println("Image UserId : $userID")
        }
    }

    fun getImages(context: Context) {
        viewModelScope.launch {
            _images.value = images.value.copy(
                isLoading = true
            )
            val result = getImagesUseCase(context)
                _images.value = images.value.copy(
                    images = result,
                    isLoading = false
            )
        }
    }
}