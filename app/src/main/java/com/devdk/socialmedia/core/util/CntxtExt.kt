package com.devdk.socialmedia.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.ui.res.stringResource
import com.devdk.socialmedia.R

@SuppressLint("StringFormatInvalid")
fun Context.sharePost(postId : String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "https://dobodachi.com/$postId"
        )
        type = "text/plain"
    }

    if(intent.resolveActivity(packageManager) != null) {
        startActivity(Intent.createChooser(intent, "Select an app"))
    }
}