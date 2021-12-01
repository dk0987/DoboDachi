package com.devdk.socialmedia.core.presentation.util

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val icon : ImageVector ,
    val description : String ,
    val route : String
)
