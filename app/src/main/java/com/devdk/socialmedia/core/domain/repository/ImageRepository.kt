package com.devdk.socialmedia.core.domain.repository

import android.content.Context
import com.devdk.socialmedia.core.domain.modal.Images

interface ImageRepository {

    suspend fun getListOfImages(context: Context) : List<Images>
}