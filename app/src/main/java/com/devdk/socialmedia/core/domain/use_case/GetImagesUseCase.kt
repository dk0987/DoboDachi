package com.devdk.socialmedia.core.domain.use_case

import android.content.Context
import com.devdk.socialmedia.core.domain.modal.Images
import com.devdk.socialmedia.core.domain.repository.ImageRepository

class GetImagesUseCase (
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(context: Context) : List<Images>{
        return imageRepository.getListOfImages(context)
    }
}