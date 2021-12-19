package com.devdk.socialmedia.core.presentation.images_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.socialmedia.core.domain.use_case.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    private val _images = mutableStateOf(ImagesStates())
    val images : State<ImagesStates> = _images

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