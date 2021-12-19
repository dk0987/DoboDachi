package com.devdk.socialmedia.core.data.repository

import android.content.Context
import com.devdk.socialmedia.core.data.local.GetImages
import com.devdk.socialmedia.core.domain.modal.Images
import com.devdk.socialmedia.core.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val getImages: GetImages
) : ImageRepository {

    override suspend fun getListOfImages(context: Context): List<Images> {
        return getImages(context)
    }
}