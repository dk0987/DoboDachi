package com.devdk.socialmedia.core.presentation.images_screen.util

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@ExperimentalPermissionsApi
fun PermissionState.isPermissionDenied() : Boolean{
    return !shouldShowRationale && !hasPermission
}