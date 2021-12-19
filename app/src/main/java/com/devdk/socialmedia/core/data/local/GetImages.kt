package com.devdk.socialmedia.core.data.local

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.devdk.socialmedia.core.domain.modal.Images
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetImages {

    suspend operator fun invoke(context: Context) : List<Images> {
        val images = arrayListOf<Images>()
        withContext(Dispatchers.IO){
            val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
            val projection = arrayOf(MediaStore.Images.ImageColumns.DATA,)
            context.contentResolver.query(
                collection ,
                projection ,
                null ,
                null ,
                MediaStore.Images.Media.DATE_ADDED
            )?.use { cursor ->
                val contentUriColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)
                while (cursor.moveToNext()) {
                    val contentUri: Uri = cursor.getString(contentUriColumn).toUri()
                    images.add(Images(contentUri ))
                }
            }
        }
        return images
    }

}